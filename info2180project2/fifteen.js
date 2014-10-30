// JavaScript Document
"use strict";

window.onload = function(){
	
	var puzarea = document.getElementById('puzzlearea');
	var div  = puzarea.getElementByTagName('div');
	
	for (var a = 0 ; a<div.length ; a++){
			div[a].className = 'puzzlepiece';
		div[a].style.left = (a%4*100)+'px';
		div[a].style.top = (parseInt(a/4)*100) + 'px';
		div[a].style.backgroundPosition= '-' + div[a].style.left + ' ' + '-' + div[a].style.top;
		
	}
	
};

