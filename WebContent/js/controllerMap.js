var socket = io.connect("http://localhost:9092");

var map = null;
var centerPos = [ -0.461389, 51.4775 ];
var vectorLayer;
var vectorSource = new ol.source.Vector({
// create empty vector
});

var planes = [];

$(document).ready(
		function() {

			socket.on("chatevent", function(jsonData) {
				var data = JSON.parse(jsonData);
				console.log(data);
				var featureToUpdate = vectorSource.getFeatureById(data.id);
				 var coordinate = vectorSource.getFeatureById(data.id).getGeometry().getCoordinates();

				 var coord = getPointFromLongLat(data.positionX, data.positionY );
				// featureToUpdate.getGeometry().setCoordinates(coord);

			});
			function getPointFromLongLat (long, lat) {
			    return ol.proj.transform([long, lat], 'EPSG:4326', 'EPSG:3857')
			}

			$.get("getFlights", function(data, status) {
				var obj = jQuery.parseJSON(data);
				planes = obj.result[0];

				for (var i = 0; i < planes.length; i++) {

					var iconFeature = new ol.Feature({
						geometry : new ol.geom.Point(ol.proj.transform([
								planes[i].planeMovement.positionX,
								planes[i].planeMovement.positionY ],
								'EPSG:4326', 'EPSG:3857'))
					});
					iconFeature.setId(planes[i].id);
					vectorSource.addFeature(iconFeature);

				}

				var iconStyle = new ol.style.Style({
					image : new ol.style.Icon(/** @type {olx.style.IconOptions} */
					({
						anchor : [ 0.5, 46 ],
						anchorXUnits : 'fraction',
						anchorYUnits : 'pixels',
						opacity : 0.75,
						src : '../rsc/img/plane-icon.png'
					}))
				});

				// add the feature vector to the layer vector, and apply a style
				// to whole layer
				vectorLayer = new ol.layer.Vector({
					source : vectorSource,
					style : iconStyle
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

function changeTerminalZoom(longitude, latitude) {
	var bounce = ol.animation.bounce({
		resolution : map.getView().getResolution()
	});

	var pan = ol.animation.pan({
		source : map.getView().getCenter()
	});
	map.beforeRender(bounce);
	map.beforeRender(pan);

	map.getView()
			.setCenter(
					ol.proj.fromLonLat([ parseFloat(latitude),
							parseFloat(longitude) ]));
	map.getView().setZoom(16);
}
