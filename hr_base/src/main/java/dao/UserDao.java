package dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import domain.User;

//@Transactional
public interface UserDao {

	Long create(User user);

	User read(Long id);

	void update(User user);

	void delete(User user);

	List<User> findAll();
	
	User findByLoginPass(String login, String pass);
	
	User findByLogin(String login);

}
