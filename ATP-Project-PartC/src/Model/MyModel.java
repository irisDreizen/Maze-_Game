package Model;

import Client.Client;
import IO.MyDecompressorInputStream;
import Server.IServerStrategy;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.scene.input.KeyCode;
import Client.IClientStrategy;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.scene.input.KeyCode.M;

public class MyModel extends Observable implements IModel {

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;



    private Position endPosition;
    private Maze ourMaze;
    private Solution ourSolution;
    private Server mazeGeneratingServer;
    private Server solveSearchProblemServer;
    private boolean isFinished = false;

    public MyModel() {
        //Raise the servers
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Position getEndPosition() {
        return endPosition;
    }
    public void startServers() {
        IServerStrategy servStra=new ServerStrategyGenerateMaze();
       mazeGeneratingServer = new Server(5400, 1000, servStra);
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        mazeGeneratingServer.start();
        solveSearchProblemServer.start();
    }

    public void stopServers() {
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
    }

/*
    @Override
    public void generateMaze(int width, int height) {
        //Generate maze
        threadPool.execute(() -> {
            generateExelentMaze(width,height);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers();
        });
    }
    */
/*
    private int[][] generateRandomMaze(int width, int height) {
        Random rand = new Random();
        maze = new int[width][height];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = Math.abs(rand.nextInt() % 2);
            }
        }
        return maze;
    }
    */

    public void generateMaze(int width, int height){

   //     this.startServers();
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new clientServerStrategy(width, height));
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        /*
        MyMazeGenerator GeneratedMaze = new MyMazeGenerator();
        Maze mazeToReturn=GeneratedMaze.generate(width,height);
        ourMaze =mazeToReturn;
        characterPositionRow=mazeToReturn.getStartPosition().getRowIndex();
        characterPositionColumn=mazeToReturn.getStartPosition().getColumnIndex();
        */
      //  return  mazeToReturn.getTheMaze();
    }

