package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    public static Properties prop = new Properties();
    //strategy
    //numOfThreads
    //generator


    public static void OperateConfiguration(){
        InputStream input=null;
        try{
            input=new FileInputStream("resources/config.properties");
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(input!=null){
                try{
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static int getNumberOfThreads(){
        String str= prop.getProperty(("NumOfThreads"));
        return Integer.parseInt(str);

    }

    public static AMazeGenerator getMaze(){
        String str= prop.getProperty(("MazeGenerator"));
        if(str.equals("MyMazeGenerator")){
            return new MyMazeGenerator();
        }
        else if(str.equals("SimpleMazeGenerator")){
            return new SimpleMazeGenerator();
        }
        else if(str.equals("EmptyMazeGenerator")){
            return new EmptyMazeGenerator();
        }

        return new MyMazeGenerator();


    }

    public static ASearchingAlgorithm getSearchingAlgorithm(){
        String str= prop.getProperty(("SearchAlgorithm"));
        if(str.equals(" BestFirstSearch")){
            return new BestFirstSearch();
        }
        else if(str.equals("BreadthFirstSearch")){
            return new BreadthFirstSearch();
        }
        else if(str.equals("DepthFirstSearch")){
            return new DepthFirstSearch();
        }

        return new DepthFirstSearch();


    }
}



