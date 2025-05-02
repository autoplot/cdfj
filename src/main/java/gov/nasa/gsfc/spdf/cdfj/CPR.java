package gov.nasa.gsfc.spdf.cdfj;
import java.nio.*;

/**
 *
 * @author nand
 */
public class CPR {
    ByteBuffer record = ByteBuffer.allocate(8/*RecordSize*/ +
        4/*RecordType*/ + 4/*cType*/ + 4/*rfuA*/ + 4/*pCount*/ +
        4/*cParms*/);

    /**
     *
     */
    protected long position;
    
    /**
     *
     * @return
     */
    public ByteBuffer get() {
        record.position(0);
        record.putLong((long)(record.capacity()));
        record.putInt(11); /* CPR_ */
        record.putInt(5); /* GZIP */
        record.putInt(0);
        record.putInt(1);
        record.putInt(6); /* GZIP.6 */
        record.position(0);
        return record;
    }

    /**
     *
     * @return
     */
    public int getSize() {return record.capacity();}

}
