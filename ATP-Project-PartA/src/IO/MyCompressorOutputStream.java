package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;

    public MyCompressorOutputStream(OutputStream out){
        this.out=out;
    }
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        for(int i=0; i<12; i++){
            write((int)b[i]);
        }

        String uncompressed=new String(b);
        // Build the dictionary.
        int dictSize = 2;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 2; i++)
            dictionary.put("" + (char)i, i);

        String w = "";
        //List<Integer> result = new ArrayList<Integer>();
        for (char c : uncompressed.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                write(dictionary.get(w));
                // Add wc to the dictionary.
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }

        // Output the code for w.
        if (!w.equals(""))
            write(dictionary.get(w));


    }
}
