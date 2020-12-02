package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends  AState {
    private Position p;

    public MazeState(Position p) {
        this.p = p;
        this.cost=0;
    }
    public MazeState(Position p,int cost) {
        this.p = p;
        this.cost=cost;
    }

    public Position getP() {
        return p;
    }

    public void setP(Position p) {
        this.p = p;
    }


    @Override
    public boolean equals(Object o) {
        return ((MazeState)o).getP().getRowIndex()==p.getRowIndex() && ((MazeState)o).getP().getColumnIndex()==p.getColumnIndex();
    }

    @Override
    public String toString() {
        return p.toString();
    }
}
