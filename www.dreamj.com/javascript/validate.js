// JavaScript Document


$(document).ready(function(){
	 
$("#submit1").click(function(){
		
		var username = $("#username").val();

		var password = $("#password").val();

		
		var dataString = 'username=' + username +'&password='+ password;
		$.ajax({
			type: "POST",
			url: "/www.dreamj.com/server/login.php",
			data: dataString,
			cache:false,
			dataType:"json",
			success: function(result){
				
				
				//if(result.status == "success"){
					
				  	
					$('#bodyarea').load('/www.dreamj.com/content/home.php #contentwrapper');
					
				//}else{
					
					
					
				//alert(result.status);
				//}
			},
			error: function() {
				alert("Did not connect to php");
			}
		
		});
		
		return false;
});
});

