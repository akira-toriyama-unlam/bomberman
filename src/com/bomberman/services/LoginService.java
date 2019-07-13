package com.bomberman.services;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.bomberman.database.HibernateConfiguration;
import com.bomberman.entities.Player;

public class LoginService {

	public Optional<Player> createPlayer(String name, String password) {
		if (!existsPlayer(name, password).isPresent()) {
			SessionFactory factory = HibernateConfiguration.getSessionFactory();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();
			try {
				Player player = new Player(name, password);
				session.save(player);
				tx.commit();
				return Optional.of(player);
			} catch (HibernateException e) {
				if (tx != null) {
					tx.rollback();
				}
				e.printStackTrace();
				return Optional.ofNullable(null);
			} finally {
				session.close();
			}
		}
		return Optional.ofNullable(null);
	}

	public Optional<Player> existsPlayer(String name, String password) {
		SessionFactory factory = HibernateConfiguration.getSessionFactory();
		Session session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Player> cq = cb.createQuery(Player.class);
		Root<Player> rp = cq.from(Player.class);
		cq.where(cb.equal(rp.get("name"), name));
		// cq.where(cb.equal(rp.get("password"), password));
		return session.createQuery(cq).getResultList().stream().findFirst();
	}

}
