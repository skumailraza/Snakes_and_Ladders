# Snakes_and_Ladders
Traditional snakes and Ladders board game for Android

## Snakes & Ladders Android Game


## Introduction
The Snakes & Ladders is an automated version of the Android game based on the traditional variant of the original Snakes & Ladders Ludo Board Game. The game has multiple levels with increasing difficulty challenge. The game also has two Player Modes:

- Against Computer
- A multiplayer game

## Approach

The game is a variant of the original game so it has the following set of rules:
- There are 2 players in this game and board size is of 10x10.
- Possible outcomes by throwing a dice are 1,2,3,4,5,6.
- If output is 6 then current the player will get a chance again to roll the dice.
- If outcome of the Dice is 1,2,3,4,5,6 and player positioned on mouth of snake then his current position will change to tail of snake.
- If outcome of the Dice is 1,2,3,4,5,6 and player positioned at below of ladder then His current position will change to topmost position.
- If player's current position+ roll >100 then the other player will get a chance to throw the dice.
- Any player reaching 100 earlier than the other player will be the winner and the game will end.

Some additional rules embedded into the game are:
- If either of the two players end up on the same position at the same time, the latter one will occupy that position and former has to retreat 3 positions.
- Either of the player can have a max of 1 retake on a draw of 6. If another six occurs, the max it will move is 12.

Since the game is implemented in Java, it uses the Java Canvas library for the rendering of the board. The items on the board are set using custom mathematical functions to position the screen objects on the board. It uses android studio’s built in activity designer to design the UI.


## Design

Note: For detailed documentation of the code and the classes and objects being used in the code, please refer to the Java Doc files accompanied in the project folder.

The game is controlled by multiple classes. Using the object oriented paradigm, the following classes are included in the game (a brief skeleton):

	Player
	{
	playerName	:	Name of the player
	blockVal	:	Current Position of the player
	gender			
	character	:	Identifying icon of the Player

	getName()	:	Returns player Name
	getBlock()	:	Return current position of player
	getCharacter()	:	Return identifying icon of player
	updatePos()	:	Used to update the players position after dice is rolled
	setPos()	:	Used to set position when stepped on Ladder or Snake

	}

	Ladder
	{
	ladderNum	:	Ladder id
	headPos	:	Position of Ladder’s head
	tailPos		:	Position of Ladder’s tail
	ladderChar	:	Ladder’s image

	getHead()	: 	Returns Head Position
	getTail()	:	Returns Tail Position
	setLadder()	:	Sets type of ladder based on board position

	}

	* Snake Class same as above*

	GamePanel
	{	extends android’s surfaceView Class		}

Used to meet and initialize android dependencies to be used elsewhere. For more details, please see Java Doc.

	Board{
	•	Extends Android’s View Class
	•	The actual Game board is built using this class.
	•	For complete details please see Java Doc.

	The following processes take place in this class:
	•	Initializes Blocks (i.e 10 x 10 grid of square blocks)
	•	Initializes Snakes array and Ladders for use.
	•	Sets the number of snakes and Ladders to be used based on the Levels
	•	Uses Canvas calls to render the game board as well as the snakes as well as ladders by setting their coordinates.
	•	Calculates Snake & Ladder’s Length by distance formula.
	•	Also calculates the angle of rotation for the snakes and ladders to be set to the blocks, using the trigonometric functions.

	e.g 	angle = arcTan(Height B/w blocks x,y / Width B/w 2 blocks x,y )

	•	Builds instances of Players

	}

	Dice {
	Num	:	Stores the outcome it produces
	Rand	:	An instance of Java’s Random Data type

	getNum()	:	Gets a Random number between and including 1 and 6.

	}

	GameManager {
		*for complete doc please see Java Doc File*
	•	Extends Activity class of Java.
	•	Responsible for Running and creating the activity.
	•	Initializes instances of all the objects.
	•	Sets the layout of the Activity using the Board Class Object and Android Layout.
	•	Checks if Roll The Dice button is Pressed or if it should be enabled
	•	Rolls the dice, updates the position and checks the turn of the player

	checkPosition()	:	Checks if the players are on the same position and takes necessary action as per the rules.
	checkWinner()	:	Checks if either of the players have reached 100 to declare a winner.
	checkBlock()	:	Check if the players have stepped on a snake or a ladder and take necessary action as per the rules.
	}


## Novelty:
- The design uses the FlyWeight Software Design Pattern (as Canvas is being used) to render the blocks as well as the snakes and ladders. This increases the game performance incredibly thereby reducing the impact on the GPU, hence eating up less battery power.

The 80% Win Probability in higher levels for the Computer is maintained using two approaches:

### Biased Dice:
The dice when rolled for the computer produces more 6s than the human player i.e the Dice’s 2,3,4 are occasionally considered 6.

#### PsuodoCode of the RollDice function:

	roll = dice.getNum();
	if (turn){
		if(roll in {2,3,4})
			roll = 6;
	}
	player1.updatePos(roll);

### Escape Snakes:
When nearing a block which contains a snake, the computer will check and avoid it. This is done in the update position (updatePos) function of the player object. As the SNAKE_LIST is public, the Player Class can access it and check the position of the upcoming snake and avoid it.

What did I learn?
1. Learnt how the threads in Java work and how to implement concurrency control and avoid race conditions.
2. Allowed to be comfortable with the Android IDE and understand the working components of Android applications.
3. Allowed us to get familiarized with Android jargons and terminologies.
4. Understood what the Activities are and how they communicate with each other using Intent functionality. 
5. Understood and learned various Java and Android specific dependency problems as well as methods and how to solve and implement them. Also learnt how to handle various android exceptions.
6. Understood how the touch input in smartphones work and learnt how to implement various functionalities using it.
7. Got familiar with Android Canvas Library and understood most of its tools and methods.


## Unit Tests and Test Application:
The application source code includes Unit Test class as well as Application Test logic class which verifies most of the functionality.
Application Properties & Dependencies

The application package contains:
1. A signed APK file of the game app.
2. The Project Java Docs.
3. The Compressed Source Code of the application.

The application is tested on the following devices:
- Samsung Galaxy Note 8 (Nougat 7.1.1)
- Samsung Galaxy S4 (Lollipop 5.1)
- Samsung A5 (2016) (Marshmallow 6.0)


## App Requirements:

	The app is recommended to run on Android API 22 (Lollipop 5.1).
	The max supported is Android API 25 (Nougat 7.1)
	The minimum supported is Android API 15 ( Ice Cream Sandwich 4.1.x).

All the libraries and dependencies are included in the project folder. 
The app uses the Java Unit test framework JUnit version 4.12.

## How to Run

- Just Install the APK file on any Android OS.
- To Compile it from source, open the project file in Android Studio. Built the gradle scripts and run.
- To view Java Docs, open the index.html file in the Java Docs folder.
