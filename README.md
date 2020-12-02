This project represents a maze game that offers a generation of new maze, playing it, and watching its solution. We build 2 servers - one that generates mazes and the second that solves mazes. To shorten communication time we will compress the information that passes using Stream class of Java and Decorator Design Pattern.

 It consist of 3 parts (3 steps for the final project):
Part A - Maze Generation + Search Algorithms
This part contains 3 generation methods:
empty maze - generates maze without walls
simple maze - generates maze with random walls walls
my maze - generates maze with algorithm took from Wikipedia - https://en.wikipedia.org/wiki/Maze_generation_algorithm

The algorithms part contains 3 search algorithms for solving the maze:
BFS -  algorithm for searching tree using queue
DFS - algorithm for searching tree using stack
Best-FS - algorithm for searching tree using priority queue

*We used here an object adapter named SearchableMaze that adapts our maze for a search problem.

UML diagram for this part: 
https://lucid.app/lucidchart/invitations/accept/d8b85df2-641c-4fa8-b0c2-7f8bc4ad077f

Part B - Client - Server
In this part we implement a client and a server part. 
We implemented 2 servers, one for generating maze and the other for solving it. For this purpose we will use Strategy Pattern.
Our servers support multiple clients using thread pool. The connection is a session of question-answer and ends after the server answer.

UML diagram for this part:
https://lucid.app/lucidchart/invitations/accept/0359c40a-a61d-4bb1-b42f-26abf8403426



Part C - GUI
For the game visualisation we used JavaFX in MVVM architecture.
View layer is in charge of fxml files and their controllers
Mode layer used for communication with servers, using searching algorithms, saving the game state.
The ViewModel layer connects View and Model.

*notice that to run the game you need to clone only Part C of the project, it includes jars of 2 other parts. The rest of them are given only for documentation.


