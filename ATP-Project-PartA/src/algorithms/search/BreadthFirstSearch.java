package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected Queue<AState> queue;
    public BreadthFirstSearch() {
        this.algoName="BFS";
        this.numberOfNodesEvaluated=0;
        queue = new LinkedList<>();
    }


    @Override
    public Solution solve(ISearchable s) {
        LinkedList<AState> tempSol = new LinkedList<>();
        ArrayList<AState> solution = new ArrayList<>();
        LinkedList<AState> visited = new LinkedList<>();
        queue.add(s.getStartState());
        visited.addLast(s.getStartState());
        while(!queue.isEmpty()){
            AState node = queue.remove();
            this.numberOfNodesEvaluated++;
            if(node.equals(s.getGoalState())){
                tempSol.addLast(node);
                node=node.parent;
                while(node!=null){
                    tempSol.addLast(node);
                    node=node.parent;
                }
                while(!tempSol.isEmpty()){
                    solution.add(tempSol.removeLast());
                }
                return  new Solution(solution);
            }
            else{
                for (AState child:s.getAllPossibleStates(node)) {
                    if(!visited.contains(child)){
                        visited.addLast(child);
                        queue.add(child);
                        child.parent=node;
                    }
                }
            }

        }

        return new Solution(solution);
    }


}

