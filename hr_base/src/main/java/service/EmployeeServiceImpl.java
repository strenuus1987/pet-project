package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.EmployeeDao;
import domain.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDao dao;

	@Autowired
	public void setDao(EmployeeDao dao) {
		this.dao = dao;
	}
		
   	@Override
   	@Transactional
	public void addNew(Employee employee) {
		dao.create(employee);
	}

	@Override
	@Transactional
	public void update(Employee employee) {
		dao.update(employee);
	}

	@Override
	@Transactional
	public void delete(Employee employee) {
		dao.delete(employee);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> getAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Employee getEmployeeById(Long id) {
		return dao.findEmployeeById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> getEmployeeByNameLike(String searchString) {
		return dao.findEmployeeByNameLike(searchString);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> getEmployeeBySurnameLike(String searchString) {
		return dao.findEmployeeBySurnameLike(searchString);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> getEmployeeByNameAndSurnameLike(String searchStringName, String searchStringSurname) {
		return dao.findEmployeeByNameAndSurnameLike(searchStringName, searchStringSurname);
	}

}
