package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import domain.User;

public interface UserService {

	void addNew(User user);
	void update(User user);
	void delete(User user);
	List<User> getAll();
	User getByLoginPass(String login, String pass);
	User getByLogin(String login);
	
}
