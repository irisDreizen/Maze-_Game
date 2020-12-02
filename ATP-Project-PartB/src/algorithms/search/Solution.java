package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable {

    private ArrayList<AState> solution;

    public Solution(ArrayList<AState> sol){
        this.solution = sol;
    }
    public ArrayList<AState> getSolutionPath() {
        return solution;
    }


}
