$(document).ready(function() {

	function findEmployees(searchStringName, searchStringSurname){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "search_empl",
			data : "searchStringName=" + searchStringName + "&searchStringSurname=" + searchStringSurname,
			dataType : 'json',
			timeout : 100000,
			success : function(res) {
				if (res.errorMsg != undefined) {
					$("#errormsg_no_res").css('display', 'block');
					$("#div_empl_table").css('display', 'none');
				}
				else if(res.emplList != undefined) {
					
					$("#div_empl_table").css('display', 'block');
					$("#emp_table_tbody").html(res.emplList[0].replace(/%%%%/g, $("#text_for_edit").text()));
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	}
	
	$("#addBtn").click(function() {
		
		location.replace("employee?create=true");
		
	});
	
	$(document).on('keypress', $('#firstName'), function (e) {
		
		return (/[а-яА-ЯёЁa-zA-Z?*]+$/g.test(String.fromCharCode(e.charCode)));
		
	});
	
	$(document).on('keypress', $('#lastName'), function (e) {
		
		return (/[а-яА-ЯёЁa-zA-Z?*]+$/g.test(String.fromCharCode(e.charCode)));
		
	});
	
	$("#searchBtn").click(function() {
		
		$("#errormsg_no_res").css('display', 'none');
		
		findEmployees($("#firstName").val().trim(), $("#lastName").val().trim(), 0);
		
	});
	
});