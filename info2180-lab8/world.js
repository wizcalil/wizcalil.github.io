window.onload = function(){
   getData();
    
}

function getData(){
    $('lookup').observe('click', function(){ 
	var term = $("term").getValue(); 
	//opening ajax connection
     new Ajax.Request("world.php", {
               method : 'get',
			   parameters : {lookup : term},
               onSuccess: function(transport) {
                var response = transport.responseText || "no response text";
                   $('result').update(response);
              },
              onFailure: function() { alert('Something went wrong...'); }         
     }); 
   });
   
   

}
// function getAll(){
//     $('all').observe('click', function(e){ 
// 	var checked = true; 
// 	//opening ajax connection
//      new Ajax.Request("world.php", {
//               method : 'get',
// 			   parameters : {all : checked},
//               onSuccess: function(transport) {
//                 var response = transport.responseText || "no response text";
//                   $('result').update(response);
//               },
//               onFailure: function() { alert('Something went wrong...'); }         
//      }); 
//   });