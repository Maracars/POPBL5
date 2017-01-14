$(document).ready(
		function() {

			$.ajax({
				traditional : true,
				type : "GET",
				url : "weekFlightListJSON",
				dataType : "json",
				success : function(result, success) {
					c3.generate({
						bindto : '#barChart',
						data : {
							columns : [ [ 'week Flights', result.data.length,
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
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
					alert(thrownError);
				}
			});

		});