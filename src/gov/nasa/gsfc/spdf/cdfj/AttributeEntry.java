package gov.nasa.gsfc.spdf.cdfj;
/**
 * Specifes an attribute entry.
 */
public interface AttributeEntry {

    /**
     *
     * @return
     */
    public int getType();

    /**
     *
     * @return
     */
    public Object getValue();

    /**
     *
     * @return
     */
    public boolean isStringType();

    /**
     *
     * @return
     */
    public boolean isLongType();

    /**
     *
     * @return
     */
    public String getAttributeName();

    /**
     *
     * @return
     */
    public int getVariableNumber(); 

    /**
     *
     * @return
     */
    public int getNumberOfElements();

    /**
     *
     * @param ae
     * @return
     */
    public boolean isSameAs(AttributeEntry ae);
}
