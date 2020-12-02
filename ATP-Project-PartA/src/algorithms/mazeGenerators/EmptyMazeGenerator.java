package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    public Maze generate(int row,int columns){
        if(row<0 || columns<0){
            return  null;
        }
        int [][] maze= new int [row][columns];
        for (int i=0; i<row ; i++) {
            for (int j=0;j<columns;j++) {
                maze[i][j] = 0;
            }
        }
       Maze mazeToReturn = new Maze();
        mazeToReturn.setTheMaze(maze);
        mazeToReturn.setStartPosition(getRandomStartPosition(row,columns));
        mazeToReturn.setEndPosition(getRandomEndPosition(row,columns));
        return mazeToReturn;
    }
}

