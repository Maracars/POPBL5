var socket = io.connect("http://localhost:9092");

function render(data) {
	var element = $("<li><a href = '#'><i class = 'dropdown-item'><strong>"
			+ data.id + "</strong>" + data.izena + "</i></a></li>");
	$("#alertsList").append(element);
	/*
	 * var notify = $.notify(`<strong>${data.id}</strong> ${data.izena}`, {
	 * type: 'success', offset: { x: 50, y: 50 }, animate: { enter: 'animated
	 * bounceInDown', exit: 'animated lightSpeedOut' } });
	 */
}

socket.on("connect", function() {
});

socket.on("chatevent", function(data) {
	var data2 = JSON.parse(data);

	render(data2);
});

socket.on("disconnect", function() {
});

function sendDisconnect() {
	socket.disconnect();
}
