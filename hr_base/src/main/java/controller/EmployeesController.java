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

import domain.Employee;
import domain.User;
import service.DepartmentService;
import service.EmployeeService;
import service.UserService;

@Controller
public class EmployeesController {

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
	
	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public String mainPage(Model model, HttpServletRequest req, HttpServletResponse resp) {

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
							
							List list = employeeService.getAll();
							req.setAttribute("emplList", list);

							return "employees";
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
		
		Cookie[] arrCook =  req.getCookies();
		if (arrCook != null) {
			
			String searchStringName = "";
			String searchStringSurname = "";
			
			for (Cookie cook : arrCook) {
				if (cook.getName().equals("searchStringNameEmployee")) {
					searchStringName = cook.getValue();
					
					req.setAttribute("searchStringName", searchStringName);
					
					searchStringName = searchStringName.replace("?", "_");
					searchStringName = searchStringName.replace("*", "%");

				}
				if (cook.getName().equals("searchStringSurnameEmployee")) {
					searchStringSurname = cook.getValue();
					
					req.setAttribute("searchStringSurname", searchStringSurname);
					
					searchStringSurname = searchStringSurname.replace("?", "_");
					searchStringSurname = searchStringSurname.replace("*", "%");

				}
			}

			List<Employee> list = null;
			
			
			if (searchStringName.isEmpty() && searchStringSurname.isEmpty()) {
				list = employeeService.getAll();
				req.setAttribute("emplList", list);
			}
			else if(!searchStringName.isEmpty() && !searchStringSurname.isEmpty()) {
				list = employeeService.getEmployeeByNameAndSurnameLike(searchStringName, searchStringSurname);
			}
			else if(!searchStringName.isEmpty() && searchStringSurname.isEmpty()) {
				list = employeeService.getEmployeeByNameLike(searchStringName);
			}
			else if(searchStringName.isEmpty() && !searchStringSurname.isEmpty()) {
				list = employeeService.getEmployeeBySurnameLike(searchStringSurname);
			}
			
			if (list != null) {
				req.setAttribute("emplList", list);
			}
			else {
				req.setAttribute("searchStringName", "");
				req.setAttribute("searchStringSurname", "");
				list = employeeService.getAll();
				req.setAttribute("emplList", list);
			}
			
		}
		else {
		
			List list = employeeService.getAll();
			req.setAttribute("emplList", list);

		}
		
		return "employees";
	}

	@ResponseBody
	@RequestMapping(value = "/search_empl", headers="content-type=application/json", method = RequestMethod.GET)
	public String findUser(@RequestParam("searchStringName") String searchStringName, @RequestParam("searchStringSurname") String searchStringSurname, Model model, HttpServletResponse resp, HttpServletRequest req) {		

		JSONObject myJsonObj = new JSONObject();
		
		Boolean isEdit = false;
		
		User user = (User) req.getSession().getAttribute("user");
		
		if (user != null) {
			isEdit = user.getEditor();
		}
		
		if (searchStringName.isEmpty() && searchStringSurname.isEmpty()) {

			Cookie[] arrCook =  req.getCookies();
			if (arrCook != null) {
				
				for (Cookie cook : arrCook) {
					if (cook.getName().equals("searchStringNameEmployee")) {
						cook.setMaxAge(0);
						resp.addCookie(cook);
					}
					if (cook.getName().equals("searchStringSurnameEmployee")) {
						cook.setMaxAge(0);
						resp.addCookie(cook);
					}
				}	
			}
			
			List<Employee> list = employeeService.getAll();
			
			StringBuilder sb = new StringBuilder();

			for (Employee empl : list) {
				
				sb.append("<tr>");
				sb.append(empl.toPage(isEdit));
				
				if (isEdit) {
					sb.append("<td>");
					sb.append("<a href=\"department?edit=true&id=");
					sb.append(empl.getDepartment().getId());
					sb.append("\">");
					sb.append("%%%%");
					sb.append("</a>");
					sb.append("</td>");
				}
				
				sb.append("</tr>");
			}

			myJsonObj.append("emplList", sb.toString());
			
		} else {

			Cookie[] arrCook =  req.getCookies();
			if (arrCook != null) {
				
				Boolean cookNameValSet = false;
				Boolean cookSurnameValSet = false;
				
				for (Cookie cook : arrCook) {
					if (cook.getName().equals("searchStringNameEmployee")) {
						cook.setValue(searchStringName);
						resp.addCookie(cook);
						cookNameValSet = true;
					}
					if (cook.getName().equals("searchStringSurnameEmployee")) {
						cook.setValue(searchStringSurname);
						resp.addCookie(cook);
						cookSurnameValSet = true;
					}
				}	
				
				if (!cookNameValSet) {
					Cookie searchCookie = new Cookie("searchStringNameEmployee", searchStringName);
					searchCookie.setMaxAge(-1);
					searchCookie.setHttpOnly(true);
					resp.addCookie(searchCookie);
				}
				if (!cookSurnameValSet) {
					Cookie searchCookie = new Cookie("searchStringSurnameEmployee", searchStringSurname);
					searchCookie.setMaxAge(-1);
					searchCookie.setHttpOnly(true);
					resp.addCookie(searchCookie);
				}
				
			}
			else {

				Cookie searchCookieName = new Cookie("searchStringNameEmployee", searchStringName);
				searchCookieName.setMaxAge(-1);
				searchCookieName.setHttpOnly(true);
				resp.addCookie(searchCookieName);
				
				Cookie searchCookieSurname = new Cookie("searchStringSurnameEmployee", searchStringSurname);
				searchCookieSurname.setMaxAge(-1);
				searchCookieSurname.setHttpOnly(true);
				resp.addCookie(searchCookieSurname);
				
			}

			searchStringName = searchStringName.replace("?", "_");
			searchStringName = searchStringName.replace("*", "%");
			searchStringSurname = searchStringSurname.replace("?", "_");
			searchStringSurname = searchStringSurname.replace("*", "%");

			List<Employee> list = null;
			
			if (!searchStringName.isEmpty() && !searchStringSurname.isEmpty()) {
				list = employeeService.getEmployeeByNameAndSurnameLike(searchStringName, searchStringSurname);			
			}
			else if (!searchStringName.isEmpty() && searchStringSurname.isEmpty()) {
				list = employeeService.getEmployeeByNameLike(searchStringName);
			}
			else if (searchStringName.isEmpty() && !searchStringSurname.isEmpty()) {
				list = employeeService.getEmployeeBySurnameLike(searchStringSurname);
			}

			if (list == null) {
				myJsonObj.append("errorMsg", true);
			} else {

				StringBuilder sb = new StringBuilder();

				for (Employee empl : list) {
					sb.append("<tr>");
					sb.append(empl.toPage(isEdit));
					
					if (isEdit) {
						sb.append("<td>");
						sb.append("<a href=\"department?edit=true&id=");
						sb.append(empl.getDepartment().getId());
						sb.append("\">");
						sb.append("%%%%");
						sb.append("</a>");
						sb.append("</td>");
					}
					
					sb.append("</tr>");
				}

				myJsonObj.append("emplList", sb.toString());
			}

		}
		
		return myJsonObj.toString();
		
	}

	
}
