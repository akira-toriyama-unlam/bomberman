package com.bomberman.extras;


import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*; 

public class Toast extends JFrame { 

	 //String of toast 
	 String s; 
	
	 // JWindow 
	 JWindow w; 
	 
	 Window parent;
	
	 public Toast(String s, int x, int y, Window window) 
	 { 
	     w = new JWindow(); 
	     parent = window;
	     // make the background transparent 
	     w.setBackground(new Color(0, 0, 0, 0)); 

	
	     // create a panel 
	     JPanel p = new JPanel() { 
	         public void paintComponent(Graphics g) 
	         { 
	            
	             Graphics2D g2 = (Graphics2D) g;
	             g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                 RenderingHints.VALUE_ANTIALIAS_ON);

	             int wid = g.getFontMetrics().stringWidth(s) * 2; 
	             int hei = g.getFontMetrics().getHeight(); 
	             // draw the boundary of the toast and fill it 
	             g2.setColor(Color.black); 
	             g2.fillRect(10, 10, wid + 30, hei + 10); 
	             g2.setColor(Color.black); 
	             g2.drawRect(10, 10, wid + 30, hei + 10); 
	
	             // set the color of text 
	             Font font = new Font("Lucida Sans Regular", Font.PLAIN, 20);

	             g.setFont(font);
	             g.setColor(new Color(255, 255, 255, 240));
	             g.drawString(s, 3, 27); 
	             int t = 250; 
	
	             // draw the shadow of the toast 
	             for (int i = 0; i < 4; i++) { 
	                 t -= 60; 
	                 g2.setColor(new Color(0, 0, 0, t)); 
	                 g2.drawRect(10 - i, 10 - i, wid + 30 + i * 2, 
	                            hei + 10 + i * 2); 
	             } 
	         } 
	     }; 
	
	     w.add(p); 
	     w.setLocation(x, y); 
	    
	     w.setSize(500, 100); 
	 } 
	
	 public synchronized void showtoast() 
	 { 
	     try { 
	         w.setOpacity(1); 
	         w.setVisible(true);
			 w.setLocationRelativeTo(parent);
	         Timer timer = new Timer();
			 timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// make the message disappear  slowly 
			         for (double d = 1.0; d > 0.2; d -= 0.1) { 
			             w.setOpacity((float)d); 
			         } 
			
			         // set the visibility to false 
			         w.setVisible(false);
				}
			}, 2000); 
	     } 
	     catch (Exception e) { 
	         System.out.println(e.getMessage()); 
	     } 
	 } 
} 