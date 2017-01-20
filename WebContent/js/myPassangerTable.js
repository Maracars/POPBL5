$(document)
		.ready(
				function() {
					$("#flightsTable")
							.dataTable(
									{
										"processing" : true,
										"serverSide" : true,
										"ajax" : {
											"url" : "myFlightListJSON",
											"type" : "POST"
										},
										"columnDefs" : [ {
											"targets" : 0,
											"orderable" : false
										}, {
											"width" : "120px",
											"targets" : 0
										} ],
										"columns" : [
												{
													"data" : "source"
												},
												{
													"data" : "destination"
												},
												{
													"data" : "departureDate"
												},
												{
													"data" : "price"
												},
												{
													"data" : "planeInfo"
												},
												{
													"data" : "flightId",
													"render" : function(data,
															type, full, meta) {
														if (data == "0") {
															return '<div class="btn btn-primary disabled"><i class="fa fa-times"></i></div>'
														} else {
															return '<div><button type="submit" class="btn btn-primary" value='
																	+ data
																	+ ' name="flightId"><i class="fa fa-credit-card"></i></button></div>'
														}
													}
												} ]
									});

					var table = $("#flightsTable").DataTable();

					var originColumn = table.column(0);
					var destinationColumns = table.column(1);

					$("#originSelect").on("change", function() {
						var val = $(this).find(":selected").text();

						originColumn.search(val).draw();
					});

					$("#destinationSelect").on("change", function() {
						var val = $(this).find(":selected").text();

						destinationColumns.search(val).draw();
					});

				});