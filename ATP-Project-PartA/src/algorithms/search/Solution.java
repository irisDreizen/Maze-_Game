package algorithms.search;

import java.util.ArrayList;

public class Solution {

    private ArrayList<AState> solution;

    public Solution(ArrayList<AState> sol){
        this.solution = sol;
    }
    public ArrayList<AState> getSolutionPath() {
        return solution;
    }

}
