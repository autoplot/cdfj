package gov.nasa.gsfc.spdf.cdfj;
import java.nio.ByteBuffer;
/**
 * Time Variable.
 */
public interface TimeVariableX extends TimeVariable {
    /**
     * Returns relative times for the specified time range using the given
     * {@link TimeInstantModel time instant model}.
     * <p>
     * @param    timeRange  relative time range 
     * @param tspec 
     * @return  
     * @throws java.lang.Throwable  
     */
    public double[] getTimes(double[] timeRange, TimeInstantModel tspec) throws
        Throwable;

    /**
     *
     * @param timeRange
     * @return
     * @throws Throwable
     */
    public int[] getRecordRange(double[] timeRange) throws Throwable;

    /**
     *
     * @param startTime
     * @param stopTime
     * @param ts
     * @return
     * @throws Throwable
     */
    public int [] getRecordRange(int[] startTime, int[] stopTime,
            TimeInstantModel ts) throws Throwable;

    /**
     *
     * @return
     */
    public ByteBuffer getRawBuffer();
    @Override
    public TimePrecision getPrecision();
}
