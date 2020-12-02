package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;
    byte [] output = null;
    public static int numOfWrite;

    public MyCompressorOutputStream(OutputStream out){
        this.out=out;
    }
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        /*
        for(int i=0; i<12; i++){
            write((int)b[i]);
        }
        */
        Deflater deflater = new Deflater();
        deflater.setInput(b);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(b.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
         output = outputStream.toByteArray();
        for(int i=0; i<output.length; i++){
            write(output[i]);
            numOfWrite++;
        }


    }
    public byte[] getByteArray(){
        return output;
    }

    /*
    public void secondAlgo(byte[] b) throws IOException {
        for(int i=0; i<12; i++){
            write((int)b[i]);
        }
        Deflater deflater = new Deflater();
        deflater.setInput(b);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(b.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        for(int i=0; i<output.length; i++){
            write(output[i]);
        }

    }
    */
}
