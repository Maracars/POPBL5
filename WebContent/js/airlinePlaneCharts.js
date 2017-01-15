var dataSet = [];
$(document).ready(function() {

	$.ajax({
		traditional : true,
		type : "POST",
		url : "airline/barPlaneHoursJSON",
		dataType : "json",
		data : {
			serial : "JAJAJAJA",

		},

		success : function(result, success) {
			for (i = 0; i < result.data.length; i++) {
				dataSet[i] = [ result.data[i].name, result.data[i].quantity ];
			}
			c3.generate({
				bindto : '#barChart',
				data : {
					columns : dataSet,
					type : 'bar'
				},
			});
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
	});

});