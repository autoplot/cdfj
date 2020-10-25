package gov.nasa.gsfc.spdf.cdfj;

/**
 *
 * @author nand
 */
public interface OutputOption {

    /**
     *
     * @param name
     * @param compression
     */
    public void add(String name, boolean compression);

    /**
     *
     * @param name
     * @return
     */
    public boolean isCompressed(String name);

    /**
     *
     * @return
     */
    public String[] getNames();

    /**
     *
     * @param name
     * @return
     */
    public boolean hasVariable(String name);

    /**
     *
     * @param rowMajority
     */
    public void setRowMajority(boolean rowMajority);
}
