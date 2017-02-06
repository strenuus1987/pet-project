<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="domain.Department"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Departments</title>

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
    <script src="content/js/departments.js"></script>
    
</head>
<body>
    
    <div class="container">
        <div class="row">
            <div class="col-lg-11"><spring:message code="label_language" text="Text not found" /> : <a href="?locale=en"><spring:message code="label_languageEN" text="Text not found" /></a>|<a href="?locale=ru"><spring:message code="label_languageRU" text="Text not found" /></a><p></div>
            <div class="col-md-1" id="logout"><a href="auth?logout=true"><spring:message code="label_logout" text="Text not found" /></a></div>
        </div>
        <div class="row">
            <ul class="nav nav-pills nav-justified">
                <li role="presentation" id="empl"><a href="employees"><spring:message code="label_employees" text="Text not found" /></a></li>
                <li role="presentation" class="active"><a href="departments"><spring:message code="label_departments" text="Text not found" /></a></li>
            </ul>
        </div>
        <div class="row">
            <div class="col-lg-10">
                <form class="form-inline">
                    <div class="form-group">
                        <input type="text" class="form-control inputs" id="depName" 
                        <% if (request.getAttribute("searchString") != null) {out.print("value=\"" + request.getAttribute("searchString") + "\"");}%>
                        placeholder=<spring:message code="placeholder_departments_name" text="Text not found"/>>
                    </div>
                    <button id="searchBtn" type="button" class="btn myBtn"><spring:message code="btn_search" text="Text not found"/></button>
                </form>
            </div>
            <div class="col-lg-2" id="lastCol">
                <button type="button" class="btn myBtn" id="addBtn"><spring:message code="btn_add_new_department" text="Text not found"/></button>
            </div>  
        </div>
        <div class="row">
        	
        	<label id="text_for_edit" style="display: none"><spring:message code="table_edit" text="Text not found" /></label>
        	
        	<p id="errormsg_no_res" class="error_message"> <spring:message code="error_no_data" text="Text not found" /> </p>
        		
            <div class="table table-responsive" id="div_dep_table"> 
                <table>
                    <thead>
                    <tr>
                      <th><spring:message code="table_departments_name" text="Text not found"/></th>
                      <th><spring:message code="table_edit" text="Text not found"/></th>
                    </tr>
                    </thead>
              
              		<tbody id="dep_table_tbody">
                  
                  		<%if (request.getAttribute("depList") != null) {
                    
                    	List<Department> dept = (List<Department>)request.getAttribute("depList");
                    	
                    	for(Department department: dept){%>
                    		
                    		<tr>
                    		
                    		<%out.println(department.toPage("departments")); %>
                    		
                    		<td><a href="department?edit=true&id=<% out.print(department.getId()); %> "><spring:message code="table_edit" text="Text not found" /></a></td>
                      			
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