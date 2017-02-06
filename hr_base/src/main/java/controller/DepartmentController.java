package controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import domain.Department;
import domain.Employee;
import domain.User;
import service.DepartmentService;
import service.EmployeeService;
import service.UserService;

@Controller
public class DepartmentController {

	private UserService userService;
	private EmployeeService employeeService;
	private DepartmentService departmentService;
	
	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService us) {
		this.userService = us;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "employeeService")
	public void setEmployeeService(EmployeeService es) {
		this.employeeService = es;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "departmentService")
	public void setDepartmentService(DepartmentService ds) {
		this.departmentService = ds;
	}
	
	@RequestMapping(value = "/department", method = RequestMethod.GET)
	public String mainPage(Model model, HttpServletRequest req) {

		User user = (User) req.getSession().getAttribute("user");
		
		if (user == null) {
			
			Cookie[] arrCook =  req.getCookies();
			if (arrCook != null) {
				for (Cookie cook : arrCook) {
					if (cook.getName().equals("pageuser")) {
						User dBUser = userService.getByLogin(cook.getValue());

						if (dBUser != null) {
							req.getSession().setAttribute("user", dBUser);
							
							if (dBUser.getEditor()) {
								req.setAttribute("isEditor", true);
							}
							else {
								List list = employeeService.getAll();
								req.setAttribute("emplList", list);
								return "employees";
							}
							
							Boolean isCreate = false;
							
							if (req.getParameter("create") != null && req.getParameter("create").equals("true")) {
								req.setAttribute("create", true);
								isCreate = true;
							} else	if (req.getParameter("edit") != null && req.getParameter("edit").equals("true")) {
								req.setAttribute("edit", true);
							}
							
							if (req.getParameter("id") != null) {
								Long id = Long.valueOf(req.getParameter("id"));
								Department dep = departmentService.getDepartmentById(id);
								if (dep != null) {
									req.setAttribute("department", dep);
								}
								else {
									List list = departmentService.getAll();
									req.setAttribute("depList", list);
									return "departments";
								}
							}
							else if (!isCreate){
								List list = departmentService.getAll();
								req.setAttribute("depList", list);
								return "departments";
							}
							
							return "department";
						}
					}
				}	
				return "auth";
			}
			else {
				return "auth";
			}
		}
		
		if (user.getEditor()) {
			req.setAttribute("isEditor", true);
		}
		else {
			List list = employeeService.getAll();
			req.setAttribute("emplList", list);
			return "employees";
		}
		
		Boolean isCreate = false;
		
		if (req.getParameter("create") != null && req.getParameter("create").equals("true")) {
			isCreate = true;
			req.setAttribute("create", true);
		} else	if (req.getParameter("edit") != null && req.getParameter("edit").equals("true")) {
			req.setAttribute("edit", true);
		}
		
		if (req.getParameter("id") != null) {
			Long id = Long.valueOf(req.getParameter("id"));
			Department dep = departmentService.getDepartmentById(id);
			if (dep != null) {
				req.setAttribute("department", dep);
			}
			else {
				List list = departmentService.getAll();
				req.setAttribute("depList", list);
				return "departments";
			}
		}
		else if (!isCreate) {
			List list = departmentService.getAll();
			req.setAttribute("depList", list);
			return "departments";
		}
		
		return "department";
	}
	
	@ResponseBody
	@RequestMapping(value = "/department_edit", method = RequestMethod.POST)
	public String findUser(@RequestParam("name") String name, @RequestParam("action") String action, @RequestParam("id") Integer id, Model model, HttpServletResponse resp, HttpServletRequest req) {		

		JSONObject myJsonObj = new JSONObject();
		
		if (action != "undefined" && action != null) {
			
			if (action.equals("create")) {
			
				Department dBDep = departmentService.getDepartmentByName(name);
				
				if (dBDep == null) {
					departmentService.addNew(new Department(name));
				}
				else {
					myJsonObj.append("errorMsg", "Already exists");
				}
				
			}
			else if (action.equals("edit") && id != null && !id.equals("undefined")) {
				
				Department dBDep = departmentService.getDepartmentByName(name);
				
				if (dBDep == null) {
					
					dBDep = departmentService.getDepartmentById(Long.valueOf(id));
					
					if (dBDep != null) {
						
						dBDep.setName(name);
						departmentService.update(dBDep);
						
					}
					
				}
				else {
					myJsonObj.append("errorMsg", "Already exists");
				}
				
			}
			
		}
		
		return myJsonObj.toString();
		
	}
	
}
