package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import domain.Department;
import domain.Employee;
import domain.User;

public interface EmployeeService {

	void addNew(Employee employee);
	void update(Employee employee);
	void delete(Employee employee);
	List<Employee> getAll();
	Employee getEmployeeById(Long id);
	List<Employee> getEmployeeByNameLike(String searchString);
	List<Employee> getEmployeeBySurnameLike(String searchString);
	List<Employee> getEmployeeByNameAndSurnameLike(String searchStringName, String searchStringSurname);
	
}
