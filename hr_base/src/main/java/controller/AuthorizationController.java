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
public class AuthorizationController{
	
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
	
	@RequestMapping(value = { "/", "/auth" }, method = RequestMethod.GET)
	public String authPageGet(Model model, HttpServletRequest req, HttpServletResponse resp) {
		
		String logout = req.getParameter("logout");
		
		if (logout != null && logout.equals("true")) {
			req.getSession().setAttribute("user", null);
			
			Cookie[] arrCook =  req.getCookies();
			if (arrCook != null) {
				for (Cookie cook : arrCook) {
					if (cook.getName().equals("pageuser")) {
						cook.setMaxAge(0);
						resp.addCookie(cook);
					}
				}
			}
			
			return "auth";
		}
		
		Cookie[] arrCook = req.getCookies();
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
		}
		
		return "auth";
	}
	
	@ResponseBody
	@RequestMapping(value = "/auth", headers="content-type=application/json", method = RequestMethod.GET)
	public String findUser(@RequestParam("username") String user, @RequestParam("password") String pass, Model model, HttpServletResponse resp, HttpServletRequest req) {		

		JSONObject myJsonObj = new JSONObject();
		
		if (user != "undefined" && user != null && !user.isEmpty() 
				&& pass != "undefined" && pass != null && !pass.isEmpty()) {
			
			User dBUser = userService.getByLoginPass(user, pass);
					
			if (dBUser == null) {
				myJsonObj.append("errorMsg", true);
			}
			else {
				myJsonObj.append("url", "employees");
				
				Cookie userCookie = new Cookie("pageuser", dBUser.getName());
				userCookie.setMaxAge(3600);
				userCookie.setHttpOnly(true);
				resp.addCookie(userCookie);
				
				req.getSession().setAttribute("user", dBUser);		
			}
		}
		
		return myJsonObj.toString();
		
	}
	
}
