$(document).ready(function() {
	$('.username').blur(function() {
		var username = $(".username").val();
		if (username != "") {
			$.ajax({
				traditional : true,
				type : "GET",
				url : "UserCheck",
				dataType : "json",
				data : "username=" + username,
				success : function(data, success) {
					var obj = jQuery.parseJSON(data);
					var result = obj.result[0];
					$('input[type="submit"]').prop('disabled', result);
				},
				error : function(xhr, ajaxOptions, thrownError) {

				}
			});
		}
	});
});