    public void saveMaze(File file){
        byte [] byteArray = ourMaze.toByteArray();
        byte [] toSave= new byte [byteArray.length+2];
        toSave[0]=(byte)characterPositionRow;
        toSave[1]=(byte)characterPositionColumn;
        int k=0;
        for(int i=2; i<toSave.length; i++){
            toSave[i]=byteArray[k];
            k++;
        }
        try {
            file.createNewFile();
            FileOutputStream file2=new FileOutputStream(file);
            file2.write(toSave);
        } catch (IOException e) {
          //  e.printStackTrace();
        }
    }
    public void loadMaze(File file){
        try {
            byte[] allByteArray = Files.readAllBytes(file.toPath());
            byte [] maze = new byte[allByteArray.length-2];
            int rowPos = (int)allByteArray[0];
            int colPos = (int)allByteArray[1];
            int k=0;
            for(int i=2; i<allByteArray.length; i++){
                maze[k]=allByteArray[i];
                k++;
            }
            Maze MazeForGame = new Maze(maze);
            ourMaze=MazeForGame;
            characterPositionRow=rowPos;
            characterPositionColumn=colPos;
            setChanged();
            notifyObservers();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class clientServerStrategy implements  IClientStrategy{
        private int width;
        private int height;
        public clientServerStrategy(int width1, int height2){
            width=width1;
            height=height2;
        }
        @Override
        public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
            try {
              //  System.out.println("1");
                ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
                ObjectInputStream fromServer = new ObjectInputStream(inputStream);
                toServer.flush();
                int[] mazeDimensions = new int[]{height, width};
              //  System.out.println("2");
                toServer.writeObject(mazeDimensions); //send maze dimensions to server
                toServer.flush();
              //  System.out.println("3");
                byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                //System.out.println("compressed size: "+compressedMaze.length);
                InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                byte[] decompressedMaze = new byte[mazeDimensions[0]*mazeDimensions[1]+12 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
              //  System.out.println("decompress size: "+ decompressedMaze.length);
                is.read(decompressedMaze); //Fill decompressedMaze with bytes
                Maze MFromServer = new Maze(decompressedMaze);
                ourMaze=MFromServer;
                characterPositionRow=ourMaze.getStartPosition().getRowIndex();
                characterPositionColumn=ourMaze.getStartPosition().getColumnIndex();
                endPosition=new Position(ourMaze.getGoalPosition().getRowIndex(), ourMaze.getGoalPosition().getColumnIndex());
                setChanged();
                notifyObservers();

            } catch (IOException e) {
              //  e.printStackTrace();
            } catch (ClassNotFoundException e) {
              //  e.printStackTrace();
            }


        }
    }

    public void solveMaze(){
        Client client = null;
        try {
            client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        Maze maze = ourMaze;
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        ourSolution=mazeSolution;
                        setChanged();
                        notifyObservers();
                    } catch (Exception e) {
                     //   e.printStackTrace();
                    }
                }
            });
        } catch (UnknownHostException e) {
           // e.printStackTrace();
        }
        client.communicateWithServer();

    }




    @Override
    public int[][] getMaze() {
        return ourMaze.getTheMaze();
    }

    @Override
    public Solution getSolution() {
        return ourSolution;
    }



    @Override
    public void moveCharacter(KeyCode movement) {

        switch (movement.getName()) {
            case "8":
                if(characterPositionRow-1>=0){
                    if((ourMaze.getTheMaze())[characterPositionRow-1][characterPositionColumn]!=1)
                        characterPositionRow--;
                }
                break;
            case "Up":
                if(characterPositionRow-1>=0){
                    if((ourMaze.getTheMaze())[characterPositionRow-1][characterPositionColumn]!=1)
                        characterPositionRow--;
                }
                break;
            case "2":
                if (characterPositionRow + 1 < ourMaze.getTheMaze().length) {
                    if((ourMaze.getTheMaze())[characterPositionRow+1][characterPositionColumn]!=1)
                        characterPositionRow++;
                }
                break;
            case "Down":
                if (characterPositionRow + 1 < ourMaze.getTheMaze().length) {
                    if((ourMaze.getTheMaze())[characterPositionRow+1][characterPositionColumn]!=1)
                        characterPositionRow++;
                }
                break;
            case "6":
                if (characterPositionColumn + 1 < ourMaze.getTheMaze()[0].length) {
                    if((ourMaze.getTheMaze())[characterPositionRow][characterPositionColumn+1]!=1)
                        characterPositionColumn++;
                }
                break;
            case "Right":
                if (characterPositionColumn + 1 < ourMaze.getTheMaze()[0].length) {
                    if((ourMaze.getTheMaze())[characterPositionRow][characterPositionColumn+1]!=1)
                        characterPositionColumn++;
                }
                break;
            case "4":
                if(characterPositionColumn-1>=0){
                    if((ourMaze.getTheMaze())[characterPositionRow][characterPositionColumn-1]!=1)
                        characterPositionColumn--;
                }
                break;
            case "Left":
                if(characterPositionColumn-1>=0){
                    if((ourMaze.getTheMaze())[characterPositionRow][characterPositionColumn-1]!=1)
                        characterPositionColumn--;
                }
                break;
            case "7":
                if(characterPositionRow-1>=0 && characterPositionColumn-1>=0){
                    if((ourMaze.getTheMaze())[characterPositionRow-1][characterPositionColumn-1]!=1){
                        characterPositionColumn--;
                        characterPositionRow--;
                    }
                }
                break;
            case "9":
                if(characterPositionRow-1>=0 && characterPositionColumn+1<ourMaze.getTheMaze()[0].length){
                if((ourMaze.getTheMaze())[characterPositionRow-1][characterPositionColumn+1]!=1){
                    characterPositionColumn++;
                    characterPositionRow--;
                }
            }
                break;
            case "3":
                if(characterPositionRow+1<ourMaze.getTheMaze().length && characterPositionColumn+1<ourMaze.getTheMaze()[0].length){
                    if((ourMaze.getTheMaze())[characterPositionRow+1][characterPositionColumn+1]!=1){
                        characterPositionColumn++;
                        characterPositionRow++;
                    }
                }
                break;
            case "1":
                if(characterPositionRow+1<ourMaze.getTheMaze().length && characterPositionColumn-1>=0){
                    if((ourMaze.getTheMaze())[characterPositionRow+1][characterPositionColumn-1]!=1){
                        characterPositionColumn--;
                        characterPositionRow++;
                    }
                }
                break;

        }
        if(characterPositionColumn==getMaze()[0].length-1){
            isFinished=true;
        }
        setChanged();
        notifyObservers();
    }


    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }
}
