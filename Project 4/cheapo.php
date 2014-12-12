<?php
session_start();

//ADMINISTRATOR
//username = admin
//password = Info2180


//get environment variables
$ip = getenv('IP');
$user = getenv('C9_USER');

mysql_connect(
//echo($ip);
"$ip",
"$user"
);

//select cheapo db
mysql_select_db("cheapodb");

//variable used to store response for request sent to this php file
$success;
//set header for displaying result as an xml file
header("content-type: application/xml");

//condition statments that check which request is being made to the php page then handles it using the
//required function
if(array_key_exists('login', $_POST) )
{
    $success = login();//calls login function
}
else if(array_key_exists('adduser', $_POST) )
{
    $success = adduser();//call add user function
}
else if(array_key_exists('sendmsg', $_POST) )
{
    $success = sendMessage();//call sendMessage function
}
else if(array_key_exists('checkmsg', $_POST) )
{
    $success = checkMessage();//calls function that checks message
}
else if(array_key_exists('readmsg', $_POST))
{
	$success = readmessage();//call function that updates read message data base
}

else if(array_key_exists('getusers', $_POST))
{
    $success = getUsers();//function that displays users
}

else if(array_key_exists('logout', $_POST))
{
    session_destroy();//function called to destroy php session on logout
}
else
{   //displays an error if no valid request found
    $success = '<?xml version="1.0" encoding="ISO-8859-1"?><status>error</status>';
}

//echo xml formatted result;
echo $success;

/*

*/

function getUsers()
{
    $qry = "select * from User";
    $users = mysql_query($qry);
    $out = '<?xml version="1.0" encoding="ISO-8859-1"?>';
    $out .= '<CheapoUsers>';
    $out .= '<status>';
    $out .= 'true';
    $out .= '</status>';
    
    while($row = mysql_fetch_array($users))
    {
        
        $out .= "<user>";
        $out .= "<username>".$row["username"].'</username>';
        $out .= "<userid>,".$row["id"].',</userid>';
        $out .= "</user>";
    }
    
    $out .= '</CheapoUsers>';
    
    return $out;
    
}



/*

*/
function sendMessage()
{
    $userid =  strval($_SESSION["sess_user_id"]);
    $message =  strval($_REQUEST['message']);
    $subject = strval($_REQUEST['subject']);
    $user = strval($_REQUEST['cuser']);
    $cuser = explode(",", $user);
    $ids = "";
    
	$out = '<?xml version="1.0" encoding="ISO-8859-1"?>';
    $out .= '<message>';
    $out .= '<status>';
    
    foreach($cuser as $i)
    {
        $b = trim($i);
        $getid = "Select id from User where username = '".$b."';";
        $data = mysql_query($getid);
        
        while($row = mysql_fetch_array($data))
        {
            $ids .= ",".$row['id'].",";
        } 
    
    }
    
/*

*/
    if(isset($_SESSION["sess_user_id"]))
    {
        
        $qry = "INSERT into Message VALUES  (0,'".$message."','".$subject."','".$userid."','".$ids."');";
        
        mysql_query($qry);
        
        $out .="true";
    }
    else
    {
       $out .= "false";
    }

	$out .= '</status>';
    $out .= '</message>';
	return $out;
}



/*

*/
function checkMessage()
{
    if(isset($_SESSION["sess_user_id"]))
    {
        $messagesfound = '<?xml version="1.0" encoding="ISO-8859-1"?>';
        $messagesfound .='<messagedata>';
        $messagesfound .='<status>';
        
        $userid =  strval($_SESSION["sess_user_id"]);
        $qry = "select username, subject, body, (Message.id) msgid from User join Message on Message.user_id=User.id where recipient_ids like '%,".$userid.",%' ORDER BY msgid DESC;";
        
        $messages = mysql_query($qry);
        $num_messages = mysql_num_rows($messages);
        
        if($num_messages > $_SESSION['sess_user_msg'])
        {
            $_SESSION['sess_user_msg'] = $num_messages;
            $messagesfound .= 'true';
            $messagesfound .= '</status>';
            
            while ($row = mysql_fetch_array($messages))
            {
                $readqry = "Select * from Message_read where message_id = ".$row["msgid"]." and reader_id like '%".$userid."%';";
                
                $readstat = mysql_query($readqry);
                $messagesfound .= '<message>';
                
                if(mysql_num_rows($readstat) < 1)
                {
                    $messagesfound .= '<read>false</read>';
                }
                else
                {
                    $messagesfound .= '<read>true</read>';
                }
                 
                $messagesfound .= '<sender>'.$row["username"].'</sender>';
                $messagesfound .= '<msgid>'.$row['msgid'].'</msgid>';
                $messagesfound .= '<subject>'.$row["subject"].'</subject>';
                $messagesfound .= '<body>'.$row["body"].'</body>';
                $messagesfound .= '</message>';
                
            }
            
        }
        else
        {
            $messagesfound = $messagesfound."false";
            $messagesfound = $messagesfound.'</status>';
        }
        
        $messagesfound = $messagesfound."</messagedata>";
        return $messagesfound;
    }
    
}


