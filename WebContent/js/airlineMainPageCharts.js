var dataSet = [];
$(document).ready(

		function() {

			var i = 0;

			$.ajax({
				traditional : true,
				type : "GET",
				url : "airline/weekFlightListJSON",
				dataType : "json",
				success : function(result, success) {
					if(result !== null){
						c3.generate({
							bindto : "#lineChart",
							data : {
								columns : [ [ "Week Flights", result.data.length,
										50, 60 ] ]
							},
							axis : {
								y : {
									label : {
										text : "Week Flights",
										position : "outer-middle"
									},
									tick : {
										count : 3,
									// ADD
									}
								},

								x : {
									label : {

										text : "Weeks",
										position : "outer-middle"
									},
									type : "category",
									tick : {
										count : 5,
									}
								}
							}
						});
					}
					
					$.ajax({
						traditional : true,
						type : "GET",
						url : "airline/pieFlightListJSON",
						dataType : "json",
						success : function(result, success) {
							for (i = 0; i < result.data.length; i++) {
								dataSet[i] = [ result.data[i].name,
										result.data[i].quantity ];
							}
							c3.generate({
								bindto : "#pieChart",
								data : {
									columns : dataSet,
									type : "pie"
								},

							});
						},
						error : function(xhr, ajaxOptions, thrownError) {

						}
					});
				},
				error : function(xhr, ajaxOptions, thrownError) {

				}
			});

		});