package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
      //  String mazeFileName="savedMaze";
        ObjectInputStream fromClient = null;
        ObjectOutputStream toClient=null;
        try {
            fromClient = new ObjectInputStream(inFromClient);
            toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            Maze maze = new Maze();
            AMazeGenerator my= Configurations.getMaze();
            int [] mazeDitails =  (int [])fromClient.readObject();
            maze=my.generate(mazeDitails[0],mazeDitails[1]);
            File writeTo = File.createTempFile("savedMaze",".txt");
            MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(new FileOutputStream(writeTo));
            compressorOutputStream.write(maze.toByteArray());
            writeTo.deleteOnExit();
            toClient.writeObject(compressorOutputStream.getByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
