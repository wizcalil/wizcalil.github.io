var userid = "";
var username;
var numberUnread;

/*
function that attach eventlisteners based on browser

*/
function attachEvent(element, type, handler)
{
    if (element.addEventListener){ element.addEventListener(type, handler, false);}
    else {element.attachEvent("on" + type, handler);}
}

attachEvent(window,"load",setup);

/*
setup funtion which is called when the window loads
function attaches the required events and sets up display

*/
function setup()
{
    document.getElementById("home").style.display = "none";
    document.getElementById("user_data").style.display = "none";
    document.getElementById("new_message").style.display = "none";
    var checklog = {login: 'true'};
            
    new Ajax.Request( 'cheapo.php', {method: 'post', parameters: checklog, onSuccess: reload} );
    
    
    attachEvent(document.getElementById("subButton"), "click", login);
    attachEvent(document.getElementById("logoutbutton"), "click", logout);
    
    attachEvent(document.getElementById("compose"), "click", compose);
    attachEvent(document.getElementById("send_button"), "click", send);
    attachEvent(document.getElementById("cancel_send"), "click", cancel_msg);
    attachEvent(document.getElementById("add_user_button"), "click", addNewUser);
    attachEvent(document.getElementById("new_user_sub"), "click", subNewUser);
    attachEvent(document.getElementById("new_user_cancel"), "click", cancelNewUser);
    
    var param = {getusers: ""};
    new Ajax.Request("cheapo.php", {method: 'post', parameters: param, onSuccess: getUsers});
    
    setInterval(function(){if(userid !== ""){checkmessage()}},2000);
}

/*
whenever the window reloads the function is called.
the function checks if the user if logged in and carry out required actions

*/
function reload(response)
{
    
    var status = response.responseXML.getElementsByTagName("status");
    var successful = status[0].childNodes[0].nodeValue;
    var user = response.responseXML.getElementsByTagName("userid");
    userid = user[0].childNodes[0].nodeValue;
    
    setTimeout(clearT,500);
    
    if(isTrue(successful))
    {
        document.getElementById("login").style.display = "none";
        document.getElementById("new_message").style.display = "none";
        document.getElementById("home").style.display = "inherit";
        document.getElementById("username").value = "";
        document.getElementById("pwd").value = "";
        if(userid != "1")
        {
            document.getElementById("add_user_button").style.display = "none";
            document.getElementById("user_data").style.display = "none";
        }
        else
        {
            document.getElementById("add_user_button").style.display = "inherit";
        } 
    }
    else
    {
        document.getElementById("home").style.display = "none";
        document.getElementById("user_data").style.display = "none";
        document.getElementById("new_message").style.display = "none";
    }
}


/*
this function is triggered when ever the user clicks on an unread message

*/
function readMessage(click)
{
    if(!click) {click = window.event;}
    var message = (click.target) ? click.target: click.srcElement;
    var body = message.lastChild;
    
    //alert(body.style.display.value);
    if(body.style.display == "none" || body.style.display != "inherit")
    {
        body.style.display = "inherit";
    }
    else
    {
        body.style.display = "none";
    }
    
    if(message.id == "newMessage")
    {
        message.id = "oldMessage";
        message.style.removeProperty('font-weight');
        
        var param = {readmsg: "true", id: (message.firstChild.innerHTML)};
        new Ajax.Request("cheapo.php", {method: "post", parameters: param, onSuccess: messageRead});
    }
 
}


/*
    function allows users to view messages
    the function handles response of ajax request

*/
function messageRead(response)
{
    var status = response.responseXML.getElementsByTagName("status");
    var successful = status[0].childNodes[0].nodeValue;
    
    if(isTrue(successful))
    {
        numberUnread -= 1;
        document.getElementById("message_alert").innerHTML = "<h2>You have " + numberUnread + " new unread messages!</h2>" ;
    }
}


function getUsers(response)
{
    var status = response.responseXML.getElementsByTagName("status");
    var successful = status[0].childNodes[0].nodeValue;
   
    if(isTrue(successful))
    {
        var output = "";
        var list = document.getElementById("Cheapo_Users");
        var usernames = response.responseXML.getElementsByTagName("username");
        var userids = response.responseXML.getElementsByTagName("userid");
        
       
        for(var a = 0; a < usernames.length; a++)
        {
            output = document.createElement("option");
            output.value = userids[a].childNodes[0].nodeValue;
            output.text=usernames[a].childNodes[0].nodeValue;
            list.appendChild(output);
        }
        
    }
    
}


