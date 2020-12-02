package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                column == position.column;
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
