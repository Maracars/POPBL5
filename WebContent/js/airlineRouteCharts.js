var dataSet = [];
var cat = [];

$(document).ready(function() {
	var airlineId = 774;
	
	var serialNumber = $('#planeSerial').val();
	var i = 0;

	$.ajax({
		traditional : true,
		type : "GET",
		url : "airline/routeListJSON",
		dataType : "json",
		success : function(result, success) {
			for (i = 0; i < result.data.length; i++) {
				dataSet[i] = [ result.data[i].name, result.data[i].quantity ];
				cat[i] = result.data[i].name;
			}
			c3.generate({
				bindto : "#barChartStats",
				data : {
					columns : dataSet,
					type : "bar"
				},
				axis : {
					y : {
						label: "Flight Hours"
					},
					x : {
						type : "categories",
						categories : cat,
						label : "Route Name"
					}
				}
			});
		},
		error : function(xhr, ajaxOptions, thrownError) {
			
		}
	});

});