/*
This function is called repeatedly inorder to check for new messages

*/
function checkmessage()
{
    
    var param = {checkmsg: ""};
    new Ajax.Request("cheapo.php", {method: "post", parameters: param, onSuccess: messageChecked });
}


/*

Response from Ajax Request to chech messages are handled by this function

*/
function messageChecked(response)
{
    var output = "";
    var read = "";
    var readVal = "";
    var status = response.responseXML.getElementsByTagName("status");
    var successful = status[0].childNodes[0].nodeValue;
    
    if(isTrue(successful))
    {
        var sender = response.responseXML.getElementsByTagName("sender");
        var subject = response.responseXML.getElementsByTagName("subject");
        var body = response.responseXML.getElementsByTagName("body");
        var msgid = response.responseXML.getElementsByTagName("msgid");
        numberUnread = 0;
        for (var i = 0; i < sender.length && i<10; i++)
        {
            readVal = response.responseXML.getElementsByTagName("read");
            read = readVal[i].childNodes[0].nodeValue;
            
            if(isTrue(read))
            {
                output = output +'<hr><div id="oldMessage">Sent by: ' + sender[i].childNodes[0].nodeValue + "<br>Subject: " + subject[i].childNodes[0].nodeValue + '<br><div id="messagebody">' + body[i].childNodes[0].nodeValue +"</div></div>";
            }
            else
            {
                numberUnread++;
                output = output +'<hr><div id="newMessage"><div = id ="msgid">' + msgid[i].childNodes[0].nodeValue + '</div>Sent by: ' + sender[i].childNodes[0].nodeValue + "<br>Subject: " + subject[i].childNodes[0].nodeValue + '<br> <div id="messagebody">' + body[i].childNodes[0].nodeValue +"</div></div>";
            }
            
        }
        
        document.getElementById("message_alert").innerHTML = "<h2>You have " + numberUnread + " new unread messages!</h2>" ;
        document.getElementById("user_messages").innerHTML = output;
        
        var messages = $("user_messages").select("*");
        for(var j = 0; j <= messages.length ; j++)
        {
            attachEvent(messages[j],"click",readMessage);
        }
        
    }
    
}

/*
When the user clicks the button to compose a message this function is triggeed

*/
function compose()
{
    
    document.getElementById("new_message").style.display = "inherit";
    document.getElementById("messages").style.display = "none";
}


/*
Message sending a message is achieved by calling the following function

*/
function send()
{
    var msg_body = document.getElementById("msg_body").value;
    var msg_subject = document.getElementById("msg_subject").value;
    var users = document.getElementById('msg_receipent').value;
    
    var param = {sendmsg: "", subject: msg_subject, message: msg_body, cuser: users};
        
    new Ajax.Request("cheapo.php", {method: 'post', parameters: param, onSuccess: messageSent});
    
}


/*
after the Ajax request to send a message is sent this function is called on success

*/
function messageSent(response)
{
    var status = response.responseXML.getElementsByTagName("status");
    var successful = status[0].childNodes[0].nodeValue;
    
   if(isTrue(successful))
    {
        document.getElementById("new_message").style.display = "none";
        document.getElementById("messages").style.display = "inherit";
        document.getElementById("msg_receipent").value = "Select user";
        document.getElementById("msg_subject").value = "Type subject here";
        document.getElementById("msg_body").value = "";
    } 
}


/*

The the cancel button listen for click events and calls this function
*/
function cancel_msg()
{
    
    document.getElementById("msg_subject").value = "Type subject here";
    document.getElementById("msg_body").value = "";
    document.getElementById("new_message").style.display = "none";
    document.getElementById("messages").style.display = "inherit";
}


/*
the ability to login a user is made through this function

*/
function login()
{
    var username = (String)(document.getElementById("username").value);
    var pass = (String)(document.getElementById("pwd").value);
    
    
        if(username.length <= 0 || pass.length <= 0)
        {
            document.getElementById("login_error").innerHTML = "Enter valid username/password";
            document.getElementById("pwd").style.border = "1px solid red";
            document.getElementById("username").style.border = "1px solid red";
        }
        else
        {
            document.getElementById("login_error").innerHTML = "";
            document.getElementById("pwd").style.border = "1px solid green";
            document.getElementById("username").style.border = "1px solid green";
            
            var param = {login: 'true', uname: username, upass: pass};
            
            new Ajax.Request( 'cheapo.php', {method: 'post', parameters: param, onSuccess: onSuccesfulLogin} );
           
        }
   
}


