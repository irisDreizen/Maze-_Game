package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int column;



    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumnIndex() {
        return column;
    }

    public int getRowIndex() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        String s= "{"+row+","+column+"}";
        return s;
    }
}
