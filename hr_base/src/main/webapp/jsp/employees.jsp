<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="domain.Employee"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Employees</title>

    <!-- Bootstrap -->
    <link href="content/css/bootstrap.min.css" rel="stylesheet">
    <link href="content/css/list_style.css" rel="stylesheet">
   
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
    <script src="content/js/employees.js"></script>
    
</head>
<body>
    
    <% Boolean isEditor = false;
    if (request.getAttribute("isEditor") != null && (Boolean) request.getAttribute("isEditor") == true) {
    	isEditor = true;	
    }%>
    
    
    <div class="container">
        <div class="row">
            <div class="col-lg-11"><spring:message code="label_language" text="Text not found" /> : <a href="?locale=en"><spring:message code="label_languageEN" text="Text not found" /></a>|<a href="?locale=ru"><spring:message code="label_languageRU" text="Text not found" /></a><p></div>
            <div class="col-md-1" id="logout"><a href="auth?logout=true"><spring:message code="label_logout" text="Text not found" /></a></div>
        </div>
        <%if (isEditor) {%>
        <div class="row">
            <ul class="nav nav-pills nav-justified">
                <li role="presentation" class="active" id="empl"><a href="employees"><spring:message code="label_employees" text="Text not found" /></a></li>
                <li role="presentation"><a href="departments"><spring:message code="label_departments" text="Text not found" /></a></li>
            </ul>
        </div>
        <%}; %>
        <div class="row">
            <div class="col-lg-10">
                <form class="form-inline">
                    <div class="form-group">
                        <input type="text" class="form-control inputs" id="firstName"
                        <% if (request.getAttribute("searchStringName") != null) {out.print("value=\"" + request.getAttribute("searchStringName") + "\"");}%> 
                        placeholder=<spring:message code="placeholder_employees_first_name" text="Text not found"/>>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control inputs" id="lastName" 
                        <% if (request.getAttribute("searchStringSurname") != null) {out.print("value=\"" + request.getAttribute("searchStringSurname") + "\"");}%>
                        placeholder=<spring:message code="placeholder_employees_last_name" text="Text not found" />>
                    </div>
                    <button id="searchBtn" type="button" class="btn myBtn"><spring:message code="btn_search" text="Text not found" /></button>
                </form>
            </div>
            <%if (isEditor) {%>
            <div class="col-lg-2" id="lastCol">
                <button type="button" class="btn myBtn" id="addBtn"><spring:message code="btn_add_new_user" text="Text not found" /></button>
            </div>  
            <%}; %>
        </div>
        <div class="row">
        
        	<label id="text_for_edit" style="display: none"><spring:message code="table_edit" text="Text not found" /></label>
        	
        	<p id="errormsg_no_res" class="error_message"> <spring:message code="error_no_data" text="Text not found" /> </p>
        
            <div class="table table-responsive" id="div_empl_table"> 
                <table id="emplTable">
                	<thead>
                    <tr>
                      <th><spring:message code="table_employees_first_name" text="Text not found" /></th>
                      <th><spring:message code="table_employees_last_name" text="Text not found" /></th>
                      <th><spring:message code="table_employees_department" text="Text not found" /></th>
                      <th><spring:message code="table_employees_birth_date" text="Text not found" /></th>
                      <th><spring:message code="table_employees_salary" text="Text not found" /></th>
                      <%if (isEditor) {%>
                      <th><spring:message code="table_edit" text="Text not found" /></th>
                      <%}; %>
                    </tr>
                    </thead>
                    <tbody id="emp_table_tbody">
                    <%if (request.getAttribute("emplList") != null) {
                    
                    	List<Employee> empl = (List<Employee>)request.getAttribute("emplList");
                    	
                    	for(Employee employee: empl){%>
                    		
                    		<tr>
                    		
                    		<%out.println(employee.toPage(isEditor)); %>
                    		
                    		<%if (isEditor) {%>
                      			<td><a href="employee?edit=true&id=<% out.print(employee.getId()); %> "><spring:message code="table_edit" text="Text not found" /></a></td>
                      			
                      		<%} %>
                    		</tr>
            				
                    	<%}
                    	
                    }%>
                    
                    </tbody>
                      
                </table>
            </div> 
            
        </div>
    </div>
    
  </body>
</html>