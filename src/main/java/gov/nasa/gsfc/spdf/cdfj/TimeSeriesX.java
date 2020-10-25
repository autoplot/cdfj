package gov.nasa.gsfc.spdf.cdfj;

/**
 *
 * @author nand
 */
public interface TimeSeriesX extends TimeSeries {

    /**
     *
     * @return
     */
    public boolean isOneD();

    /**
     *
     * @return
     */
    public boolean isColumnMajor();
}
