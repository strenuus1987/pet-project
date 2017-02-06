package dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public class UserDaoImpl implements UserDao {

	private static Logger log = Logger.getLogger(UserDaoImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public Long create(User user) {
		Session session = sessionFactory.getCurrentSession();
		Long id = (Long) session.save(user);
		return id;
	}

	@Override
	public User read(Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, id);
		return user;
	}

	@Override
	public void update(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
	}

	@Override
	public void delete(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
	}

	@Override
	public List<User> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> list = session.createQuery("from User").list();		
		return list;
	}

	@Override
	public User findByLoginPass(String login, String pass) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from users where users.name = :a and users.password = :b limit 1").addEntity(User.class);
		query.setString("a", login);
		query.setString("b", pass);
				
		return query.list().size() > 0 ? (User) query.list().get(0) : null;
		
	}

	@Override
	public User findByLogin(String login) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from users where users.name = :a limit 1").addEntity(User.class);
		query.setString("a", login);
				
		return query.list().size() > 0 ? (User) query.list().get(0) : null;
	}
}
