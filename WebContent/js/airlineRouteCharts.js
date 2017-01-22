var dataSet = [];
var cat = [];

$(document).ready(function() {
	var airlineId = 774;
	
	var serialNumber = $('#planeSerial').val();
	var i = 0;

	$.ajax({
		traditional : true,
		type : "GET",
		url : "routeListJSON",
		dataType : "json",
		success : function(result, success) {
			for (i = 0; i < result.data.length; i++) {
				dataSet[i] = [ result.data[i].name, result.data[i].quantity ];
				cat[i] = result.data[i].name;
			}
			console.log(dataSet);
			var chart = c3.generate({
				bindto : "#barChartStats",

			
			    data: {
			        columns: dataSet,
			        type: 'bar',
			    
			        order: 'desc' // stack order by sum of values descendantly. this is default.
//			      order: 'asc'  // stack order by sum of values ascendantly.
//			      order: null   // stack order by data definition.
			    },
			    grid: {
			        y: {
			            lines: [{value:0}]
			        }
			    },
			    axis : {
					y : {
						label: "Flight Hours"
					},
					x : {
						
						label : "Route Name",
						
					}
				}
			});
		},
		error : function(xhr, ajaxOptions, thrownError) {
			
		}
	});

});