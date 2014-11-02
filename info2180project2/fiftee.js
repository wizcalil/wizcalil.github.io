"use strict";
var div;
var blink;
var time;
var whiteSpaceY;
var whiteSpaceX;
//Extra feature is the background changes color when the player wins
// JavaScript Document
// extra feature is the page background changes color and an alert saying you have won
/*"use strict";

var emptySpaceX;
var emptySpaceY;
var div;
var blink;
var time;*/

window.onload = function ()
{
/*	var puzzlearea = document.getElementById('puzzlearea');
	
	div = puzzlearea.getElementsByTagName('div');

	for (var i=0; i<div.length; i++)
	{
		div[i].className = 'puzzlepiece';
		div[i].style.left = (i%4*100)+'px';
		div[i].style.top = (parseInt(i/4)*100) + 'px';
		div[i].style.backgroundPosition= '-' + div[i].style.left + ' ' + '-' + div[i].style.top;
		div[i].onmouseover = function()
		{
			if (checkCanMove(parseInt(this.innerHTML)))
			{
				this.style.border = "2px solid red";
				this.style.color = "#006600";
			}
		};
		div[i].onmouseout = function()
		{
			this.style.border = "2px solid black";
			this.style.color = "#000000";
		};

		div[i].onclick = function()
		{
			if (checkCanMove(parseInt(this.innerHTML)))
			{
				swap(this.innerHTML-1);
				if (checkFinish())
				{
					youWin();
				}
				return;
			}
		};
	}*/
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

	whiteSpaceX = '300px';
	whiteSpaceY = '300px';

	var shufflebutton = document.getElementById('shufflebutton');
	shufflebutton.onclick = function()
	{

		for (var a=0; a<250; a++)
		{
			var r = parseInt(Math.random()* 100) %4;
			if (r == 0)
			{
				var tmp = up(whiteSpaceX, whiteSpaceY);
				if ( tmp != -1)
				{
					swap(tmp);
				}
			}
			if (r == 1)
			{
				var tmp = down(whiteSpaceX, whiteSpaceY);
				if ( tmp != -1) 
				{
					swap(tmp);
				}
			}

			if (r == 2)
			{
				var tmp = left(whiteSpaceX, whiteSpaceY);
				if ( tmp != -1)
				{
					swap(tmp);
				}
			}

			if (r == 3)
			{
				var tmp = right(whiteSpaceX, whiteSpaceY);
				if (tmp != -1)
				{
					swap(tmp);
				}
			}
		}
	};
};

/*function checkCanMove(pos)
{
	if (calcLeft(whiteSpaceX, whiteSpaceY) == (pos-1))
	{
		return true;
	}

	if (calcDown(whiteSpaceX, whiteSpaceY) == (pos-1))
	{
		return true;
	}

	if (calcUp(whiteSpaceX, whiteSpaceY) == (pos-1))
	{
		return true;
	}

	if (calcRight(whiteSpaceX, whiteSpaceY) == (pos-1))
	{
		return true;
	}
}*/
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

/*function Blink()
{
	blink --;
	if (blink == 0)
	{
		var body = document.getElementsByTagName('body');
		body[0].style.backgroundColor = "#FFFFFF";
		alert('you win');
		return;
	}
	if (blink % 2)
	{
		var body = document.getElementsByTagName('body');
		body[0].style.backgroundColor = "#00FF00";	
	}
	else
	{
		var body = document.getElementsByTagName('body');
		body[0].style.backgroundColor = "#FF0000";
	}
	timer = setTimeout(Blink, 100);
}*/
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

/*function youWin()
{
	var body = document.getElementsByTagName('body');
	body[0].style.backgroundColor = "#FF0000";
	blink = 10;
	timer = setTimeout(Blink, 100);
}*/
var win = function()
{
	var body = document.getElementsByTagName('body');
	body[0].style.backgroundColor = "#5be7a7";
	blink = 10;
	time = setTimeout(Flash, 100);
	};

/*function checkFinish()
{
	var flag = true;
	for (var i = 0; i < div.length; i++) {
		var y = parseInt(div[i].style.top);
		var x = parseInt(div[i].style.left);

		if (x != (i%4*100) || y != parseInt(i/4)*100)
		{
			flag = false;
			break;
		}
	}
	return flag;
}*/
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

/*function calcLeft(x, y)
{
	var xx = parseInt(x);
	var yy = parseInt(y);

	if (xx > 0)
	{
		for (var i = 0; i < div.length; i++) 
		{
			if (parseInt(div[i].style.left) + 100 == xx && parseInt(div[i].style.top) == yy)
			{
				return i;
			} 
		}
	}
	else 
	{
		return -1;
	}
}

function calcRight (x, y) {
	var xx = parseInt(x);
	var yy = parseInt(y);
	if (xx < 300)
	{
		for (var i =0; i<div.length; i++){
			if (parseInt(div[i].style.left) - 100 == xx && parseInt(div[i].style.top) == yy) 
			{
				return i;
			}
		}
	}
	else
	{
		return -1;
	} 
}

function calcUp (x, y) {
	var xx = parseInt(x);
	var yy = parseInt(y);
	if (yy > 0)
	{
		for (var i=0; i<div.length; i++)
		{
			if (parseInt(div[i].style.top) + 100 == yy && parseInt(div[i].style.left) == xx) 
			{
				return i;
			}
		} 
	}
	else 
	{
		return -1;
	}
}

function calcDown (x, y)
{
	var xx = parseInt(x);
	var yy = parseInt(y);
	if (yy < 300)
	{
		for (var i=0; i<div.length; i++)
		{
			if (parseInt(div[i].style.top) - 100 == yy && parseInt(div[i].style.left) == xx) 
			{
				return i;
			}
		}
	}
	else
	{
		return -1;
	} 
}*/

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

function swap (pos) {
	var temp = div[pos].style.top;
	div[pos].style.top = whiteSpaceY;
	whiteSpaceY = temp;

	temp = div[pos].style.left;
	div[pos].style.left = whiteSpaceX;
	whiteSpaceX = temp;
}

/*var move = function (position) {
	var tmp = div[position].style.top;
	div[position].style.top = emptySpaceY;
	emptySpaceY = tmp;

	tmp = div[position].style.left;
	div[position].style.left = emptySpaceX;
	emptySpaceX = tmp;
};*/