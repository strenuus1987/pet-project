package dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import domain.Department;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

	private static Logger log = Logger.getLogger(DepartmentDaoImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public Long create(Department department) {
		Session session = sessionFactory.getCurrentSession();
		Long id = (Long) session.save(department);
		return id;
	}

	@Override
	public Department read(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Department department = (Department) session.get(Department.class, id);
		return department;
	}

	@Override
	public void update(Department department) {
		Session session = sessionFactory.getCurrentSession();
		session.update(department);
	}

	@Override
	public void delete(Department department) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(department);
	}

	@Override
	public List<Department> findAll() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Department> list = session.createQuery("from Department").list();		
		return list;
	}

	@Override
	public Department findDepartmentById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from departments where departments.id = :a").addEntity(Department.class);
		query.setLong("a", id);
		
		List<Department> list = query.list();
		
		if (list.size() == 0) {
			return null;
		}
		else {
			return list.get(0);
		}
	}

	@Override
	public Department findDepartmentByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from departments where upper(departments.name) = upper(:a)").addEntity(Department.class);
		query.setString("a", name);
		
		List<Department> list = query.list();
		
		if (list.size() == 0) {
			return null;
		}
		else {
			return list.get(0);
		}
	}

	@Override
	public List<Department> findDepartmentByNameLike(String searchString) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select * from departments where upper(departments.name) like upper(:a)").addEntity(Department.class);
		query.setString("a", searchString);
		
		List<Department> list = query.list();
		
		if (list.size() == 0) {
			return null;
		}
		else {
			return list;
		}
	}
}
