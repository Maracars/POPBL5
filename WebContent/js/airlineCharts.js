$(document).ready(function() {
	c3.generate({
		bindto: '#barChart',
		data : {
			url : 'airline/weekFlightListJSON',
			mimeType : 'json',
			keys : {
				x : 'planeName'
			}
		},
		axis : {
			x : {
				type : 'category',
				tick : {
					count : 5,
				}
			}
		}
	});

});