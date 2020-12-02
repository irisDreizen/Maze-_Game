package algorithms.mazeGenerators;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DataFormatException;

public class Main {
    private static final String path = "C:\\Users\\iris dreizenshtok\\check.txt";
    public static void main(String[] args) throws IOException {
        Maze c=new Maze();
        Thread t = new Thread();
//        EmptyMazeGenerator d = new EmptyMazeGenerator();
//        SimpleMazeGenerator s = new SimpleMazeGenerator();
//        c=s.generate(100,100);
//        System.out.println(s.measureAlgorithmTimeMillis(5,5));
   //      c.print();

        MyMazeGenerator my= new MyMazeGenerator();
       c=my.generate(15,15);
      //  c.print();
        System.out.println(" ");
        byte[] byteArray = c.toByteArray();


        try (OutputStream out = new FileOutputStream(path)) {

            MyCompressorOutputStream compressor = new MyCompressorOutputStream(out);
          //  compressor.secondAlgo(byteArray);

            MyDecompressorInputStream decompressor = new MyDecompressorInputStream(new FileInputStream(path));
           byte[] checkkkkk=  decompressor.decompress();
           Maze checkMaze = new Maze(checkkkkk);
          //  checkMaze.print();



        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }


        //  System.out.println(my.measureAlgorithmTimeMillis(10000,1000));


    }
}