/*

On successful login this function is triggered
*/
function onSuccesfulLogin(data)
{
    var status = data.responseXML.getElementsByTagName("status");
    var successful = status[0].childNodes[0].nodeValue;
    var idnum = data.responseXML.getElementsByTagName("userid");
    var name = data.responseXML.getElementsByTagName("username");
    
   if(isTrue(successful))
    {
        userid = idnum[0].childNodes[0].nodeValue;
        username = name[0].childNodes[0].nodeValue;
        document.getElementById("login").style.display = "none";
        document.getElementById("home").style.display = "inherit";
        document.getElementById("username").value = "";
        document.getElementById("pwd").value = "";
        if(userid != "1")
        {
            document.getElementById("add_user_button").style.display = "none";
        }
        else
        {
            document.getElementById("add_user_button").style.display = "inherit";
        }
    }
    else
    {
        document.getElementById("login_error").innerHTML = "Incorrect username and password combination";
        document.getElementById("pwd").style.border = "1px solid red";
        document.getElementById("username").style.border = "1px solid red";
    }
    
}


/*
function triggeres when the administrator attempts to create a new user

*/
function subNewUser()
{
    /////vaaaaaa
    var error = false;
    var fname = (String)(document.getElementById("new_user_fname").value);
    var lname = (String)(document.getElementById("new_user_lname").value);
    var uname = (String)(document.getElementById("new_user_uname").value);
    var password = (String)(document.getElementById("new_user_pass").value);
    
    var matches = password.match(/\d+/g);
    if (matches === null || password.length < 8)
    {
        error = true;
    }
    else if(!/[A-Z]/.test(password)){error = true;}
    else
    {error = false;}
    
    
    if(error)
    {
        document.getElementById("new_user_pass").style.border = "2px solid red";
        document.getElementById("new_user_error").innerHTMl = "<p>Ensure that passwords have at least one number and one letter,<br> and one capital letter and are at least 8 digits long<p>";
    }
    else
    {
        var param = {adduser: "", fname: fname, lname: lname, pass: password, username: uname};
        
        new Ajax.Request( 'cheapo.php', {method: 'post', parameters: param, onSuccess: confirmAdd} );
        
    }
}

/*
this function on success events from  the Ajax request to add a new user

*/

function confirmAdd(data)
{
    var status = data.responseXML.getElementsByTagName("status");
    var successful = status[0].childNodes[0].nodeValue;
    
    if(successful == "true")
    {
        document.getElementById("new_user_error").innerHTMl = "";
        document.getElementById("new_user_pass").style.border = "1px solid black";
        cancelNewUser();
    }
    else
    {
        document.getElementById("new_user_error").innerHTML = "Fail to add user";
    }
      
}
/*

Display's option to add user
*/

function addNewUser()
{
    document.getElementById("message_area").style.display = "none";
    document.getElementById("user_data").style.display = "inherit";
}


/*
cancels the addition of a new user when the cancel button is clicked

*/
function cancelNewUser()
{
    
    document.getElementById("new_user_fname").value = "";
    document.getElementById("new_user_lname").value = "";
    document.getElementById("new_user_uname").value = "";
    document.getElementById("new_user_pass").value = "";
    
    document.getElementById("message_area").style.display = "inherit";
    document.getElementById("user_data").style.display = "none";   
}

/*

function that handles logout request
*/

function logout()
{
    document.getElementById("login").style.display = "inherit";
    document.getElementById("home").style.display = "none";
    document.getElementById("user_messages").innerHTML = "No messages";
    document.getElementById("message_alert").innerHTML = "";
    
    var update = {logout: 'true'};
    new Ajax.Request( 'cheapo.php', {method: 'post', parameters: update, onSuccess: bye} );
    
    userid = "";
    username = "";
}

function bye()
{
    //alert("GOOD BYE");
}

/*
funtion that helps casing string values to boolean 

*/
function isTrue(input) {
    if (typeof input == 'string') {
        return input.toLowerCase() == 'true';
    }
    return !!input;
}

/*


*/
function clearT()
{
    clearTimeout(300);
}
