package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    protected String algoName;
    protected int numberOfNodesEvaluated;
    @Override
    public String getName() {
        return algoName;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated;
    }

}
