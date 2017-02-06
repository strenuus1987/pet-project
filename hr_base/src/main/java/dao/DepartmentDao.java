package dao;

import java.util.List;

import domain.Department;

public interface DepartmentDao {

	Long create(Department department);

	Department read(Long id);

	void update(Department department);

	void delete(Department department);

	List<Department> findAll();
	
	Department findDepartmentById(Long id);
	
	Department findDepartmentByName(String name);
	
	List<Department> findDepartmentByNameLike(String searchString);

}
