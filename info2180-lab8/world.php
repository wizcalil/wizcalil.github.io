<?php
// A simple web site in Cloud9 that runs through Apache
// Press the 'Run' button on the top to start the web server,
// then click the URL that is emitted to the Output tab of the console



mysql_connect(
    getenv('IP'),
    getenv('C9_USER')
    );
mysql_select_db("world");

if(!empty($_REQUEST['lookup']) ){//&& !(isset($_POST['name']))){
$LOOKUP = $_REQUEST['lookup'];

# execute a SQL query on the database
$results = mysql_query("SELECT * FROM countries WHERE name LIKE '%$LOOKUP%';");
print $results;
# loop through each country
while ($row = mysql_fetch_array($results)) {
  ?>
  <li> <?php echo $row["name"]; ?>, ruled by <?php echo $row["head_of_state"]; ?> </li>

  <?php
  
}
}else{
    
    echo "no query";
}

if(!empty($_REQUEST['lookup']) && !(isset($_POST['name']))){

  echo '<?xml version="1.0" encoding="ISO-8859-1"?>';
    echo "\n<countrydata>\n";
    
    while ($row = mysql_fetch_array($results))
    {
    
    echo "<country>\n";
    echo "<name>".$row["name"]."</name>\n";
    echo "<ruler>".$row["head_of_state"]."</ruler>\n";
    echo "</country>\n";
    
    }
    echo "</countrydata>";
    
}else{
    
    echo "No query";
    
}


?>
