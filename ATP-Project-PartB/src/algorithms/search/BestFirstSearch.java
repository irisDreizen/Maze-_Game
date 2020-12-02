package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch {
    public BestFirstSearch() {
        this.algoName= "BestFirstSearch";
        this.numberOfNodesEvaluated=0;
        queue = new PriorityQueue<>();
    }

    @Override
    public Solution solve(ISearchable s) {
        Solution solve = super.solve(s);

        return solve;
    }



}
