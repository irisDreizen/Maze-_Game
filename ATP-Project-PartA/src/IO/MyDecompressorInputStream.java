package IO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {//not sure what should wi overide here

        /*
        // Build the dictionary.
        int dictSize = 2;
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < 2; i++)
            dictionary.put(i, "" + (char)i);

        String w = "" + (char)(int)in.read();
        StringBuffer result = new StringBuffer(w);
        int i=in.read();
        while (i!=-1) {
            String entry;
            if (dictionary.containsKey(i))
                entry = dictionary.get(i);
            else if (i == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + i);

            result.append(entry);

            // Add w+entry[0] to the dictionary.
            dictionary.put(dictSize++, w + entry.charAt(0));

            w = entry;
           i=in.read();
        }
        return result.toString();


*/
        return in.read();
    }


}
