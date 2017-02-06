package dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import domain.Department;
import domain.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	private static Logger log = Logger.getLogger(EmployeeDaoImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public Long create(Employee employee) {
		Session session = sessionFactory.getCurrentSession();
		Long id = (Long) session.save(employee);
		return id;
	}

	@Override
	public Employee read(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Employee employee = (Employee) session.get(Employee.class, id);
		return employee;
	}

	@Override
	public void update(Employee employee) {
		Session session = sessionFactory.getCurrentSession();
		session.update(employee);
	}

	@Override
	public void delete(Employee employee) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(employee);
	}

	@Override
	public List<Employee> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from employees").addEntity(Employee.class);
		return query.list();
	}

	@Override
	public Employee findEmployeeById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from employees where employees.id = :a").addEntity(Employee.class);
		query.setLong("a", id);
		
		List<Employee> list = query.list();
		
		if (list.size() == 0) {
			return null;
		}
		else {
			return list.get(0);
		}
		
	}

	@Override
	public List<Employee> findEmployeeByNameLike(String searchString) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from employees where upper(employees.first_name) like upper(:a)").addEntity(Employee.class);
		query.setString("a", searchString);
		
		List<Employee> list = query.list();
		
		if (list.size() == 0) {
			return null;
		}
		else {
			return list;
		}
	}

	@Override
	public List<Employee> findEmployeeBySurnameLike(String searchString) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from employees where upper(employees.last_name) like upper(:a)").addEntity(Employee.class);
		query.setString("a", searchString);
		
		List<Employee> list = query.list();
		
		if (list.size() == 0) {
			return null;
		}
		else {
			return list;
		}
	}

	@Override
	public List<Employee> findEmployeeByNameAndSurnameLike(String searchStringName, String searchStringSurname) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from employees where upper(employees.first_name) like upper(:a) and upper(employees.last_name) like upper(:b)").addEntity(Employee.class);
		query.setString("a", searchStringName);
		query.setString("b", searchStringSurname);
		
		List<Employee> list = query.list();
		
		if (list.size() == 0) {
			return null;
		}
		else {
			return list;
		}
	}
}
