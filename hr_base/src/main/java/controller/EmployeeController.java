package controller;

import java.util.GregorianCalendar;
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
public class EmployeeController {

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
	
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String mainPage(Model model, HttpServletRequest req) {

		User user = (User) req.getSession().getAttribute("user");

		if (user == null) {

			Cookie[] arrCook = req.getCookies();
			if (arrCook != null) {
				for (Cookie cook : arrCook) {
					if (cook.getName().equals("pageuser")) {
						User dBUser = userService.getByLogin(cook.getValue());

						if (dBUser != null) {
							req.getSession().setAttribute("user", dBUser);
							
							Boolean isCreate = false;
							
							if (req.getParameter("create") != null && req.getParameter("create").equals("true")) {
								req.setAttribute("create", true);
								isCreate = true;
							} else	if (req.getParameter("edit") != null && req.getParameter("edit").equals("true")) {
								req.setAttribute("edit", true);
							}
							
							req.setAttribute("departments", departmentService.getAll());
							
							if (req.getAttribute("id") != null) {
								Long id = Long.valueOf(req.getParameter("id"));
								Employee emp = employeeService.getEmployeeById(id);
								if (emp != null) {
									req.setAttribute("employee", emp);
								}
								else {
									List list = employeeService.getAll();
									req.setAttribute("emplList", list);
									return "employees";
								}
							}
							else if (!isCreate){
								List list = employeeService.getAll();
								req.setAttribute("emplList", list);
								return "employees";
							}
							
							return "employee";
						}
					}
				}
				return "auth";
			} else {
				return "auth";
			}
		}
		
		Boolean isCreate = false;
		
		if (req.getParameter("create") != null && req.getParameter("create").equals("true")) {
			req.setAttribute("create", true);
			isCreate = true;
		} else	if (req.getParameter("edit") != null && req.getParameter("edit").equals("true")) {
			req.setAttribute("edit", true);
		}
		
		req.setAttribute("departments", departmentService.getAll());
		
		if (req.getParameter("id") != null) {
			Long id = Long.valueOf(req.getParameter("id"));
			Employee emp = employeeService.getEmployeeById(id);
			if (emp != null) {
				req.setAttribute("employee", emp);
			}
			else {
				List list = employeeService.getAll();
				req.setAttribute("emplList", list);
				return "employees";
			}
		}
		else if (!isCreate){
			List list = employeeService.getAll();
			req.setAttribute("emplList", list);
			return "employees";
		}

		return "employee";

	}

	@ResponseBody
	@RequestMapping(value = "/employee_edit", method = RequestMethod.POST)
	public String saveData(@RequestParam("action") String action, @RequestParam("id") Long id, 
			@RequestParam("name") String name, @RequestParam("surname") String surname,
			@RequestParam("dep") String dep, @RequestParam("bdate") String bdate,
			@RequestParam("salary") Double salary, @RequestParam("fired") Boolean fired,
			Model model, HttpServletResponse resp, HttpServletRequest req) {		

		JSONObject myJsonObj = new JSONObject();
		
		String[] dateArray = bdate.split("-");
		
		GregorianCalendar date = new GregorianCalendar(Integer.valueOf(dateArray[0]), Integer.valueOf(dateArray[1]), Integer.valueOf(dateArray[2]));
		
		if (action != "undefined" && action != null) {
			
			if (action.equals("create")) {
			
				Employee empl = new Employee(formatStringData(name), formatStringData(surname), salary, date, true, departmentService.getDepartmentByName(dep));
				
				employeeService.addNew(empl);
				
			}
			else if (action.equals("edit") && id != null && !id.equals("undefined")) {
				
				String firstLetter = name.substring(0, 0).toUpperCase();
				if (name.length() > 1) {
					
				}
				
				Employee empl = employeeService.getEmployeeById(id);
				
				empl.setFirstName(formatStringData(name));
				empl.setLastName(formatStringData(surname));
				empl.setDepartment(departmentService.getDepartmentByName(dep));
				empl.setBirthdate(date);
				empl.setSalary(salary);
				empl.setActive(!fired);
				
				employeeService.update(empl);
				
			}
			
		}
		
		return myJsonObj.toString();
		
	}
	
	private String formatStringData(String stringData) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(stringData.substring(0, 0).toUpperCase());
		
		if (stringData.length() > 1) {
			sb.append(stringData.substring(1, stringData.length() - 1).toLowerCase());
		}
		
		return sb.toString();
	}
	
}
