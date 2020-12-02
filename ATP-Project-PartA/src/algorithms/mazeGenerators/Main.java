package algorithms.mazeGenerators;

public class Main {
    public static void main(String[] args) {
        Maze c=new Maze();
//        EmptyMazeGenerator d = new EmptyMazeGenerator();
//        SimpleMazeGenerator s = new SimpleMazeGenerator();
//        c=s.generate(100,100);
//        System.out.println(s.measureAlgorithmTimeMillis(5,5));
//        c.print();

        MyMazeGenerator my= new MyMazeGenerator();
        c=my.generate(10,10);
        c.print();
       // System.out.println(my.measureAlgorithmTimeMillis(1000,1000));


    }
}
