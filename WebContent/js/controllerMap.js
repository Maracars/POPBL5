var map = null;
var centerPos = [ -0.461389, 51.4775 ];

var planes = [];

$(document).ready(function() {

	$('#map').ready(function() {

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
	});
	$.ajax({
		traditional : true,
		type : "GET",
		url : "getFlights",
		dataType : "json",
		data : "username='joanes'",

		success : function(data, success) {
			var obj = jQuery.parseJSON(data);
			planes = obj.result[0];
			console.log(planes);
		},
		error : function(xhr, ajaxOptions, thrownError) {

		}
	});
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
