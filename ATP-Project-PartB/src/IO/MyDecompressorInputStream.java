package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    public byte[] decompress() throws IOException, DataFormatException {
        return null;
    }

    @Override
    public int read(byte[] toRturnArray) throws IOException {

        int[] firstParameters = new int[12];
        //read first parameters
        /*
        for (int i = 0; i < 12; i++) {
            firstParameters[i] = ((byte)in.read());
        }
        */


        //transform from list to array

        ByteArrayOutputStream buffer12 = new ByteArrayOutputStream();

        int nRead;
        byte[] data1 = new byte[16384];

        while ((nRead = in.read(data1, 0, data1.length)) != -1) {
            buffer12.write(data1, 0, nRead);
        }

       byte[] data= buffer12.toByteArray();

        //decompress
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {

            try {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);

            } catch (DataFormatException e) {
                e.printStackTrace();
            }

        }
        outputStream.close();

        byte[] tempMaze = outputStream.toByteArray();
        for(int i=0; i<toRturnArray.length;i++){
            toRturnArray[i]=tempMaze[i];
        }


        return toRturnArray.length;
    }
    /*
    public int secondOption(byte[] b) throws DataFormatException, IOException {

        List<Byte> tempList = new ArrayList<>();
        Byte curr=(byte)in.read();
        while(curr!=-1){
            tempList.add(curr);
            curr=(byte)in.read();
        }
        byte [] data=new byte[tempList.size()];
        for(int i=0;i<data.length;i++){
            data[i]=tempList.get(i);
        }
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        b = outputStream.toByteArray();

        return b.length;
    }
    */
}
