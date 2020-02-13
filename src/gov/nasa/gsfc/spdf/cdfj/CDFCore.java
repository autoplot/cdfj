package gov.nasa.gsfc.spdf.cdfj;
/**
 * Interface that defines methods for getting attributes, variable
 * characteristics, and data from a generic CDF 
 */
public interface CDFCore extends CDFMeta {
    /**
     * Returns a byte[] containing value of the given variable for the
     * specified range of points.
     * <p>
     * If pt is null, all available records are returned.
     * If pt.length is 1, only the pt[0] record is returned.
     * @param varName
     * @param pt
     * @return 
     * @throws java.lang.Throwable 
     */
    public byte[] getByteArray(String varName, int[] pt) throws Throwable;

    /**
     * Returns a double[] containing value of the given variable for the
     * specified range of records.
     * <p>
     * same as getDoubleArray(varName, pt, true).
     * If pt is null, all available records are returned.
     * If pt.length is 1, only the pt[0] record is returned.
     * @param varName
     * @param pt
     * @return 
     * @throws java.lang.Throwable 
     */
    public double[] getDoubleArray(String varName, int[] pt) throws Throwable;

    /**
     * Returns a double[] containing value of the given variable for the
     * specified range of records, optionally accepting loss of precision.
     * <p>
     * If pt is null, all available records are returned.
     * If pt.length is 1, only the pt[0] record is returned.
     * @param varName
     * @param preserve
     * @param pt
     * @return 
     * @throws java.lang.Throwable 
     */
    public double[] getDoubleArray(String varName, int[] pt, boolean preserve)
    throws Throwable;

    /**
     * Returns a float[] containing value of the given variable for the
     * specified range of records.
     * <p>
     * same as getFloatArray(varName, pt, true).
     * If pt is null, all available records are returned.
     * If pt.length is 1, only the pt[0] record is returned.
     * @param varName
     * @param pt
     * @return 
     * @throws java.lang.Throwable 
     */
    public float[] getFloatArray(String varName, int[] pt) throws Throwable;

    /**
     * Returns a float[] containing value of the given variable for the
     * specified range of records, optionally accepting loss of precision.
     * <p>
     * If pt is null, all available records are returned.
     * If pt.length is 1, only the pt[0] record is returned.
     * @param varName
     * @param preserve
     * @param pt
     * @return 
     * @throws java.lang.Throwable 
     */
    public float[] getFloatArray(String varName, int[] pt, boolean preserve)
    throws Throwable;

    /**
     * returns a int[] containing value of the given variable for the
     * specified range of records.
     * <p>
     * same as getIntArray(varName, pt, true).
     * If pt is null, all available records are returned.
     * If pt.length is 1, only the pt[0] record is returned.
     * @param varName
     * @param pt
     * @return 
     * @throws java.lang.Throwable 
     */
    public int[] getIntArray(String varName, int[] pt) throws Throwable;

    /**
     * returns a int[] containing value of the given variable for the
     * specified range of records, optionally accepting loss of precision.
     * <p>
     * If pt is null, all available records are returned.
     * If pt.length is 1, only the pt[0] record is returned.
     * @param varName
     * @param preserve
     * @param pt
     * @return 
     * @throws java.lang.Throwable 
     */
    public int[] getIntArray(String varName, int[] pt, boolean preserve) throws
    Throwable;

    /**
     * returns a long[] containing value of the given variable for the
     * specified range of records.
     * <p>
     * If pt is null, all available records are returned.
     * If pt.length is 1, only the pt[0] record is returned.
     * @param varName
     * @param pt
     * @return 
     * @throws java.lang.Throwable 
     */
    public long[] getLongArray(String varName, int[] pt) throws Throwable;

    /**
     * returns a short[] containing value of the given variable for the
     * specified range of records.
     * <p>
     * same as getShortArray(varName, pt, true).
     * If pt is null, all available records are returned.
     * If pt.length is 1, only the pt[0] record is returned.
     * @param varName
     * @param pt
     * @return 
     * @throws java.lang.Throwable 
     */
    public short[] getShortArray(String varName, int[] pt) throws Throwable;

    /**
     * returns a short[] containing value of the given variable for the
     * specified range of records, optionally accepting loss of precision.
     * <p>
     * If pt is null, all available records are returned.
     * If pt.length is 1, only the pt[0] record is returned.
     * @param varName
     * @param preserve
     * @param pt
     * @return 
     * @throws java.lang.Throwable 
     */
    public short[] getShortArray(String varName, int[] pt, boolean preserve)
    throws Throwable;

    /**
     *
     * @param varName
     * @param first
     * @param last
     * @param columnMajor
     * @return
     * @throws Throwable
     */
    public Object getRangeOneD(String varName, int first, int last,
        boolean columnMajor) throws Throwable;

    /**
     *
     * @param varName
     * @param columnMajor
     * @return
     * @throws Throwable
     */
    public Object getOneD(String varName, boolean columnMajor) throws
        Throwable;

    @Override
    Variable getVariable(String varName);

    /**
     *
     * @return
     */
    public CDFFactory.CDFSource getSource();
}
