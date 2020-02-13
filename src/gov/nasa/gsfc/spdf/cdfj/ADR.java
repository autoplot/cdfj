package gov.nasa.gsfc.spdf.cdfj;
import java.nio.*;

/**
 *
 * @author nand
 */
public class ADR {
    ByteBuffer record = ByteBuffer.allocate(8 + 4 + 8 + 8 + 4 +
        4 + 4 + 4 + 4 + 8 + 4 + 4 + 4 + 256);
    long aDRNext;

    /**
     *
     */
    protected long position;

    /**
     *
     * @param l
     */
    public void setADRNext(long l) {
        aDRNext = l;
    }
    long agrEDRHead;

    /**
     *
     * @param l
     */
    public void setAgrEDRHead(long l) {
        agrEDRHead = l;
    }
    int scope;

    /**
     *
     * @param n
     */
    public void setScope(int n) {
        scope = n;
    }

    /**
     *
     */
    protected int num;

    /**
     *
     * @param n
     */
    public void setNum(int n) {
        num = n;
    }
    int ngrEntries;

    /**
     *
     * @param n
     */
    public void setNgrEntries(int n) {
        ngrEntries = n;
    }
    int mAXgrEntry = -1;

    /**
     *
     * @param n
     */
    public void setMAXgrEntry(int n) {
        mAXgrEntry = n;
    }
    long azEDRHead;

    /**
     *
     * @param l
     */
    public void setAzEDRHead(long l) {
        azEDRHead = l;
    }
    int nzEntries;

    /**
     *
     * @param n
     */
    public void setNzEntries(int n) {
        nzEntries = n;
    }
    int mAXzEntry;

    /**
     *
     * @param n
     */
    public void setMAXzEntry(int n) {
        mAXzEntry = n;
    }

    /**
     *
     */
    protected String name;

    /**
     *
     * @param s
     */
    public void setName(String s) {
        name = s;
    }

    /**
     *
     * @param s
     * @return
     */
    public byte[] getNameBytes(String s) {
        byte[] name = new byte[256];
        byte[] bs = s.getBytes();
        int i = 0;
        for (; i < bs.length; i++) name[i] = bs[i];
        for (; i < name.length; i++) name[i] = 0;
        return name;
    }

    /**
     *
     * @return
     */
    public int getSize() {return record.limit();}

    /**
     *
     * @return
     */
    public ByteBuffer get() {
        record.position(0);
        record.putLong((long)(record.capacity()));
        record.putInt(4);
        record.putLong(aDRNext);
        record.putLong(agrEDRHead);
        record.putInt(scope);
        record.putInt(num);
        record.putInt(ngrEntries);
        record.putInt(mAXgrEntry);
        record.putInt(0);
        record.putLong(azEDRHead);
        record.putInt(nzEntries);
        record.putInt(mAXzEntry);
        record.putInt(-1);
        record.put(getNameBytes(name));
        record.position(0);
        return record;
    }
}
