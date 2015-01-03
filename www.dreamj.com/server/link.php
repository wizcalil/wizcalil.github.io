<?php

session_start();
require_once("connector.class.php");

/***************************
*Connecting to the database*
****************************/

$dbHost="localhost";
$dbUsername = "root";
$dbPassword = "";
$dbName="dreamjinc";


//instantiate object
$connect = new MySQL($dbHost,$dbUsername,$dbPassword,$dbName);

//connect to database
$connect->connect();


?>