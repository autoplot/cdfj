package gov.nasa.gsfc.spdf.cdfj;
import java.nio.*;

/**
 *
 * @author nand
 */
public class VariableDataBuffer {
    int firstRecord;
    int lastRecord;
    ByteBuffer buffer;
    boolean compressed;
    VariableDataBuffer(int first, int last, ByteBuffer buf, boolean comp) {
        firstRecord = first;
        lastRecord = last;
        buffer = buf;
        compressed = comp;
    }

    /**
     *
     * @return
     */
    public int getFirstRecord() {return firstRecord;}

    /**
     *
     * @return
     */
    public int getLastRecord() {return lastRecord;}

    /**
     *
     * @return
     */
    public ByteBuffer getBuffer() {return buffer;}

    /**
     *
     * @return
     */
    public boolean isCompressed() {return compressed;}
}
