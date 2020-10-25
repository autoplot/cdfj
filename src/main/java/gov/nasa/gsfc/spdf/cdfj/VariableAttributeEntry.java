package gov.nasa.gsfc.spdf.cdfj;

/**
 *
 * @author nand
 */
public class VariableAttributeEntry extends AEDR {
    static int VARIABLE_ATTRIBUTE_RECORD_TYPE = 9;

    /**
     *
     * @param adr
     * @param i
     * @param o
     * @throws Throwable
     */
    public VariableAttributeEntry(ADR adr, int type, Object value) throws
        Throwable {
        super(adr, type, value);
        setAttributeType(VARIABLE_ATTRIBUTE_RECORD_TYPE);
    }

    /**
     *
     * @param adr
     * @param o
     * @throws Throwable
     */
    public VariableAttributeEntry(ADR adr, Object value) throws
        Throwable {
        this(adr, -1, value);
    }
}
