*	Author: Liam Johnston
*	Date made MVP: 8/6/19
*	Purpose: a basic 2d video game to demonstrate basic understanding of Java and to begin to play with Threads
*
*	User Notes: the program is started by running the java file
*	On running you should find multiple buttons along with an image in the background, this is the menu
*	There are multiple buttons but only start program and quit program are properly implenented
*	Program Options is an attempt to allow for some simple alterations with the settings eg player input in cleaner and more user friendly experience
*		such a window would allow for clicking various buttons to quickly re - key each button as the user inputs and allow for updating to same base settings with a range
*		for example to allow the option to select 1 or 2 players as the program is currently only setup to handle those number of players
*	Program Quit ends the program, in the windows environment using the close button (the X on the top right) serves the same purpose
*	Direct Connect is a button that literally does nothing at time of writing but was an idea of impleneting direct connection amongst computers to allow players to play on different computers
*	
*	Play / Start Program
*	This will actually 'hide' the menu and create a new window to play the game in, this is notable because the user must ensure they have selected the new window else the keyboard input won't be accepted. - this is standard windows procedure
*	unless altered below the players will be able to use 'wasd' or 'ijkl' to move their respective fighter across the screen
*	After moving down the players will drift downards by design and must 'pull up' to straight out the flight path
*	Additionally the players will always be moving left / right without player input until a different direction is selected
*	
*	The point of the game is to kill as many enemy ground and air units before the player's health reaches 0 -> only when all players are dead is the game over
*	To restart the game, close the game window and reselect the start program button in the menu to open a new game
*
*	To kill the enemy units you have default of 'q' or 'u' as a relevative to your direction, forward facing blasters
*	Additionally you have the default of 'e' or 'o' for your bombs which will on detonation create an explosion to damage surrounding enemies
*
*	Within the GameSettings.txt you can edit various settings with the following notes:
*	The program has inbuilt defaults so don't worry if you delete something
*	most values shouldn't be changed on a whim
*	But you can change the image names but you should include the full path name or at least the full name eg: 'image.png' rather than 'image'
*	The player keyboard values can be changed but they are using the number values of the keyboards and would probably require a google search
*		at time of creation i found https://www.oreilly.com/library/view/javascript-dhtml/9780596514082/apb.html as a reasonable reference but not inherently trustworthy / the only reference
*
*	Finally the program that reads this file will ignore any line containing the asterick '*'
*	it will look for a certain pre built values to override and if it can't find them the program will default to prebuilt values
*	This means that even if something isn't commented out that the program will still functionally ignore it
*	The only other real syntax to follow is the 'variable name' '=' 'value' format which can have spaces anywhere it must have an '=' to distinguish the variable from the value

* Actual final note: the commenting could be better / actually commenting more stuff