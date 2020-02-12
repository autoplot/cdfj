package gov.nasa.gsfc.spdf.cdfj;

/**
 *
 * @author nand
 */
public class GlobalAttributeEntry extends AEDR {
    static int GLOBAL_ATTRIBUTE_RECORD_TYPE = 5;

    /**
     *
     * @param adr
     * @param i
     * @param o
     * @throws Throwable
     */
    public GlobalAttributeEntry(ADR adr, int type, Object value) throws
        Throwable {
        super(adr, type, value);
        setAttributeType(GLOBAL_ATTRIBUTE_RECORD_TYPE);
    }

    /**
     *
     * @param adr
     * @param o
     * @throws Throwable
     */
    public GlobalAttributeEntry(ADR adr, Object value) throws
        Throwable {
        this(adr, -1, value);
    }
}
