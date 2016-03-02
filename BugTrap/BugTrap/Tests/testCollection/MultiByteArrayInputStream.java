package testCollection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

/**
 *
 * @author Admin
 */
public class MultiByteArrayInputStream extends InputStream {

    public MultiByteArrayInputStream(Queue<String> queue) {
        this.queue = queue;
        if (!queue.isEmpty()) {
            this.byteSource = new ByteArrayInputStream(queue.poll().getBytes());
        }
        flag = false;
    }

    private static final String WHITESPACE = System.lineSeparator();
    private ByteArrayInputStream byteSource;
    private Queue<String> queue;
    private boolean flag;

    @Override
    public int read() throws IOException {
        if (byteSource == null) {
            return -1;
        }

        //Ended a string last time.
        /*if (flag) {
         if (!queue.isEmpty()) {
         byteSource = new ByteArrayInputStream(queue.poll().getBytes());
         } else {
         byteSource = null;
         }
         }*/
        //Read
        int nRead = byteSource.read();

        //Set flag.
        if (nRead == -1) {
            if (flag) {
                if (!queue.isEmpty()) {
                    byteSource = new ByteArrayInputStream(queue.poll().getBytes());
                } else {
                    byteSource = null;
                }
            } else {
                byteSource = new ByteArrayInputStream(WHITESPACE.getBytes());
                flag = true;
            }
        }
        return nRead;
    }

}
