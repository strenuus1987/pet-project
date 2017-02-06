$(document).ready(function() {

	function saveData(name, action, id){
		$.ajax({
			type : "POST",
			url : "department_edit",
			data : "name=" + name + "&action=" + action + "&id=" + id,
			dataType : 'json',
			success : function(res) {
				if (res.errorMsg != undefined) {
					$("#errormsg_exist_dep").css('display', 'block')
				}
				else {
					location.replace("departments");
				};
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	}
	
	$("#saveBtn").click(function() {
		
		$("#errormsg_exist_dep").css('display', 'none');
		$("#errormsg_empty_dep").css('display', 'none');
		
		if ($("#name").val().trim() == "") {
			$("#errormsg_empty_dep").css('display', 'block');
		}
		else {
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
				
				saveData($("#name").val().trim(), action, "");
				
			}
			else if (action == "edit" && id != undefined) {
				
				saveData($("#name").val().trim(), action, id);
				
			}
		}
		
	});
	
	$("#cancelBtn").click(function() {
		
		var source = getUrlVars()["source"];
		
		if (source == undefined) {
			source = "departments";
		}
		
		location.replace(source);
		
	});

	$("#okBtn").click(function() {
	
		var source = getUrlVars()["source"];
		
		if (source == undefined) {
			source = "departments";
		}
		
		location.replace(source);
	
	});
	
	function getUrlVars() {
	    var vars = {};
	    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
	        vars[key] = value;
	    });
	    return vars;
	}
	
});