package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator {
    public Maze generate(int row, int columns){
        if(row<0 || columns<0){
            return  null;
        }
        int [][] maze= new int [row][columns];
        for (int i=0; i<row ; i++) {
            for (int j=0;j<columns;j++) {
                maze[i][j] = 1;
            }
        }
        Maze mazeToReturn = new Maze();
        mazeToReturn.setTheMaze(maze);
        mazeToReturn.setStartPosition(getRandomStartPosition(row,columns));

        int rowIndex=mazeToReturn.getStartPosition().getRowIndex();
        int columnIndex=mazeToReturn.getStartPosition().getColumnIndex();
        boolean isFound=false;

        while(!isFound){
            if(columnIndex==columns-1){
                isFound=true;
                mazeToReturn.changeCell(rowIndex,columnIndex,0);
                mazeToReturn.setEndPosition(new Position(rowIndex,columnIndex));
            }
            else if(rowIndex==0){
                int choice=(int)(Math.random()*2)+1;
                mazeToReturn.changeCell(rowIndex,columnIndex,0);
                if(choice==1){
                    columnIndex++;
                }
                else{
                    rowIndex++;
                }
            }
            else if(rowIndex==mazeToReturn.getRowSize()-1){
                int choice=(int)(Math.random()*1);
                mazeToReturn.changeCell(rowIndex,columnIndex,0);
                if(choice==1){
                    columnIndex++;
                }
                else{//we need to move up
                    rowIndex--;
                }
            }
            else{
                int choice=(int)(Math.random()*2);
                mazeToReturn.changeCell(rowIndex,columnIndex,0);
                if(choice==1){
                    columnIndex++;
                }
                else if(choice==2){//we need to move up
                    rowIndex++;
                }
                else{
                    rowIndex--;
                }
            }
        }



        return mazeToReturn;
    }
}
