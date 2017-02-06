$(document).ready(function() {

	function setData(username, password){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "auth",
			data : "username=" + username + "&password=" + password,
			dataType : 'json',
			timeout : 100000,
			success : function(res) {
				if (res.url != "" && res.url != undefined) location.replace(res.url);
				if (res.errorMsg != undefined && res.errorMsg) $("#errormsg_no_user").css('display', 'block');
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	}
	
	$("#login_btn").click(function() {
		
		var errorLogin = false;
		var errorPass = false;
		
		$("#login_input").css("background-color", "#3b4148");
		$("#login_pass").css("background-color", "#3b4148");
		
		$("#errormsg_no_user").css('display', 'none');
		
		$("#errormsg_empty_login").css('display', 'none');
		$("#errormsg_unacceptable_symbols_login").css('display', 'none');
		
		$("#errormsg_empty_pass").css('display', 'none');
		$("#errormsg_unacceptable_symbols_pass").css('display', 'none');
		
		
		if ($("#login_input").val() == '') {
			$("#login_input").css("background-color", "orange");
			$("#errormsg_empty_login").css('display', 'block');
			errorLogin = true;
		}
		else if(/[0-9а-яА-ЯёЁ!@#$%^&*\s]/g.test($("#login_input").val())) {
			$("#login_input").css("background-color", "orange");
			$("#errormsg_unacceptable_symbols_login").css('display', 'block');
			errorLogin = true;
		}
		else {
			errorLogin = false;
		}
		
		if ($("#login_pass").val() == '') {
			$("#login_pass").css("background-color", "orange");
			$("#errormsg_empty_pass").css('display', 'block');
			errorPass = true;
		}
		else if(/[а-яА-ЯёЁ!@#$%^&*\s]/g.test($("#login_pass").val())) {
			$("#login_pass").css("background-color", "orange");
			$("#errormsg_unacceptable_symbols_pass").css('display', 'block');
			errorPass = true;
		}
		else {
			errorPass = false;
		}
		
		if (!(errorLogin || errorPass)) {
			setData($("#login_input").val(), $("#login_pass").val());
		}
		
	});
	
});