var socket = io.connect("http://localhost:9092");

socket.on("connect", function() {
	console.log("jajaja konektau in da");
});

socket.on("chatevent", function(data) {
	var data2 = JSON.parse(data);

	console.log(data2.izena);
	render(data2);
});

socket.on("disconnect", function() {
	console.log("jajaja deskonektau in da");
});

function sendDisconnect() {
	socket.disconnect();
}

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
