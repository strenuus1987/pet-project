package dao;

import java.util.List;

import domain.Employee;

public interface EmployeeDao {

	Long create(Employee employee);

	Employee read(Long id);

	void update(Employee employee);

	void delete(Employee employee);

	List<Employee> findAll();
	
	Employee findEmployeeById(Long id);

	List<Employee> findEmployeeByNameLike(String searchString);
	
	List<Employee> findEmployeeBySurnameLike(String searchString);
	
	List<Employee> findEmployeeByNameAndSurnameLike(String searchStringName, String searchStringSurname);
	
}
