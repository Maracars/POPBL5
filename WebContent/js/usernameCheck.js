/**
 * 
 */

$(document).ready(function(){
	
	
	
	$(".username").blur(function() {
		var EmailV = $(".username").val();
		$.get("UserCheck.action", {
			username : EmailV
		}, function(response) {
			 var data = JSON.parse(response);
			 var result = data.result[0];
			 
			if( result){
				console.log("That user exists, you can not create a user with it");
			}else {
				console.log("That user does not exist, you can create a user with it");

			}

			//Honen arabera jarri bihar dau tick bat edo ekis bat div baten
		});
		return false;
	});
});
