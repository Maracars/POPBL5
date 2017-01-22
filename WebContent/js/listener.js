var socket = io.connect("http://localhost:9092");

var counter = 0;

$(document).ready(function(){
	onLoadFunction();
});
function onLoadFunction() {
	var listenTo = $("#listenerRole").val();
	if (listenTo === "") {

		listenTo = "public";

	}

	socket.on("connect", function() {
	});

	socket.on(listenTo, function(data) {
		// var data2 = JSON.parse(data);
		
		render(data);
	});

	socket.on("disconnect", function() {
	});

	function sendDisconnect() {
		socket.disconnect();
	}
}

function render(data) {
	var element = $("<li id = 'console"+ counter+"'><a href = '#'><strong>" + data + "</strong></li>");
	$(".console").append(element);
    $(".console").animate({scrollTop: $(".console").prop("scrollHeight")}, 1);
	
	
	 var notify = $.notify(`<strong>${data}</strong>`, {
	 type: 'success', offset: { x: 50, y: 50 }, animate: { enter: 'animated	 bounceInDown', exit: 'animated lightSpeedOut' } });
	
}