/*

*/
function login()
{
    $login = '<?xml version="1.0" encoding="ISO-8859-1"?>';
    $login = $login.'<login>';
    
    if (!isset($_SESSION["sess_user_id"]))
    {
    
        
        //parse string value of the credentials parameter 
        $upass = strval($_REQUEST['upass']);
        $uname = strval($_REQUEST['uname']);
                
        $qry = "SELECT * FROM User WHERE username = '".$uname."' AND password= '".$upass."' ;";
                
        $logindata = mysql_query($qry);
                
                
        if(mysql_num_rows($logindata) == 1)
        {
            $userData = mysql_fetch_array($logindata);
            $_SESSION['sess_user_id'] = $userData['id'];
        	$_SESSION['sess_username'] = $userData['username'];
        	$_SESSION['sess_user_msg'] = 0;
                	
        	$login .='<status>';
            $login .="true";
            $login .='</status>';
            $login .='<userid>';
            $login .="".$userData['id'];
            $login .='</userid>';
            $login .='<username>';
            $login .="".$userData['username'];
            $login .='</username>';
            $login .='</login>';
            return $login;
        }
        else
        {
            $login .='<status>';
            $login .="false";
            $login .='</status>';
            $login .='</login>';
            return $login;
        }
       
        
    }
    else
    {
        
        $login .='<status>';
        $login .="true";
        $login .='</status>';
        $login .='<userid>';
        $login .= $_SESSION['sess_user_id'];
        $login .='</userid>';
        $login .='</login>';
        $_SESSION['sess_user_msg'] = 0;
        return $login;
        
    }
    
}

/*

*/

function adduser()
{
    $nusername =  strval($_REQUEST['username']);
    $nfname =  strval($_REQUEST['fname']);
    $nlname =  strval($_REQUEST['lname']);
    $npass =  strval($_REQUEST['pass']);
    
    
	$out = '<?xml version="1.0" encoding="ISO-8859-1"?>';
    $out .= '<newuser>';
    $out .= '<status>';
      
    if(strval($_SESSION['sess_user_id']) === '1')
    {
        
        $qry = "INSERT into User (id, first_name, last_name, password, username) VALUES  (0,'".$nfname."','".$nlname."','".$npass."','".$nusername."');";
            
        mysql_query($qry);
            
        $out .= 'true';
                  
    }
    else
    {
        $out .= 'false';
    }

	$out .= '</status>';
    $out .= '</newuser>';
	return $out;
}


/*
function that handles updation database when message read
*/

function readMessage()
{
	$message_id =  strval($_REQUEST['id']);
    $reader_id =  strval($_SESSION["sess_user_id"]);
    $date =  date("Y-m-d");
        
    $out = '<?xml version="1.0" encoding="ISO-8859-1"?>';
    $out .= '<read>';
    $out .= '<status>';
    
    if(isset($_SESSION["sess_user_id"]))
    {  	
        
        $qry = "INSERT into Message_read (id, message_id, reader_id, date) VALUES  (0,'".$message_id."','".$reader_id."','".$date."');";
        
        mysql_query($qry);
        
        $out .= 'true';
              
    }
    else
    {
       $out .= 'false';
    }

	$out .= '</status>';
    $out .= '</read>';
	return $out;
}

?>

