package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import domain.User;

@Service
public class UserServiceImpl implements UserService {

	private UserDao dao;

	@Autowired
	public void setDao(UserDao dao) {
		this.dao = dao;
	}
		
   	@Override
   	@Transactional
	public void addNew(User user) {
		dao.create(user);
	}

	@Override
	@Transactional
	public void update(User user) {
		dao.update(user);
	}

	@Override
	@Transactional
	public void delete(User user) {
		dao.delete(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User getByLoginPass(String login, String pass) {
		return dao.findByLoginPass(login, pass);
	}

	@Override
	@Transactional(readOnly = true)
	public User getByLogin(String login) {
		return dao.findByLogin(login);
	}

}
