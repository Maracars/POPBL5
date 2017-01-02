
var map = null;
var centerPos = [-0.461389,51.4775];

$(document).ready(function(){
	
	$('#map').ready(function() {

		map = new ol.Map({
			target: 'map',
			layers: [
				new ol.layer.Tile({
					source: new ol.source.OSM()
				})
				],
				view: new ol.View({
					center: ol.proj.fromLonLat(centerPos),
					zoom:14
				})
		})
	});

});

function changeTerminalZoom(longitude, latitude){
    var bounce = ol.animation.bounce({
        resolution: map.getView().getResolution()
      });
    
      var pan = ol.animation.pan({
        source: map.getView().getCenter()
      });
      map.beforeRender(bounce);
      map.beforeRender(pan);
      
      map.getView().setCenter(ol.proj.fromLonLat([parseFloat(latitude),parseFloat(longitude)]));
      map.getView().setZoom(16);
}



