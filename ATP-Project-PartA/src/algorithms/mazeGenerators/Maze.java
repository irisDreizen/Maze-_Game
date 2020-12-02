package algorithms.mazeGenerators;

public class Maze {
    private int [][] theMaze;
    private Position startPosition;
    private Position endPosition;

    public Maze(byte [] byteArray) {
        int rowSize= byteArray[0]*255+byteArray[1];
        int colSize=byteArray[2]*255+byteArray[3];
        int rowStart =byteArray[4]*255+byteArray[5];
        int colStart =byteArray[6]*255+byteArray[7];
        int rowEnd =byteArray[8]*255+byteArray[9];
        int colEnd =byteArray[10]*255+byteArray[11];

        this.startPosition=new Position(rowStart,colStart);
        this.endPosition=new Position(rowEnd,colEnd);
        this.theMaze= new int[rowSize][colSize];
        int counter=12;
        for(int i=0; i<rowSize; i++){
            for(int j=0; j<colSize; j++){
                this.theMaze[i][j]=byteArray[counter];
                counter++;
            }
        }





}

    public int[][] getTheMaze() {
        return theMaze;
    }

    public void setTheMaze(int[][] theMaze) {
        this.theMaze = theMaze;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getGoalPosition() {
        return endPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    public int getRowSize(){
        return theMaze[0].length;
    }
    public int getColumnSize(){
        return theMaze.length;
    }
    protected void changeCell(int row,int column, int changeNum){
        theMaze[row][column]=changeNum;
    }
    public void print(){
        for (int i=0; i<theMaze.length; i++){
            for (int j=0; j<theMaze[0].length; j++){
                if(i==startPosition.getRowIndex()&&j==startPosition.getColumnIndex()){
                    System.out.print("S");
                }
                else if(i==endPosition.getRowIndex()&&j==endPosition.getColumnIndex()){
                    System.out.println("E");
                }
                else if(j==theMaze[0].length-1){
                    System.out.println(theMaze[i][j]);
                }
                else {
                    System.out.print(theMaze[i][j]);
                }

            }
        }
    }
    protected byte[] toByteArray(){
       int matrixSize = this.getRowSize()*this.getColumnSize();
       byte [] toReturn = new byte[matrixSize+12];
       int temp0= this.getRowSize()/255;
       toReturn[0]=(byte)temp0;//row size-complete numbers
        int temp1=this.getRowSize()%255;
        toReturn[1]=(byte)temp1;//row size-fraction
        int temp2=this.getColumnSize()/255;
        toReturn[2]=(byte)temp2;//column size- complete numbers
        int temp3=this.getColumnSize()%255;
        toReturn[3]=(byte)temp2;//column size- fraction
        int temp4=this.startPosition.getRowIndex()/255;
        toReturn[4]=(byte)temp4;//row start position
        int temp5=this.startPosition.getRowIndex()%255;
        toReturn[5]=(byte)temp5;//row start position
        int temp6=this.startPosition.getColumnIndex()/255;
        toReturn[6]=(byte)temp6;//column start position
        int temp7=this.startPosition.getColumnIndex()%255;
        toReturn[7]=(byte)temp7;//column start position
        int temp8=this.endPosition.getRowIndex()/255;
        toReturn[8]=(byte)temp8;//row end position
        int temp9=this.endPosition.getRowIndex()%255;
        toReturn[9]=(byte)temp9;//row end position
        int temp10=this.endPosition.getColumnIndex()/255;
        toReturn[10]=(byte)temp10;//column end position
        int temp11=this.endPosition.getColumnIndex()%255;
        toReturn[11]=(byte)temp11;//column end position
        int counter=12;
        for(int i=0; i<this.getColumnSize(); i++){
            for(int j=0;j<this.getRowSize();j++){
                toReturn[counter]=(byte)theMaze[i][j];
                counter++;
            }
        }
        return toReturn;
    }


}
