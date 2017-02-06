$(document).ready(function() {

	function saveData(action, id, name, surname, dep, bdate, salary, fired){
		$.ajax({
			type : "POST",
			url : "employee_edit",
			data : "action=" + action + "&id=" + id + "&name=" + name + "&surname=" + surname + "&dep=" + dep + "&bdate=" + bdate + "&salary=" + salary + "&fired=" + fired,
			dataType : 'json',
			success : function(res) {
				location.replace("employees");
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	}
	
	$("#saveBtn").click(function() {
		
		var errorMsg = false;
		
		$("#errormsg_salary").css('display', 'none');
		$("#errormsg_date").css('display', 'none');
		$("#errormsg_empty_surname").css('display', 'none');
		$("#errormsg_unacceptable_surname").css('display', 'none');
		$("#errormsg_empty_name").css('display', 'none');
		$("#errormsg_unacceptable_name").css('display', 'none');
		$("#errormsg_empty_date").css('display', 'none');
		$("#errormsg_empty_salary").css('display', 'none');
		
		
		if ($("#firstName").val().trim() == "") {
			$("#errormsg_empty_name").css('display', 'block');
			errorMsg = true;
		}
		else if (!/^[а-яА-ЯёЁa-zA-Z]+$/g.test($("#firstName").val())) {
			$("#errormsg_unacceptable_name").css('display', 'block');
			errorMsg = true;
		}
		
		if($("#lastName").val().trim() == "") {
			$("#errormsg_empty_surname").css('display', 'block');
			errorMsg = true;
		}
		else if (!/^[а-яА-ЯёЁa-zA-Z]+$/g.test($("#lastName").val())) {
			$("#errormsg_unacceptable_surname").css('display', 'block');
			errorMsg = true;
		}
		
		if($("#birthDate").val().trim() == "") {
			$("#errormsg_empty_date").css('display', 'block');
			errorMsg = true;
		} 
		else {
			var birthdate = new Date($("#birthDate").val());
			var year = birthdate.getFullYear();
			if (year < 1900 || year > new Date().getFullYear()) {
				$("#errormsg_date").css('display', 'block');
				errorMsg = true;
			}
		}
		
		if($("#salary").val().trim() == "") {
			$("#errormsg_empty_salary").css('display', 'block');
			errorMsg = true;
		}
		else if ($("#salary").val() <= 0){
			$("#errormsg_salary").css('display', 'block');
			errorMsg = true;
		}
		
		var fired = false;
		if ($("#fired:checked").val() != undefined) {
			fired = true;
		}
		
		if (!errorMsg) {
			var action;
			var id;
			
			if (getUrlVars()["edit"] != undefined) {
				action = "edit";
				if (getUrlVars()["id"] != undefined) {
					id = getUrlVars()["id"];
				}
			}
			else if(getUrlVars()["create"] != undefined) {
				action = "create";
			}
			
			if (action == "create") {
				
				saveData(action, "", $("#firstName").val(), $("#lastName").val(), $("#department option:selected").val(), $("#birthDate").val(), $("#salary").val(), "");
				
			}
			else if (action == "edit" && id != undefined) {
				
				saveData(action, id, $("#firstName").val(), $("#lastName").val(), $("#department option:selected").val(), $("#birthDate").val(), $("#salary").val(), fired);
				
			}
		}
				
	});
	
	$(document).on('input', $('#salary'), function () {
				
		if ($("#salary").val().indexOf(".") != '-1') {
			$("#salary").val($("#salary").val().substring(0, $("#salary").val().indexOf(".") + 3));
		}
		
	});
	
	$(document).on('keypress', $('#salary'), function (e) {
		
		if ($("#salary").val().indexOf(".") != '-1' || $("#salary").val().indexOf(",") != '-1') {
			return !(/[.,]/.test(String.fromCharCode(e.charCode)));
		}
		
		return !(/[-+]/.test(String.fromCharCode(e.charCode)));
		
	});
	
	$("#cancelBtn").click(function() {
		
		location.replace("employees");
		
	});
	
	$("#okBtn").click(function() {
		
		location.replace("employees");
	
	});
	
	function getUrlVars() {
	    var vars = {};
	    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
	        vars[key] = value;
	    });
	    return vars;
	};
	
});