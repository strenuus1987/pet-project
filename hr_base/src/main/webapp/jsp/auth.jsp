<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Authentification</title>
    
    <link rel="stylesheet" href="content/css/auth_style.css">

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script type="text/javascript" src="content/js/auth.js"></script>
</head>

<body>
	<p class="lang"><spring:message code="label_language" text="Text not found" /> : <a href="?locale=en"><spring:message code="label_languageEN" text="Text not found" /></a>|<a href="?locale=ru"><spring:message code="label_languageRU" text="Text not found" /></a><p>
	<div id="login">  
        <form>
            <fieldset class="clearfix">
                <p>
                <span class="fontawesome-user"></span>
                <input id="login_input" type="text" placeholder=<spring:message code="auth_input_username_placeholder" text="Text not found" />>
                </p>
                
                <p>
                <span class="fontawesome-lock"></span>
                <input type="password" id="login_pass" placeholder=<spring:message code="auth_input_password_placeholder" text="Text not found" />>
                </p>
                
                <p id="errormsg_empty_login" class="error_message"> <spring:message code="auth_error_empty_user" text="Text not found" /> </p>
                <p id="errormsg_unacceptable_symbols_login" class="error_message"> <spring:message code="auth_error_unacceptable_symbols_user" text="Text not found" /> </p>
                <p id="errormsg_empty_pass" class="error_message"> <spring:message code="auth_error_empty_pass" text="Text not found" /> </p>
                <p id="errormsg_unacceptable_symbols_pass" class="error_message"> <spring:message code="auth_error_unacceptable_symbols_pass" text="Text not found" /> </p>
                <p id="errormsg_no_user" class="error_message"> <spring:message code="auth_error_no_user" text="Text not found" /> </p>
                
                <p><input type="button" id="login_btn" value=<spring:message code="auth_button_value" text="Text not found" />></p>
            </fieldset>
        </form>
    </div>
</body>
</html>