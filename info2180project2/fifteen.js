//Extra feature is the background changes color when the player wins
// JavaScript Document
// extra feature is the page background changes color and an alert saying you have won
"use strict";

var emptySpaceX;
var emptySpaceY;
var div;
var blink;
var time;

window.onload = function () {
	
	//get element id from puzzlearea
	var puzzle = document.getElementById('puzzlearea');
	
	//using element id to get all the elements inside the div tag
	 div  = puzzle.getElementsByTagName('div');
	
	
	
	// This for loop is used to make the fifteen puzzle pieces appear in the correct positions 
	for (var a = 0 ; a < div.length ; a++){
		
		//style each element from the css file by using the class name puzzlepiece
		div[a].className = 'puzzlepiece';
		
		//make numbers more visible
		div[a].style.color = "red";
		
		//align four tiles from left to right
		div[a].style.left = (a% 4 * 100) + 'px';
		
		//align four tiles from top to bottom
		div[a].style.top = (parseInt(a/4) * 100) + 'px';
		
		//Make the correct parts of the background show through behind each tile(negation needed for positioning)
		var left = div[a].style.left;
		var right = div[a].style.top;
		div[a].style.backgroundPosition = '-'+ left + ' ' + '-'+ right ;
		
		// when a tile is clicked and is able to move it is move in the white space
		div[a].onclick = function()
			{
				//check if tile can move
				if(checkIfCanMove(parseInt(this.innerHTML))){
					
					move(this.innerHTML-1);
					if(checkIfFinish()){
						win();//player win
						
					}
						
					return;
				}
			};
		div[a].onmouseover = function()
		{
			if(checkIfCanMove(parseInt(this.innerHTML))){
				
				this.style.border = "2px solid green";
				this.style.color = "green";
			}
				
		};
		div[a].onmouseout = function(){
			this.style.border = "2px solid black";
			this.style.color = "red";
		};
	}
	//set dimension of empty/white space
		emptySpaceX = '300px';
		emptySpaceY = '300px';

//listener function for shuffle button, to shuffle the tile randomly
	var shufflebutton = document.getElementById('shufflebutton');
	shufflebutton.onclick = function()
	{

		for (var a = 0; a < 250; a++)
		{
			var r = parseInt(Math.random()* 100) %4;
			if (r == 0)
			{
				var tmp = up(emptySpaceX, emptySpaceY);
				if ( tmp != -1)
				{
					move(tmp);
					
				}
			}
			if (r == 1)
			{
				var tmp = down(emptySpaceX, emptySpaceY);
				if ( tmp != -1) 
				{
					move(tmp);
				}
			}

			if (r == 2)
			{
				var tmp = left(emptySpaceX, emptySpaceY);
				if ( tmp != -1)
				{
					move(tmp);
					
				}
			}

			if (r == 3)
			{
				var tmp = right(emptySpaceX, emptySpaceY);
				if (tmp != -1)
				{
					move(tmp);
					
				}
			}
		}
	};
};

//Helper function to reduce complexity in code
var left = function(x, y)
{
	var xint = parseInt(x);
	var yint = parseInt(y);

	if (xint > 0)
	{
		for (var a = 0; a < div.length; a++) 
		{
			if (parseInt(div[a].style.top) == yint && parseInt(div[a].style.left) + 100 == xint)
			{
				return a;
			} 
		}
	}
	else 
	{
		return -1;
	}
};

var right = function (x, y) {
	var xint = parseInt(x);
	var yint = parseInt(y);
	if (xint < 300)
	{
		for (var a =0; a < div.length; a++){
			if (parseInt(div[a].style.top) == yint && parseInt(div[a].style.left) - 100 == xint ) 
			{
				return a;
			}
		}
	}
	else
	{
		return -1;
	} 
};

var up = function (x, y) {
	var xint = parseInt(x);
	var yint = parseInt(y);
	if (yint > 0)
	{
		for (var a=0; a<div.length; a++)
		{
			if ( parseInt(div[a].style.left) == xint && parseInt(div[a].style.top) + 100 == yint) 
			{
				return a;
			}
		} 
	}
	else 
	{
		return -1;
	}
};

var down = function (x, y)
{
	var xint = parseInt(x);
	var yint = parseInt(y);
	if (yint < 300)
	{
		for (var a=0; a<div.length; a++)
		{
			if ( parseInt(div[a].style.left) == xint && parseInt(div[a].style.top) - 100 == yint) 
			{
				return a;
			}
		}
	}
	else
	{
		return -1;
	} 
};

//function to move tile
var move = function (position) {
	var tmp = div[position].style.top;
	div[position].style.top = emptySpaceY;
	emptySpaceY = tmp;

	tmp = div[position].style.left;
	div[position].style.left = emptySpaceX;
	emptySpaceX = tmp;
};

// function use to change the background colour of the page when player has won 
var win = function()
{
	var body = document.getElementsByTagName('body');
	body[0].style.backgroundColor = "#5be7a7";
	blink = 10;
	time = setTimeout(Flash, 100);
	};

//check if player is finish organising the tiles
var checkIfFinish = function()
{
	var mark = true;
	for (var a = 0; a < div.length; a++) {
		var y = parseInt(div[a].style.top);
		var x = parseInt(div[a].style.left);

		if ( y != parseInt(a/4)*100 || x != (a%4*100))
		{
			mark = false;
			break;
		}
	}
	return mark;
};

//Check if tile is beside the white/empty space
var checkIfCanMove = function(position)
{
	if (left(emptySpaceX, emptySpaceY) == (position-1))
	{
		return true;
	}

	if (down(emptySpaceX, emptySpaceY) == (position-1))
	{
		return true;
	}

	if (up(emptySpaceX, emptySpaceY) == (position-1))
	{
		return true;
	}

	if (right(emptySpaceX, emptySpaceY) == (position-1))
	{
		return true;
	}
};
//function to makes the effect on the background(flashing colours)
var Flash = function()
{
	blink --;
	if (blink == 0)
	{
		var body = document.getElementsByTagName('body');
		body[0].style.backgroundColor = "#ffb7c5";
		alert('You Won');
		return;
	}
	if (blink % 2)
	{
		var body = document.getElementsByTagName('body');
		body[0].style.backgroundColor = "#33cc5c";	
	}
	else
	{
		var body = document.getElementsByTagName('body');
		body[0].style.backgroundColor = "#cbbeb5";
	}
	time = setTimeout(Flash, 100);
};