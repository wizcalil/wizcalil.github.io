<?php





//define  variable
//$username=$password="";
//$errorMessage = array();
//$userReg = "/^[A-Za-z ]*$/";


//if($_SERVER("REQUEST_METHOD")== "POST"){
	
	
	$username = $_POST['username'];
	$password = $_POST['password'];
	
	/*if(empty($username) || !isset($username)){
		
		$errorMessage[0] = "Please enter a Username";
		echo"empty";
		
	}else if(!preg_match($userReg,$username)){
		
		$errorMessage[0] = "Only letters and white space allowed";
		echo"fail";
		
	}else{*/
		
		session_start();
		require_once("connector.class.php");
		
		/***************************
		*Connecting to the database*
		****************************/
		
		$dbHost="localhost";
		$dbUsername = "root";
		$dbPassword = "";
		$dbName="dreamjinc";
		$response = array();
		
		//instantiate object
		$connect = new MySQL($dbHost,$dbUsername,$dbPassword,$dbName);
		
		//connect to database
		$con = $connect->connect();
		
		$sql = "SELECT  * FROM users WHERE username = '$username' AND password = '$password'";
	
		$result = mysqli_query($con,$sql);
		
		if(mysqli_num_rows($result)>0){
		
			$response['status'] = 'success' ;
			
			echo json_encode($response);
		
		}else{
			
			$response['status'] = 'error';
			
			echo json_encode($response);
		}
	
	/*if(empty($password)){
		$errorMessage[1] = "Please enter a Username";
	}else if(!preg_match($userReg,$password)){//check
		
		$errorMessage[1] = "Only letters and white space allowed";
	}else{
		
		echo "Correct password";
	}*/
	
	
	
	
//}
	
		
	
//}
/*function test_input($data){
	
	$data = trim($data);
	$data = stripslashes($data);
	$data = htmlspecialchars($data);
	
	return $data;
}*/
	

?>
