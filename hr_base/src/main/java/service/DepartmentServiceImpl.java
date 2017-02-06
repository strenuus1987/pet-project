package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.DepartmentDao;
import dao.UserDao;
import domain.Department;
import domain.Employee;
import domain.User;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao dao;

	@Autowired
	public void setDao(DepartmentDao dao) {
		this.dao = dao;
	}
		
   	@Override
   	@Transactional
	public void addNew(Department department) {
		dao.create(department);
	}

	@Override
	@Transactional
	public void update(Department department) {
		dao.update(department);
	}

	@Override
	@Transactional
	public void delete(Department department) {
		dao.delete(department);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Department> getAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Department getDepartmentById(Long id) {
		return dao.findDepartmentById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Department getDepartmentByName(String name) {
		return dao.findDepartmentByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Department> getDepartmentByNameLike(String searchString) {
		return dao.findDepartmentByNameLike(searchString);
	}
	
}
