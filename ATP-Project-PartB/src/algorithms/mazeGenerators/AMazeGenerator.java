package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    public long measureAlgorithmTimeMillis(int row,int columns){
        long start= System.currentTimeMillis();
        generate(row,columns);
        long end= System.currentTimeMillis();
        return end-start;
    }
    protected Position getRandomStartPosition(int row, int col){
        /*
        int RorC =(int) (Math.random()*10)%2;
        if(RorC==0){//it's first row
            int start1=(int) (Math.random()*maze.getColumnSize());
            return new Position(0,start1);

        }
        */
        //it's first column
            int start2=(int) (Math.random()*(row-1));
            return new Position(start2,0);


    }

    protected Position getRandomEndPosition(int row, int col){
        /*
        int RorC =(int) (Math.random()*10)%2;
        if(RorC==0){//it's last row
            int start1=(int) (Math.random()*maze.getColumnSize());
            return new Position(maze.getRowSize()-1,start1);

        }
        */
       //it's last column
            int start2=(int) (Math.random()*(row-1));
            return new Position(start2,col-1);


    }

}
