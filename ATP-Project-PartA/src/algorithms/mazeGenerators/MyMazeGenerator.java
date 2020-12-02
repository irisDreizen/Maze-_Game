package algorithms.mazeGenerators;
import java.util.LinkedList;

public class MyMazeGenerator extends AMazeGenerator{
    public Maze generate(int row,int columns){
        if(row<0 || columns<0){
            return  null;
        }
        Maze maze = new Maze();
        Position start = getRandomStartPosition(row,columns);
        Position end = getRandomEndPosition(row,columns);
        int [][] MatrixMaze= new int [row][columns];
        for (int i=0; i<row ; i++) {
            for (int j=0;j<columns;j++) {
                MatrixMaze[i][j] = 1;
            }
        }
        int rowToRun = start.getRowIndex();
        int colToRun = start.getColumnIndex();
        int nextChoice=0;
        LinkedList<Position> list = new LinkedList<Position>();
        MatrixMaze[start.getRowIndex()][start.getColumnIndex()]=0;
        list=addToList(list, MatrixMaze,start.getRowIndex(),start.getColumnIndex());
        while(!list.isEmpty()){
            int indexList = (int)(Math.random()*(list.size()-1));
            Position tempP=list.remove(indexList);
            Position PtoAddToList= isDivide(MatrixMaze,tempP.getRowIndex(),tempP.getColumnIndex());
            if(PtoAddToList!=null){//if tempP isdivided
                MatrixMaze[tempP.getRowIndex()][tempP.getColumnIndex()]=0;
                if(PtoAddToList.getRowIndex()>=0 && PtoAddToList.getRowIndex()<MatrixMaze.length && PtoAddToList.getColumnIndex()>=0 && PtoAddToList.getColumnIndex()<MatrixMaze[0].length){
                    MatrixMaze[PtoAddToList.getRowIndex()][PtoAddToList.getColumnIndex()]=0;
                    list=addToList(list,MatrixMaze,PtoAddToList.getRowIndex(),PtoAddToList.getColumnIndex());
                }
            }

        }

        for(int i=0; i<MatrixMaze.length; i++){
            if(MatrixMaze[i][MatrixMaze[0].length-1]==0){
                maze.setEndPosition(new Position(i,MatrixMaze[0].length-1));
                i=MatrixMaze.length;
            }
        }
        maze.setTheMaze(MatrixMaze);
        maze.setStartPosition(start);


         return maze;
    }



    protected LinkedList<Position> addToList(LinkedList<Position> l,int [][] maze,int row, int col){
        if(row-1>=0 && maze[row-1][col]==1) {//up
            l.add(new Position(row - 1, col));
        }
        if(row+1<maze.length && maze[row+1][col]==1){//down
            l.add(new Position(row +1, col));
        }
        if(col-1>=0 && maze[row][col-1]==1) {//left
            l.add(new Position(row , col-1));
        }
        if(col+1<maze[0].length && maze[row][col+1]==1){//down
            l.add(new Position(row , col+1));
        }
        return l;
    }

    protected  Position isDivide(int [][] maze,int row, int col){
        boolean isSuccssed=false;
        int howToChaeck = (int)(Math.random()*1); //1 is lest-right check, 0 is up-down check
        if(howToChaeck==1 && !isSuccssed){//check is we have 1 answer at first
            if((col-1==-1 &&maze[row][col+1]==0)){
                return new Position(row, col-1);
            }
            else if((col+1==maze[0].length && maze[row][col-1]==0)){
                return new Position(row, col+1);
            }

            else if(!(col-1==-1)){
                if(!(col+1==maze[0].length)){
                    if((maze[row][col-1]==0&&maze[row][col+1]==1)){
                        isSuccssed=true;
                        return new Position(row, col+1);
                    }
                    else if ((maze[row][col-1]==1&&maze[row][col+1]==0)){
                        isSuccssed=true;
                        return new Position(row,col-1);
                    }
                }
            }
        }
       if(howToChaeck==0 || !isSuccssed){

           if(row-1==-1 &&maze[row+1][col]==0){
               isSuccssed=true;
               return new Position(row-1, col);
           }
          else if(row+1==maze.length &&maze[row-1][col]==0) {
               isSuccssed = true;
               return new Position(row + 1, col);
           }
           else if(!(row-1==-1)){
               if (!(row+1==maze.length)) {
                   if ((maze[row + 1][col] == 0 && maze[row - 1][col] == 1)) {
                       isSuccssed = true;
                       return new Position(row - 1, col);
                   } else if ((maze[row + 1][col] == 1 && maze[row - 1][col] == 0)) {
                       isSuccssed = true;
                       return new Position(row + 1, col);
                   }
               }
           }
           if(!isSuccssed){
               if((col-1==-1 &&maze[row][col+1]==0)){
                   return new Position(row, col-1);
               }
               else if((col+1==maze[0].length && maze[row][col-1]==0)){
                   return new Position(row, col+1);
               }

               else if(!(col-1==-1)){
                   if(!(col+1==maze[0].length)){
                       if((maze[row][col-1]==0&&maze[row][col+1]==1)){
                           isSuccssed=true;
                           return new Position(row, col+1);
                       }
                       else if ((maze[row][col-1]==1&&maze[row][col+1]==0)){
                           isSuccssed=true;
                           return new Position(row,col-1);
                       }
                   }
               }

           }

       }
       return null;
    }

}
