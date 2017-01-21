$(document).ready(function() {
	$('.username').on('input', function() {
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
					if(result){
						$('div[class*="form-group"]').has('div').has('.username').addClass('has-error');
						$('.user-taken-msg').show();
					}else{
						$('div[class*="form-group"]').has('div').has('.username').removeClass('has-error');
						$('.user-taken-msg').hide();
					}
						
				},
				error : function(xhr, ajaxOptions, thrownError) {

				}
			});
		}
	});
});