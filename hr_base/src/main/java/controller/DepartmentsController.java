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
import domain.User;
import service.DepartmentService;
import service.EmployeeService;
import service.UserService;

@Controller
public class DepartmentsController {

	private UserService userService;
	private DepartmentService departmentService;
	private EmployeeService employeeService;
	
	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService us) {
		this.userService = us;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "departmentService")
	public void setDepartmentService(DepartmentService ds) {
		this.departmentService = ds;
	}
	
	@Autowired(required = true)
	@Qualifier(value = "employeeService")
	public void setEmployeeService(EmployeeService es) {
		this.employeeService = es;
	}
	
	@RequestMapping(value = "/departments", method = RequestMethod.GET)
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
							else {
								List list = employeeService.getAll();
								req.setAttribute("emplList", list);
								return "employees";
							}
							
							Cookie[] arrCookSearch =  req.getCookies();
							if (arrCook != null) {
								
								Boolean cookFound = false;
								
								for (Cookie cookSearch : arrCookSearch) {
									if (cookSearch.getName().equals("searchStringDepartment")) {
										String searchString = cookSearch.getValue();
										
										req.setAttribute("searchString", searchString);
										
										searchString = searchString.replace("?", "_");
										searchString = searchString.replace("*", "%");

										List<Department> list = departmentService.getDepartmentByNameLike(searchString);
										if (list != null) {
											req.setAttribute("depList", list);
										}
										else {
											req.setAttribute("searchString", "");
											list = departmentService.getAll();
											req.setAttribute("depList", list);
										}
										
										cookFound = true;
									}
								}
								
								if (!cookFound) {
									List list = departmentService.getAll();
									req.setAttribute("depList", list);
								}
								
							}
							else {
							
								List list = departmentService.getAll();
								req.setAttribute("depList", list);

							}
							
							return "departments";
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
		
		Cookie[] arrCook =  req.getCookies();
		if (arrCook != null) {
			
			Boolean cookFound = false;
			
			for (Cookie cook : arrCook) {
				if (cook.getName().equals("searchStringDepartment")) {
					String searchString = cook.getValue();
					
					req.setAttribute("searchString", searchString);
					
					searchString = searchString.replace("?", "_");
					searchString = searchString.replace("*", "%");

					List<Department> list = departmentService.getDepartmentByNameLike(searchString);
					if (list != null) {
						req.setAttribute("depList", list);
					}
					else {
						req.setAttribute("searchString", "");
						list = departmentService.getAll();
						req.setAttribute("depList", list);
					}
					
					cookFound = true;
				}
			}
			
			if (!cookFound) {
				List list = departmentService.getAll();
				req.setAttribute("depList", list);
			}
			
		}
		else {
		
			List list = departmentService.getAll();
			req.setAttribute("depList", list);

		}
		
		return "departments";
	}
	
	@ResponseBody
	@RequestMapping(value = "/search_dep", headers="content-type=application/json", method = RequestMethod.GET)
	public String findUser(@RequestParam("searchString") String searchString, Model model, HttpServletResponse resp, HttpServletRequest req) {		

		JSONObject myJsonObj = new JSONObject();
		
		if (searchString.isEmpty()) {

			Cookie[] arrCook =  req.getCookies();
			if (arrCook != null) {
				
				for (Cookie cook : arrCook) {
					if (cook.getName().equals("searchStringDepartment")) {
						cook.setMaxAge(0);
						resp.addCookie(cook);
					}
				}	
			}
			
			List<Department> list = departmentService.getAll();
			
			StringBuilder sb = new StringBuilder();

			for (Department department : list) {
				sb.append("<tr>");
				sb.append(department.toPage("departments"));
				sb.append("<td>");
				sb.append("<a href=\"department?edit=true&id=");
				sb.append(department.getId());
				sb.append("\">");
				sb.append("%%%%");
				sb.append("</a>");
				sb.append("</td>");
				sb.append("</tr>");
			}

			myJsonObj.append("depList", sb.toString());
			
		} else {

			Cookie[] arrCook =  req.getCookies();
			if (arrCook != null) {
				
				Boolean cookValSet = false;
				
				for (Cookie cook : arrCook) {
					if (cook.getName().equals("searchStringDepartment")) {
						cook.setValue(searchString);
						resp.addCookie(cook);
						cookValSet = true;
					}
				}	
				
				if (!cookValSet) {
					Cookie searchCookie = new Cookie("searchStringDepartment", searchString);
					searchCookie.setMaxAge(-1);
					searchCookie.setHttpOnly(true);
					resp.addCookie(searchCookie);
				}
				
			}
			else {

				Cookie searchCookie = new Cookie("searchStringDepartment", searchString);
				searchCookie.setMaxAge(-1);
				searchCookie.setHttpOnly(true);
				resp.addCookie(searchCookie);
				
			}

			searchString = searchString.replace("?", "_");
			searchString = searchString.replace("*", "%");

			List<Department> list = departmentService.getDepartmentByNameLike(searchString);

			if (list == null) {
				myJsonObj.append("errorMsg", true);
			} else {

				StringBuilder sb = new StringBuilder();

				for (Department department : list) {
					sb.append("<tr>");
					sb.append(department.toPage("departments"));
					sb.append("<td>");
					sb.append("<a href=\"department?edit=true&id=");
					sb.append(department.getId());
					sb.append("\">");
					sb.append("%%%%");
					sb.append("</a>");
					sb.append("</td>");
					sb.append("</tr>");
				}

				myJsonObj.append("depList", sb.toString());
			}

		}
		
		return myJsonObj.toString();
		
	}
	
}
