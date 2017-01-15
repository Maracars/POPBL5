
var dataSet = [];
$(document).ready(
		function() {

			$.ajax({
				traditional : true,
				type : "GET",
				url : "weekFlightListJSON",
				dataType : "json",
				success : function(result, success) {
					c3.generate({
						bindto : '#lineChart',
						data : {
							columns : [ [ 'Week Flights', result.data.length,
									50, 60 ] ]
						},
						axis : {
							y : {
								label : {
									text : 'Week Flights',
									position : 'outer-middle'
								},
								tick : {
									count : 3,
								// ADD
								}
							},

							x : {
								label : {

									text : 'Weeks',
									position : 'outer-middle'
								},
								type : 'category',
								tick : {
									count : 5,
								}
							}
						}
					});
					$.ajax({
						traditional : true,
						type : "GET",
						url : "pieFlightListJSON",
						dataType : "json",
						success : function(result, success) {
							for(i = 0; i < result.data.length; i++){
								dataSet[i] = [result.data[i].name, result.data[i].quantity];
								console.log(dataSet[i]);
							}
							c3.generate({
								bindto : '#pieChart',
								data : {
									columns : dataSet,
									type : 'pie'
								},
								
							});
						},
						error : function(xhr, ajaxOptions, thrownError) {
							alert(xhr.status);
							alert(thrownError);
						}
					});
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
					alert(thrownError);
				}
			});

		});