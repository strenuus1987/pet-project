package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import domain.Department;
import domain.Employee;
import domain.User;

public interface DepartmentService {

	void addNew(Department department);
	void update(Department department);
	void delete(Department department);
	List<Department> getAll();
	Department getDepartmentById(Long id);
	Department getDepartmentByName(String name);
	List<Department> getDepartmentByNameLike(String searchString);
	
}
