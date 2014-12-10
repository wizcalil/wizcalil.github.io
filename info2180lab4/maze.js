// JavaScript Document
"use strict";

var lost = false;

window.onload = function(){
	$("start").onmouseover = out;
	$("start").onclick = start;
    $("end").onmouseover = end;
	
	var borderlines =$$("div#maze div.boundary");
	var outside = $$("div#maze");
	for (var i = 0;i<borderlines.length;i++){
		borderlines[i].onmouseover = borderline;
		outside[i].onmouseover = out;
	}
};
function out(){
	var borderlines = $$("div#maze");
	borderlines.addClassName("youlose");
	}	

function borderline(){
	lost = true;
	$("status").textContent = "You lose!";
	var borderlines = $$("div#maze div.boundary");
	for ( var i = 0;i < borderlines.length; i++){
	borderlines[i].addClassName("youlose");
	}
}

function start(){
	lost = false;
	$("status").textContent = "game in progress";
	var borderlines = $$("div#maze div.boundary");
	for (var i = 0; i < borderlines.length;i++){
		borderlines[i].removeClassName("youlose");
	}
}
function end(){
	if(!lost){
		$("status").textContent = "you win!";
	}
}