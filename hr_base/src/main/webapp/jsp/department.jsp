<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="domain.Department"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Department</title>

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
    <script src="content/js/department.js"></script>
    
</head>
<body>
    
    <%
    Boolean isCreate = false;
    Boolean isEdit = false;
    Department dep = null;
    if (request.getAttribute("create") != null && (Boolean) request.getAttribute("create") == true) {
    	isCreate = true;
    }
    if (request.getAttribute("edit") != null && (Boolean) request.getAttribute("edit") == true) {
    	isEdit = true;
    }
    if (request.getAttribute("department") != null) {
    	dep = (Department) request.getAttribute("department");
    }
    %>
    
    <div class="container">
        <div class="row">
            <div class="col-lg-11"><spring:message code="label_language" text="Text not found" /> : 
            <a href="?locale=en<% if (isCreate) {
            	out.print("&create=true");
            }
            else if (isEdit && dep != null) {
            	out.print("&edit=true&id=" + dep.getId());
            }
            %>
            "><spring:message code="label_languageEN" text="Text not found" /></a>|
            <a href="?locale=ru<% if (isCreate) {
            	out.print("&create=true");
            }
            else if (isEdit && dep != null) {
            	out.print("&edit=true&id=" + dep.getId());
            }
            %>
            "><spring:message code="label_languageRU" text="Text not found" /></a><p></div>
            <div class="col-md-1" id="logout"><a href="auth?logout=true"><spring:message code="label_logout" text="Text not found" /></a></div>
        </div>
        <div class="row">
            <form>
              
				<%if (dep != null) { %>
              	<legend>
              	<%out.print(dep.getName()); %>
              	</legend>
              	<%} %>

              <div class="form-group">
              
 				<%if (isCreate) {%>
              	
              	  <label><spring:message code="label_department" text="Text not found" /></label>
                  <input type="text" class="form-control inputs" id="name" placeholder=<spring:message code="placeholder_departments_name" text="Text not found" />>
                
                <% } else if (isEdit && dep != null){ %>
              	
              	<label><spring:message code="label_department" text="Text not found" /></label>
                  <input type="text" value="<% out.print(dep.getName()); %>" class="form-control inputs" id="name" placeholder=<spring:message code="placeholder_departments_name" text="Text not found" />>
                
              	
              	<% } else { %>
              
                  <label><spring:message code="label_department" text="Text not found" /></label>
                  <input type="text" value="<% out.print(dep.getName()); %>" class="form-control inputs" id="name" placeholder=<spring:message code="placeholder_departments_name" text="Text not found" /> readonly="true">
                
                <%}; %>  
                  
                <p id="errormsg_exist_dep" class="error_message"> <spring:message code="dep_error_exist_dep" text="Text not found" /> </p>
   				<p id="errormsg_empty_dep" class="error_message"> <spring:message code="dep_error_empty_name" text="Text not found" /> </p>
                  
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