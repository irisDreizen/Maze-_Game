package algorithms.mazeGenerators;

public interface IMazeGenerator {

    public Maze generate(int row,int columns);
    public long measureAlgorithmTimeMillis(int row,int columns);
}
