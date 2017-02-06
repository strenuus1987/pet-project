$(document).ready(function() {

	function findDepartments(searchString){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "search_dep",
			data : "searchString=" + searchString,
			dataType : 'json',
			timeout : 100000,
			success : function(res) {
				if (res.errorMsg != undefined) {
					$("#errormsg_no_res").css('display', 'block');
					$("#div_dep_table").css('display', 'none');
				}
				else if(res.depList != undefined) {
					
					$("#div_dep_table").css('display', 'block');
					$("#dep_table_tbody").html(res.depList[0].replace(/%%%%/g, $("#text_for_edit").text()));
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	}
	
	$("#addBtn").click(function() {
		
		location.replace("department?create=true");
		
	});
	
	$(document).on('keypress', $('#depName'), function (e) {
		
		return (/[а-яА-ЯёЁa-zA-Z?*]+$/g.test(String.fromCharCode(e.charCode)));
		
	});
	
	$("#searchBtn").click(function() {
		
		$("#errormsg_no_res").css('display', 'none');
		
		findDepartments($("#depName").val().trim());
		
	});
	
});