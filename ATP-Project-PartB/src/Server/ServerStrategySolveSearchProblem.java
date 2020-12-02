package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    int checkIndex=0;
    int numofMaze=0;
    int numOfFiles=0;
    String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        ObjectInputStream fromClient = null;
        ObjectOutputStream toClient=null;
        try {
            fromClient = new ObjectInputStream(inFromClient);
            toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            Maze mazeFromClient= (Maze) fromClient.readObject();
            //add check if maze exist
            int numOfEqual = findEqualMaze(mazeFromClient);
            if(numOfEqual!=0){

                String fileSolName = tempDirectoryPath+ "\\" + "solution" + numOfEqual+ ".bin";
               Solution solution= readSolution(fileSolName);
                toClient.writeObject(solution);
            }

            else{
                //writeMaze
                int temp= numofMaze+1;
                numofMaze++;
                String fileName =tempDirectoryPath+ "\\maze" +temp+".bin";
                FileOutputStream f = new FileOutputStream(new File(fileName));
                ObjectOutputStream o = new ObjectOutputStream(f);

                // Write objects to file
                o.writeObject(mazeFromClient);

                f.close();
                o.close();

                //search for solustion
                SearchableMaze domain=new SearchableMaze(mazeFromClient);
                ASearchingAlgorithm searcher = Configurations.getSearchingAlgorithm();
                Solution solution = searcher.solve(domain);



               // writeSolution
                String fileName1 = tempDirectoryPath+ "\\solution"+temp+".bin";
                FileOutputStream f1 = new FileOutputStream(new File(fileName1));
                ObjectOutputStream o1 = new ObjectOutputStream(f1);

                // Write objects to file
                o1.writeObject(solution);
                f1.close();
                o1.close();


                numOfFiles++;


                toClient.writeObject(solution);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    private void writeMaze(Maze maze){
        try {

            int temp= numofMaze+1;
            numofMaze++;
            String fileName =tempDirectoryPath+ "\\maze" +temp+".bin";
            System.out.println("the directory i write the maze:" + fileName);
            FileOutputStream f = new FileOutputStream(new File(fileName));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(maze);
            numOfFiles++;
            f.close();
            o.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    /*
    private void writeSolutin(Solution sol){
        try {
            String fileName = tempDirectoryPath+ "\\solution"+numofMaze+".bin";

            System.out.println("the directory i write the maze:" + fileName);
            FileOutputStream f = new FileOutputStream(new File(fileName));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(sol);
            f.close();
            o.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
*/


    private Solution readSolution(String fileName){
        Solution sol=null;
       // ArrayList<AState> toSol = new ArrayList<>();
        try {
            FileInputStream fi = new FileInputStream(new File(fileName));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            sol = (Solution) oi.readObject();
            fi.close();
            oi.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return sol;
    }

    private int findEqualMaze(Maze maze) {
        byte [] bInput = maze.toByteArray();
        if (numOfFiles > 0){
            for (int i = 1; i < numOfFiles + 1; i++) {
                String fileName = tempDirectoryPath + "\\maze" + i+".bin";
                try {

                    FileInputStream fi = new FileInputStream(new File(fileName));
                    ObjectInputStream oi = new ObjectInputStream(fi);

                    // Read objects
                    Maze mazeFromFile = (Maze) oi.readObject();
                    byte [] bReadFromFile = mazeFromFile.toByteArray();
                    String sReadFromFile = new String(bReadFromFile);
                    String sInput = new String(bInput);

                    if(sReadFromFile.equals(sInput)){
                        return i;
                    }
                    fi.close();
                    oi.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }

        return 0;
    }


}
