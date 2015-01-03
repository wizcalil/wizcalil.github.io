<?php

class MySQL{
	
	
	private $dbHost;
	private $dbUsername;
	private $dbPassword;
	private $dbName;
	private $dbLink;
	
	function MySQL($dbHost,$dbUsername,$dbPassword,$dbName){
		$this->dbHost = $dbHost;
		$this->dbUsername = $dbUsername;
		$this->dbPassword = $dbPassword;
		$this->dbName = $dbName;
		
		
	}
	
	

	function connect(){
		$this->dbLink = mysqli_connect($this->dbHost,$this->dbUsername,$this->dbPassword,$this->dbName);
		if(!$this->dbLink){
			 die('Connect Error (' . mysqli_connect_errno() . ') '
            . mysqli_connect_error());
		}else{
			
			return $this->dbLink;
		}
		
	}
	
	/*****************************
	 * Method to close connection *
	 *****************************/
	function close()
	{
		@mysqli_close($this->dbLink);
	}
}

?>
