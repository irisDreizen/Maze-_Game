package View;

import Server.Configurations;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.search.ASearchingAlgorithm;

public class properties {

    public AMazeGenerator generate= Configurations.getMaze();//For Binding
    public ASearchingAlgorithm search=Configurations.getSearchingAlgorithm(); //For Binding
    public int numThreads=Configurations.getNumberOfThreads(); //For Binding

    public String getGenerate() {
        String s ="";
        if (generate  instanceof algorithms.mazeGenerators.MyMazeGenerator) {
            s = "MyMazeGenerator";
        }
        else if(generate  instanceof algorithms.mazeGenerators.EmptyMazeGenerator){
            s = "EmptyMazeGenerator";
        }
        else{
            s = "SimpleMazeGenerator";
        }
        return s;
    }

    public String getSearch() {
        String s ="";
        if (search instanceof algorithms.search.BestFirstSearch) {
            s = "BestFirstSearch";
        }
        else if(search instanceof algorithms.search.DepthFirstSearch){
            s = "DepthFirstSearch";
        }
        else{
            s = "BreadthFirstSearch";
        }
        return s;
    }

    public String getNumThreads() {
        String s = Integer.toString(numThreads);
        return s;
    }


}
