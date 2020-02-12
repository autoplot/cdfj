package gov.nasa.gsfc.spdf.cdfj;
import java.nio.*;

/**
 *
 * @author nand
 */
public class GDR {
    ByteBuffer record = ByteBuffer.allocate(8 + 4 + 8 + 8 + 8 +
        8 + 4 + 4 + 4 + 4 + 4 + 8 + 4 + 4 + 4);

    /**
     *
     */
    protected long position;
    long zVDRHead;
    long aDRHead;
    long eof;
    int numAttr;
    int nzVars;
    int lastLeapSecondId;

    /**
     *
     * @param l
     */
    public void setZVDRHead(long l) {
        zVDRHead = l;
    }

    /**
     *
     * @param l
     */
    public void setADRHead(long l) {
        aDRHead = l;
    }

    /**
     *
     * @param l
     */
    public void setEof(long l) {
        eof = l;
    }

    /**
     *
     * @param n
     */
    public void setNumAttr(int n) {
        numAttr = n;
    }

    /**
     *
     * @param n
     */
    public void setNzVars(int n) {
        nzVars = n;
    }

    /**
     *
     * @param n
     */
    public void setLastLeapSecondId(int n) {
        lastLeapSecondId = n;
    }

    /**
     *
     * @return
     */
    public ByteBuffer get() {
        record.position(0);
        record.putLong((long)(record.capacity()));
        record.putInt(2);
        record.putLong(0);
        record.putLong(zVDRHead);
        record.putLong(aDRHead);
        record.putLong(eof);
        record.putInt(0);
        record.putInt(numAttr);
        record.putInt(-1);
        record.putInt(0);
        record.putInt(nzVars);
        record.putLong(0);
        record.putInt(0);
        record.putInt(lastLeapSecondId);
        record.putInt(0);
        record.position(0);
        return record;
    }

    /**
     *
     * @return
     */
    public int getSize() {return record.limit();}
}
