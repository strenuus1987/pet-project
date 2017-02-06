<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="domain.Department"%>
<%@ page import="domain.Employee"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>User</title>

    <!-- Bootstrap -->
    <link href="content/css/bootstrap.min.css" rel="stylesheet">
    <link href="content/css/elem_style.css" rel="stylesheet">
   
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="content/js/bootstrap.min.js"></script>
    <script src="content/js/employee.js"></script>
    
</head>
<body>
    
    <%Boolean isCreate = false;
    Boolean isEdit = false;
    Employee empl = null;
    if (request.getAttribute("create") != null && (Boolean) request.getAttribute("create") == true) {
    	isCreate = true;
    }
    if (request.getAttribute("edit") != null && (Boolean) request.getAttribute("edit") == true) {
    	isEdit = true;
    }
    if (request.getAttribute("employee") != null) {
    	empl = (Employee) request.getAttribute("employee");
    	if (!empl.getActive()) {
    		isEdit = false;
    	}
    }
    %>
    
    <div class="container">
        <div class="row">
            <div class="col-lg-11"><spring:message code="label_language" text="Text not found" /> : 
            <a href="?locale=en<% if (isCreate) {
            	out.print("&create=true");
            }
            else if (isEdit && empl != null) {
            	out.print("&edit=true&id=" + empl.getId());
            }
            %>
            "><spring:message code="label_languageEN" text="Text not found" /></a>|
            <a href="?locale=ru<% if (isCreate) {
            	out.print("&create=true");
            }
            else if (isEdit && empl != null) {
            	out.print("&edit=true&id=" + empl.getId());
            }
            %>"><spring:message code="label_languageRU" text="Text not found" /></a><p></div>
            <div class="col-md-1" id="logout"><a href="auth?logout=true"><spring:message code="label_logout" text="Text not found" /></a></div>
        </div>
        <div class="row">
            <form>
              <%if (empl != null) { %>
              	<legend>
              	<%out.print(empl.getFirstName() + " " + empl.getLastName()); %>
              	</legend>
              <%} %>
              <div class="form-group">
					<%if (isCreate) {%>
					
					<label><spring:message code="label_employee_name"
							text="Text not found" /></label> 
					<input type="text"
						class="form-control inputs" id="firstName"
						placeholder="<spring:message code="placeholder_employee_name" text="Text not found" />"> 
						
					<p id="errormsg_empty_name" class="error_message"> <spring:message code="emp_error_empty_name" text="Text not found" /> </p>	
					<p id="errormsg_unacceptable_name" class="error_message"> <spring:message code="emp_error_unacceptable_symbols_name" text="Text not found" /> </p>	
					
						
					<label><spring:message code="label_employee_surname"
							text="Text not found" /></label> 
							
					<input type="text"
						class="form-control inputs" id="lastName"
						placeholder="<spring:message code="placeholder_employee_surname" text="Text not found" />"> 
					
					<p id="errormsg_empty_surname" class="error_message"> <spring:message code="emp_error_empty_surname" text="Text not found" /> </p>	
					<p id="errormsg_unacceptable_surname" class="error_message"> <spring:message code="emp_error_unacceptable_symbols_surname" text="Text not found" /> </p>	
					
						
					<label><spring:message code="label_employee_department"
							text="Text not found" /></label> 
							
					<select id = "department"
						class="form-control inputs">
						<%if(request.getAttribute("departments") != null){
							List<Department> depts = (List<Department>)request.getAttribute("departments");
	                    	
	                    	for(Department department: depts){%>
	                    		<option>
	                    		<% out.print(department.getName()); %>
	                    		</option>
	                    	<%}
						}; %>
					</select> 
					
					<label><spring:message
							code="label_employee_birth_date" text="Text not found" /></label> 
							
					<input type="date" class="form-control inputs" id="birthDate"> 
					
					<p id="errormsg_date" class="error_message"> <spring:message code="emp_error_date_format" text="Text not found" /> </p>	
					<p id="errormsg_empty_date" class="error_message"> <spring:message code="emp_error_empty_date" text="Text not found" /> </p>	
					
					
					<label><spring:message code="label_employee_salary"
							text="Text not found" /></label> 
							
					<input type="number"
						class="form-control inputs" step="0.01" id="salary"
						placeholder="<spring:message code="placeholder_employee_salary" text="Text not found" />">

					<p id="errormsg_salary" class="error_message"> <spring:message code="emp_error_salary_format" text="Text not found" /> </p>	
					<p id="errormsg_empty_salary" class="error_message"> <spring:message code="emp_error_empty_salary" text="Text not found" /> </p>	
					

					<%} else if (isEdit && empl != null) {%>

					<label><spring:message code="label_employee_name" text="Text not found" /></label> 
							
					<input type="text" class="form-control inputs" id="firstName" value="<% out.print(empl.getFirstName()); %>" placeholder="<spring:message code="placeholder_employee_name" text="Text not found" />"> 
					
					<p id="errormsg_empty_name" class="error_message"> <spring:message code="emp_error_empty_name" text="Text not found" /> </p>	
					<p id="errormsg_unacceptable_name" class="error_message"> <spring:message code="emp_error_unacceptable_symbols_name" text="Text not found" /> </p>	
						
					<label><spring:message code="label_employee_surname" text="Text not found" /></label> 
							
					<input type="text" class="form-control inputs" id="lastName" value="<% out.print(empl.getLastName()); %>" placeholder="<spring:message code="placeholder_employee_surname" text="Text not found" />"> 
					
					<p id="errormsg_empty_surname" class="error_message"> <spring:message code="emp_error_empty_surname" text="Text not found" /> </p>	
					<p id="errormsg_unacceptable_surname" class="error_message"> <spring:message code="emp_error_unacceptable_symbols_surname" text="Text not found" /> </p>	
					
						
					<label><spring:message code="label_employee_department" text="Text not found" /></label> 
							
					<select id = "department" class="form-control inputs">
						<option><% out.print(empl.getDepartment().getName()); %></option>
						
						<%if(request.getAttribute("departments") != null){
							List<Department> depts = (List<Department>)request.getAttribute("departments");
	                    	
	                    	for(Department department: depts){
	                    		if (!department.getName().equals(empl.getDepartment().getName())) {%>
		                    		<option>
		                    		<% out.print(department.getName()); %>
		                    		</option>
	                    		<%}
	                    	}
						}; %>
						
						</select> 
						
					<label><spring:message code="label_employee_birth_date" text="Text not found" /></label> 
							
					<input type="date" class="form-control inputs" id="birthDate" value="<% out.print(empl.getBirthdateToPage()); %>"> 
					
					<p id="errormsg_date" class="error_message"> <spring:message code="emp_error_date_format" text="Text not found" /> </p>	
					<p id="errormsg_empty_date" class="error_message"> <spring:message code="emp_error_empty_date" text="Text not found" /> </p>	
					
						
					<label><spring:message code="label_employee_salary" text="Text not found" /></label> 
							
					<input type="number" class="form-control inputs" id="salary" value="<% out.print(empl.getSalary()); %>" placeholder="<spring:message code="placeholder_employee_salary" text="Text not found" />">

					<p id="errormsg_salary" class="error_message"> <spring:message code="emp_error_salary_format" text="Text not found" /> </p>	
					<p id="errormsg_empty_salary" class="error_message"> <spring:message code="emp_error_empty_salary" text="Text not found" /> </p>	
					

					<label> <spring:message code="label_employee_active" text="Text not found" /> </label> 
					<input id="fired" type="checkbox">

					<%} else {%>

					<label><spring:message code="label_employee_name"
							text="Text not found" /></label> 
							
					<input type="text"
						class="form-control inputs" id="firstName"
						placeholder="<spring:message code="placeholder_employee_name" text="Text not found" />"
						readonly="true"
						value="<% out.print(empl.getFirstName()); %>"> 
						
					<label><spring:message
							code="label_employee_surname" text="Text not found" /></label> 
							
					<input
						type="text" class="form-control inputs" id="lastName"
						placeholder="<spring:message code="placeholder_employee_surname" text="Text not found" />"
						readonly="true"
						value="<% out.print(empl.getLastName()); %>"> 
						
					<label><spring:message
							code="label_employee_department" text="Text not found" /></label> 
							
					<select class="form-control inputs" disabled ="true">
						<option> <% out.print(empl.getDepartment().getName()); %> </option>
						</select> 
						
					<label><spring:message
							code="label_employee_birth_date" text="Text not found" /></label> 
							
					<input type="date" class="form-control inputs" id="birthDate"
						readonly="true"
						value="<% out.print(empl.getBirthdateToPage()); %>">
						
					<label><spring:message
							code="label_employee_salary" text="Text not found" /></label> 
							
					<input
						type="number" class="form-control inputs" id="salary"
						placeholder="<spring:message code="placeholder_employee_salary" text="Text not found" />"
						readonly="true"
						value="<% out.print(empl.getSalary()); %>">
						

					<label> <spring:message code="label_employee_active" text="Text not found" /> </label> 
					<input type="checkbox" disabled="true"
					<%if (!empl.getActive()) { %>
						checked="checked"
					<% } %>">

					<%}; %> 
					

		<%if (isCreate || isEdit) {%>      
        </div>
              <button type="button" class="btn myBtn" id="saveBtn"><spring:message code="btn_save" text="Text not found" /></button>
              <button type="button" class="btn myBtn" id="cancelBtn"><spring:message code="btn_cancel" text="Text not found" /></button>
                
        </div>
        <%} else {%>
        	</div>
              <button type="button" class="btn myBtn" id="okBtn"><spring:message code="btn_ok" text="Text not found" /></button>
               
        </div>
        <%}; %>
        </form>
    </div>
    
  </body>
</html>