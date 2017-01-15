var socket = io.connect("http://localhost:9092");

var map = null;
var centerPos = [ -0.461389, 51.4775 ];
var vectorLayer;
var vectorSource = new ol.source.Vector({
// create empty vector
});

var planes = [];
var pendingMoves = [];
var counter = 0;
var steps = 1000;
var iconStyle = {
	anchor : [ 0.5, 46 ],
	anchorXUnits : 'fraction',
	anchorYUnits : 'pixels',
	opacity : 1,
	rotateWithView : true,

	src : 'rsc/img/plane-icon.png'

}

$(document).ready(
		function() {

			socket.on("chatevent", function(jsonData) {
				var data = JSON.parse(jsonData);
				var pending;
				console.log(pendingMoves);
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
						geometry : new ol.geom.Point(ol.proj.transform([
								data.positiony, data.positionx ], 'EPSG:4326',
								'EPSG:3857'))
					});
					featureToUpdate.setStyle(new ol.style.Style({
						image : new ol.style.Icon(iconStyle)
					}));
					featureToUpdate.setId(data.id);
					vectorSource.addFeature(featureToUpdate);
				}
				var beforeCoordWithoutChange = vectorSource.getFeatureById(
						data.id).getGeometry().getCoordinates();
				beforeCoord = getOriginLongLat(beforeCoordWithoutChange[1],
						beforeCoordWithoutChange[0]);

				var afterCoord = getPointFromLongLat(data.positionx,
						data.positiony);
				latStep = (data.positionx - beforeCoord[0]) / steps;
				longStep = (data.positiony - beforeCoord[1]) / steps;
				simulateMovement(featureToUpdate, latStep, longStep,
						beforeCoord, data);

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
			function simulateMovement(featureToUpdate, latStep, longStep,
					beforeCoord, data) {
				for (var int = 1; int <= steps; int++) {
					setTimeout(f, 10 * int, int, featureToUpdate, latStep,
							longStep, beforeCoord, data);
				}
			}

			function f(int, featureToUpdate, latStep, longStep, beforeCoord,
					data) {
				var long = beforeCoord[1] + longStep * int;
				var lat = beforeCoord[0] + latStep * int
				featureToUpdate.getGeometry().setCoordinates(
						getPointFromLongLat(long, lat));
				console.log(beforeCoord);
				console.log(data)
				console.log(Math.atan((beforeCoord[1] - data.positiony)
						/ (beforeCoord[0] - data.positionx)));

				featureToUpdate.getStyle().getImage().setRotation(
						Math.atan((beforeCoord[1] - data.positiony)
								/ (beforeCoord[0] - data.positionx)));
				if (int === steps) {
					var momentMove = checksNextPendingMoveOfPlane(data.id);
					pendingMoves.splice(momentMove.index, 1);
					var nextMove = checksNextPendingMoveOfPlane(data.id);
					if (nextMove !== null) {
						movePlane(nextMove);
					}
				}

			}

			function getPointFromLongLat(long, lat) {
				return ol.proj.transform([ long, lat ], 'EPSG:4326',
						'EPSG:3857');
			}
			function getOriginLongLat(long, lat) {
				return ol.proj.transform([ long, lat ], 'EPSG:3857',
						'EPSG:4326');
			}

			$.get("/Naranair/controller/getFlights", function(data, status) {
				var obj = jQuery.parseJSON(data);
				planes = obj.result[0];

				for (var i = 0; i < planes.length; i++) {
					if (planes[i].planeStatus.positionStatus !== "ARRIVING") {
						var iconFeature = new ol.Feature({
							geometry : new ol.geom.Point(ol.proj.transform([
									planes[i].planeMovement.positionX,
									planes[i].planeMovement.positionY ],
									'EPSG:4326', 'EPSG:3857'))
						});
						iconFeature.setStyle(new ol.style.Style({
							image : new ol.style.Icon(iconStyle)
						}));
						iconFeature.setId(planes[i].id);
						vectorSource.addFeature(iconFeature);
					}

				}

				// add the feature vector to the layer
				// vector, and apply a style
				// to whole layer
				vectorLayer = new ol.layer.Vector({
					source : vectorSource,
				});
				initMap();

			}, "json");

			function initMap() {

				$('#map').ready(function() {
					if (vectorLayer === undefined || planes.length === 0) {
						map = new ol.Map({
							target : 'map',
							layers : [ new ol.layer.Tile({
								source : new ol.source.OSM()
							}) ],
							view : new ol.View({
								center : ol.proj.fromLonLat(centerPos),
								zoom : 14
							})
						})
					} else {
						map = new ol.Map({
							target : 'map',
							layers : [ new ol.layer.Tile({
								source : new ol.source.OSM()
							}), vectorLayer ],
							view : new ol.View({
								center : ol.proj.fromLonLat(centerPos),
								zoom : 14
							})
						})
					}

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
