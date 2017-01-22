var socket = io.connect("http://maracars.sytes.net:9092");//http://maracars.sytes.net:9092

var map = null;
var centerPos = [ -0.461389, 51.4775 ];
var vectorLayer;
var select;
var planeId;
var vectorSource = new ol.source.Vector({
// create empty vector
});

var planes = [];
var pendingMoves = [];
var overlay;
var counter = 0;
var steps = 1000;
var iconStyle = {
	anchor : [ 0.5, 0.5 ],
	anchorXUnits : "fraction",
	anchorYUnits : "pixels",
	opacity : 1,
	rotateWithView : true,
	size : [ 32, 48 ],

	src : "/Naranair/rsc/img/miniplane.png"

};

$(document)
		.ready(
				function() {

					socket.on("chatevent", function(jsonData) {
						var data = JSON.parse(jsonData);
						setTimeNow();
						var pending;
						pending = checksNextPendingMoveOfPlane(data.id);
						data.moveId = counter++;
						pendingMoves.push(data);
						if (pending === null) {
							movePlane(data);

						}

					});
					function movePlane(data) {
						var beforeCoord;
						var latStep;
						var longStep;
						var featureToUpdate = null;

						featureToUpdate = vectorSource.getFeatureById(data.id);

						if (featureToUpdate === null) {
							featureToUpdate = new ol.Feature({
								geometry : new ol.geom.Point(ol.proj.transform(
										[ data.positiony, data.positionx ],
										"EPSG:4326", "EPSG:3857"))
							});
							featureToUpdate.setStyle(new ol.style.Style({
								image : new ol.style.Icon(iconStyle)
							}));
							featureToUpdate.setId(data.id);
							vectorSource.addFeature(featureToUpdate);
						}
						beforeCoord = getLastPlanePosition(data.id);
						latStep = (data.positionx - beforeCoord.positionx)
								/ steps;
						longStep = (data.positiony - beforeCoord.positiony)
								/ steps;
						var d = new Date();
						var n = d.getTime();
						simulateMovement(featureToUpdate, latStep, longStep,
								beforeCoord, data, n);

					}

					function checksNextPendingMoveOfPlane(id) {
						for (var int = 0; int < pendingMoves.length; int++) {
							if (pendingMoves[int].id === id) {
								pendingMoves[int].index = int;
								return pendingMoves[int];
							}
						}
						return null;
					}
					function simulateMovement(featureToUpdate, latStep,
							longStep, beforeCoord, data, time) {
						var plane = getLastPlanePosition(data.id);
						var sleepTime = (time - plane.time) / steps;
						for (var int = 1; int <= steps; int++) {
							setTimeout(f, int * 10, int,
									featureToUpdate, latStep, longStep,
									beforeCoord, data, time);
						}
					}

					function f(int, featureToUpdate, latStep, longStep,
							beforeCoord, data, time) {
						var long = beforeCoord.positiony + longStep * int;
						var lat = beforeCoord.positionx + latStep * int;
						featureToUpdate.getGeometry().setCoordinates(
								getPointFromLongLat(long, lat));

						if (data.id == planeId) {
							overlay.setPosition(getPointFromLongLat(long, lat));
						}

						featureToUpdate.getStyle().getImage().setRotation(
								getRotation(beforeCoord.positionx,
										beforeCoord.positiony, data.positionx,
										data.positiony));
						if (int === steps) {
							var momentMove = checksNextPendingMoveOfPlane(data.id);
							pendingMoves.splice(momentMove.index, 1);
							var nextMove = checksNextPendingMoveOfPlane(data.id);
							changePlanePosition(data.id, long, lat, time);
							if (nextMove !== null) {
								movePlane(nextMove);
							}
						}

					}

					function getRotation(bx, by, ax, ay) {
						var dx = ax - bx;
						var dy = ay - by;
						if (dy === 0 && dx < 0) {
							return Math.PI;
						} else if (dx === 0 && dy > 0) {
							return (Math.PI) / 2;
						} else if (dx === 0 && dy < 0) {
							return 3 * (Math.PI) / 2;
						}
						return 0;
					}

					function getLastPlanePosition(id) {

						for (var int = 0; int < planes.length; int++) {
							if (planes[int].id === id) {
								return planes[int];
							}
						}
					}
					function changePlanePosition(id, posy, posx, time) {

						for (var int = 0; int < planes.length; int++) {
							if (planes[int].id === id) {
								planes[int].positionx = posx;

								planes[int].positiony = posy;
								planes[int].time = time;
							}
						}
					}

					function getPointFromLongLat(long, lat) {
						return ol.proj.transform([ long, lat ], "EPSG:4326",
								"EPSG:3857");
					}
					function getOriginLongLat(long, lat) {
						return ol.proj.transform([ long, lat ], "EPSG:3857",
								"EPSG:4326");
					}

					$
							.get(
									"/Naranair/controller/getFlights",
									function(data, status) {
										var obj = jQuery.parseJSON(data);
										planes = obj.result[0];
										var d = new Date();
										var n = d.getTime();

										for (var i = 0; i < planes.length; i++) {
											planes[i].positionx = planes[i].planeMovement.positionX;
											planes[i].positiony = planes[i].planeMovement.positionY;
											planes[i].time = n;

											if (planes[i].planeStatus.positionStatus !== "ARRIVING") {
												var iconFeature = new ol.Feature(
														{
															geometry : new ol.geom.Point(
																	ol.proj
																			.transform(
																					[
																							planes[i].planeMovement.positionY,
																							planes[i].planeMovement.positionX ],
																					"EPSG:4326",
																					"EPSG:3857"))
														});
												iconFeature
														.setStyle(new ol.style.Style(
																{
																	image : new ol.style.Icon(
																			iconStyle)
																}));
												iconFeature.setId(planes[i].id);
												vectorSource
														.addFeature(iconFeature);
											}

										}
										console.log(planes);

										// add the feature vector to the layer
										// vector, and apply a style
										// to whole layer
										vectorLayer = new ol.layer.Vector({
											source : vectorSource,
										});
										initMap();

									}, "json");

					function setTimeNow() {
						var d = new Date();
						var n = d.getTime();
						for (var int = 0; int < planes.length; int++) {
							if (planes[int].planeStatus.positionStatus === "ON AIRPORT"
									|| planes[int].planeStatus.positionStatus === "ARRIVING") {
								planes[int].time = n;
							}
						}
					}

					function initMap() {

						var coordinate;

						$("#map")
								.ready(
										function() {
											if (vectorLayer === undefined
													|| planes.length === 0) {
												map = new ol.Map(
														{
															target : 'map',
															layers : [ new ol.layer.Tile(
																	{
																		source : new ol.source.OSM()
																	}) ],
															view : new ol.View(
																	{
																		center : ol.proj
																				.fromLonLat(centerPos),
																		zoom : 14
																	})
														})
											} else {
												map = new ol.Map(
														{
															target : 'map',
															layers : [
																	new ol.layer.Tile(
																			{
																				source : new ol.source.OSM()
																			}),
																	vectorLayer ],
															view : new ol.View(
																	{
																		center : ol.proj
																				.fromLonLat(centerPos),
																		zoom : 14
																	})
														})
											}

											select = new ol.interaction.Select(
													{
														condition : ol.events.condition.click,
														multi : true
													});

											overlay = new ol.Overlay(
													{
														element : document
																.getElementById("planeInfo")
													});

											map.on("click", function(evt) {
												coordinate = evt.coordinate;
											})

											map.addInteraction(select);

											select
													.on(
															"select",
															function(e) {
																var element = overlay
																		.getElement();
																if (e.target
																		.getFeatures()
																		.getLength() > 0) {
																	e.target
																			.getFeatures()
																			.forEach(
																					function(
																							feature) {
																						planeId = feature
																								.getId();
																					})
																	var plane = getLastPlanePosition(planeId);

																	overlay
																			.setPosition(coordinate);
																	map
																			.addOverlay(overlay);
																	$(element)
																			.popover(
																					"destroy");
																	$(element)
																			.popover(
																					{
																						"placement" : "top",
																						"animation" : false,
																						"title" : plane.serial,
																						"html" : true,
																						"content" : "<ul class='list-unstyled'>" +
																						"<li><b>Plane model: </b>" + plane.model.planeMaker.name + ", " + plane.model.name +
																						"</li><li><b>Lat: </b>" + plane.positiony +
																						
																						"</li><li><b>Long: </b>" + plane.positionx +
																						"</li><li><b>Position status: </b>" + plane.planeStatus.positionStatus +
																						"</li><li><b>Technical status: </b>" + plane.planeStatus.technicalStatus +

																						"</li></ul>",
																					});
																	$(element)
																			.popover(
																					"show");
																} else {
																	$(element)
																			.popover(
																					"destroy");
																}
															});

										});

					}

				});

function changeTerminalZoom(longitude, latitude, type) {

	if (String(type) == "terminal") {
		map.getView().animate(
				{
					center : ol.proj.fromLonLat([ parseFloat(latitude),
							parseFloat(longitude) ]),
					duration : 2000,
					zoom : 16
				});
	} else if (String(type) == "gate") {
		map.getView().animate(
				{
					center : ol.proj.fromLonLat([ parseFloat(latitude),
							parseFloat(longitude) ]),
					duration : 2000,
					zoom : 19
				});
	}

}
