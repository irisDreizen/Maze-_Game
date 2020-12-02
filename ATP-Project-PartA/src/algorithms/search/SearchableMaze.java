package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    protected Maze maze;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        return new MazeState(maze.getStartPosition());
    }

    @Override
    public AState getGoalState() {
        return new MazeState(maze.getGoalPosition());
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> arrayToRturn = new ArrayList<>();
        int [][] MatrixMaze = maze.getTheMaze();
        Position curr = ((MazeState)s).getP();
        if(curr.getRowIndex()-1!=-1 && MatrixMaze[curr.getRowIndex()-1][curr.getColumnIndex()]==0){//up
            MazeState toAdd8=new MazeState(new Position(curr.getRowIndex()-1, curr.getColumnIndex()));
            toAdd8.parent=s;
            if(toAdd8.parent!=null) {
                toAdd8.cost = (toAdd8.parent).cost + 10;
            }
            else{
                toAdd8.cost = 10;
            }
            arrayToRturn.add(toAdd8);
        }
        if(curr.getRowIndex()-1!=-1 && curr.getColumnIndex()+1<MatrixMaze[0].length && MatrixMaze[curr.getRowIndex()-1][curr.getColumnIndex()+1]==0 && (MatrixMaze[curr.getRowIndex()-1][curr.getColumnIndex()]==0 || MatrixMaze[curr.getRowIndex()][curr.getColumnIndex()+1]==0)){//1 p.m
            MazeState toAdd7=new MazeState(new Position(curr.getRowIndex()-1, curr.getColumnIndex()+1));
            toAdd7.parent=s;
            if(toAdd7.parent!=null) {
                toAdd7.cost = (toAdd7.parent).cost + 15;
            }
            else{
                toAdd7.cost = 15;
            }
            arrayToRturn.add(toAdd7);
        }
        if(curr.getColumnIndex()+1<MatrixMaze[0].length && MatrixMaze[curr.getRowIndex()][curr.getColumnIndex()+1]==0){//right
            MazeState toAdd6 = new MazeState(new Position(curr.getRowIndex(), curr.getColumnIndex()+1));
            toAdd6.parent=s;
            if(toAdd6.parent!=null) {
                toAdd6.cost = (toAdd6.parent).cost + 10;
            }
            else{
                toAdd6.cost = 10;
            }
            arrayToRturn.add(toAdd6);
        }
        if(curr.getRowIndex()+1<MatrixMaze.length && curr.getColumnIndex()+1<MatrixMaze[0].length && MatrixMaze[curr.getRowIndex()+1][curr.getColumnIndex()+1]==0 && (MatrixMaze[curr.getRowIndex()][curr.getColumnIndex()+1]==0 || MatrixMaze[curr.getRowIndex()+1][curr.getColumnIndex()]==0)){//5 p.m
            MazeState toAdd5 =new MazeState(new Position(curr.getRowIndex()+1, curr.getColumnIndex()+1));
            toAdd5.parent=s;
            if(toAdd5.parent!=null) {
                toAdd5.cost = (toAdd5.parent).cost + 15;
            }
            else{
                toAdd5.cost = 15;
            }
            arrayToRturn.add(toAdd5);
        }
        if(curr.getRowIndex()+1<MatrixMaze.length && MatrixMaze[curr.getRowIndex()+1][curr.getColumnIndex()]==0){//down
            MazeState toAdd4=new MazeState(new Position(curr.getRowIndex()+1, curr.getColumnIndex()));
            toAdd4.parent=s;
            if(toAdd4.parent!=null) {
                toAdd4.cost = (toAdd4.parent).cost + 10;
            }
            else{
                toAdd4.cost = 10;
            }
            arrayToRturn.add(toAdd4);
        }
        if(curr.getRowIndex()+1<MatrixMaze.length && curr.getColumnIndex()-1!=-1 && MatrixMaze[curr.getRowIndex()+1][curr.getColumnIndex()-1]==0 &&(MatrixMaze[curr.getRowIndex()+1][curr.getColumnIndex()]==0 || MatrixMaze[curr.getRowIndex()][curr.getColumnIndex()-1]==0)){//7 p.m
            MazeState toAdd3=new MazeState(new Position(curr.getRowIndex()+1, curr.getColumnIndex()-1));
            toAdd3.parent=s;
            if(toAdd3.parent!=null) {
                toAdd3.cost = (toAdd3.parent).cost + 15;
            }
            else{
                toAdd3.cost = 15;
            }
            arrayToRturn.add(toAdd3);
        }
        if(curr.getColumnIndex()-1!=-1 && MatrixMaze[curr.getRowIndex()][curr.getColumnIndex()-1]==0){//left
            MazeState toAdd2=new MazeState(new Position(curr.getRowIndex(), curr.getColumnIndex()-1));
            toAdd2.parent=s;
            if(toAdd2.parent!=null) {
                toAdd2.cost = (toAdd2.parent).cost + 10;
            }
            else{
                toAdd2.cost = 10;
            }
            arrayToRturn.add(toAdd2);
        }
        if(curr.getRowIndex()-1!=-1 && curr.getColumnIndex()-1!=-1 && MatrixMaze[curr.getRowIndex()-1][curr.getColumnIndex()-1]==0&& (MatrixMaze[curr.getRowIndex()][curr.getColumnIndex()-1]==0 ||MatrixMaze[curr.getRowIndex()-1][curr.getColumnIndex()]==0)){//11 p.m
            MazeState toAdd1=new MazeState(new Position(curr.getRowIndex()-1, curr.getColumnIndex()-1));
            toAdd1.parent=s;
            if(toAdd1.parent!=null) {
                toAdd1.cost = (toAdd1.parent).cost + 15;
            }
            else{
                toAdd1.cost = 15;
            }
            arrayToRturn.add(toAdd1);
        }
        return arrayToRturn;
    }


}
