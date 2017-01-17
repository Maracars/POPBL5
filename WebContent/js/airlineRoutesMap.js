$(document).ready(function(){
	var map;
	var centerPos = [ -0.461389, 51.4775 ];
	var flightsData;
	var flightsSource;
	var features;
	var newStyleFeatures = [];
	var selectControl;
	var container = document.getElementById("routeInfo");
	var select = null;
	var coordinate = null;

	$("#routesMap").ready(function(){

		var overlay = new ol.Overlay({
			element : container
		});

		map = new ol.Map({
			target : "routesMap",
			layers : [ new ol.layer.Tile({
				source : new ol.source.OSM()
			}) ],
			view : new ol.View({
				center : ol.proj
				.fromLonLat(centerPos),
				zoom : 4
			})
		});
		
		

		var style = new ol.style.Style({
			stroke : new ol.style.Stroke({
				color : "#5bc0de",
				width : 4
			})

		});

		var newStyle = new ol.style.Style({
			stroke : new ol.style.Stroke({
				color : "#428bca",
				width : 6
			})

		});

		var addLater = function(feature, timeout) {
			window.setTimeout(function() {
				feature.set("start", new Date().getTime());
				flightsSource.addFeature(feature);
			}, timeout);
		};

		var pointsPerMs = 0.1;
		var animateFlights = function(event) {
			var vectorContext = event.vectorContext;
			var frameState = event.frameState;
			vectorContext.setStyle(style);

			features = flightsSource.getFeatures();
			for (var i = 0; i < features.length; i++) {
				var feature = features[i];
				if (!feature.get("finished")) {
					var coords = feature.getGeometry().getCoordinates();
					var elapsedTime = frameState.time - feature.get("start");
					var elapsedPoints = elapsedTime * pointsPerMs;

					if (elapsedPoints >= coords.length) {
						feature.set("finished", true);
					}

					var maxIndex = Math.min(elapsedPoints, coords.length);
					var currentLine = new ol.geom.LineString(coords.slice(0, maxIndex));

					vectorContext.drawGeometry(currentLine);
				}
			}
			map.render();
		};

		flightsSource = new ol.source.Vector({
			wrapX : false,
			loader : function(){
				var url = "routesListJSON";
				fetch(url).then(function(response){
					return response.json();
				}).then(function(json){
					flightsData = json.data;
					for(var i = 0; i < flightsData.length; i++){
						var flight = flightsData[i];
						var from = flight.source;
						var to = flight.destination;

						var arcGenerator = new arc.GreatCircle(
								{x : from[1], y : from[0]},
								{x : to[1], y : to[0]}
						);

						var arcLine = arcGenerator.Arc(100, {offset : 10});

						if(arcLine.geometries.length === 1){
							var line = new ol.geom.LineString(arcLine.geometries[0].coords);
							line.transform(ol.proj.get("EPSG:4326"), ol.proj.get("EPSG:3857"));

							var feature = new ol.Feature({
								geometry : line,
								finished : false
								
							});
							
							feature.setId(i);
							
							addLater(feature, i*50);
						}
					}
					map.on("postcompose", animateFlights);
				});
			}
		});
		var flightsLayer = new ol.layer.Vector({
			source: flightsSource,
			style: function(feature) {
				if (feature.get("finished")) {
					return style;
				} else {
					return null;
				}
			}
		});
		map.addLayer(flightsLayer);

		select = new ol.interaction.Select({
			condition: ol.events.condition.click,
			multi : true
		});


		map.addInteraction(select);
	     
		var ol3_sprint_location = ol.proj.transform([-1.20472, 52.93646], "EPSG:4326", "EPSG:3857");

		map.on("click", function(evt){
			coordinate = evt.coordinate;
		})

		select.on("select", function(e){
			var element = overlay.getElement();
			var sourceAirport = null;
			var destinationAirport = null;
			var routeId = null;
			if(e.target.getFeatures().getLength() > 0){
				e.target.getFeatures().forEach(function(feature){
					sourceAirport = flightsData[feature.getId()].sourceAirport;
					destinationAirport = flightsData[feature.getId()].destinationAirport;
					routeId = flightsData[feature.getId()].routeId;
				})
				overlay.setPosition(coordinate);
				map.addOverlay(overlay);
				$(element).popover("destroy");
		        $(element).popover({
		            "placement": "top",
		            "animation": false,
		            "title" : "Route: " +routeId,
		            "html": true,
		            "content": "<h5>Source Airport:</h5><p class='bg-info'>"+sourceAirport+"<hr/><h5>Destination Airport:</h5><p class='bg-info'>"+destinationAirport+"</p>"
		          });
		          $(element).popover("show");
			}else{
				$(element).popover("destroy");
			}
		});
		



		$(map.getViewport()).on("pointermove", function(e){
			for(i = 0; i < newStyleFeatures.length; i++){
				newStyleFeatures[i].setStyle(style);
			}
			map.forEachFeatureAtPixel(map.getEventPixel(e), function (feature, layer) {
				newStyleFeatures = [];
				newStyleFeatures.push(feature);
				feature.setStyle(newStyle);
			});
		});

	});
});