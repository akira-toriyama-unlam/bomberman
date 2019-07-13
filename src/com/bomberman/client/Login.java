package com.bomberman.client;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.bomberman.database.HibernateConfiguration;
import com.bomberman.entities.Player;

public class Login extends JPanel {

	private Window frame;
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public Login(Window frame) {
		this.frame = frame;
		setLayout(null);
		setBounds(100, 100, Window.WIDTH, Window.HEIGHT);
	}

	@Override
	protected void paintComponent(Graphics g) {
		this.setBackground(g);

		ScheduledFuture<?> countdown = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				showLogin();
			}
		}, 1, TimeUnit.MILLISECONDS);
	}

	private void setBackground(Graphics g) {
		int width = this.getSize().width;
		int height = this.getSize().height;
		g.drawImage(new ImageIcon("./resources/fondo-menu.jpg").getImage(), 0, 0, width, height, null);
	}

	private void showLogin() {
		JTextField user = new JTextField(5);
		JTextField password = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Username:"));
		myPanel.add(user);
		myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		myPanel.add(new JLabel("Password:"));
		myPanel.add(password);

		int result = JOptionPane.showOptionDialog(null, myPanel, "Login into your account",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				new String[] { "Login", "Register" }, "default");

		if (result == JOptionPane.OK_OPTION) {
			System.out.println("x value: " + user.getText());
			System.out.println("y value: " + password.getText());
		} else {
			this.showRegister();
		}
	}

	private void showRegister() {
		JTextField user = new JTextField(10);
		JTextField password = new JTextField(10);

		JPanel myPanel = new JPanel(new GridLayout(2, 1));
		myPanel.add(new JLabel("Username:"));
		myPanel.add(user);
		myPanel.add(new JLabel("Password:"));
		myPanel.add(password);

		int result = JOptionPane.showOptionDialog(null, myPanel, "Login into your account", JOptionPane.OK_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new String[] { "Register" }, "default");

		if (result == JOptionPane.OK_OPTION) {
			SessionFactory factory = HibernateConfiguration.getSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();

			try {
				Player player = new Player(user.getText(), password.getText());
				session.save(player);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null) {
					tx.rollback();
				}
				e.printStackTrace();
			} finally {
				session.close();
			}

		} else {
			// Close
		}
	}
}
