package gov.nasa.gsfc.spdf.cdfj;
import java.util.*;
import java.nio.*;
/**
 * Interface that defines methods for getting  properties of
 * a CDF variable.
 */
public interface VariableMetaData {
    /**
     * Determines whether the value of this variable is the same at
     * all time points.returns true if value may change, false otherwise
     * @return
     */
    public boolean recordVariance();

    /**
     * Determines whether the value of this variable is represented as
     * a compressed byte sequence in the CDF.
     * @return 
     */
    public boolean isCompressed();

    /**
     * Determines whether the value of this variable is presented in
     * a row-major order in the CDF.
     * @return 
     */
    public boolean rowMajority();

    /**
     * Gets the name of this of this variable
     * @return 
     */
    public String getName();

    /**
     * Gets the type of values of the variable.Supported types are defined in the CDF Internal Format Description
     * @return
     */
    public int getType();

    /**
     * Gets the size of an item (defined as number of bytes needed to
     * represent the value of this variable at a point).
     * @return 
     */
    public int getDataItemSize();

    /**
     * Gets the sequence number of the variable inside the CDF. 
     * @return 
     */
    public int getNumber();

    /**
     * Gets the number of elements (of type returned by getType()).
     * @return 
     */
    public int getNumberOfElements();

    /**
     * Gets the number of values (size of time series)
     * @return 
     */
    public int getNumberOfValues();

    /**
     * Gets an object that represents a padded instance.For variable of type 'string', a String is returned;
 For numeric data, a double[] is returned.
     * If the variable type is
 long, a loss of precision may occur. 
     * @return 
     */
    public Object getPadValue();

    /**
     * Gets an object that represents a padded instance for a variable of
     * numeric type.A double[] is returned, unless the variable type is long and
 preservePrecision is set to true;
     * @param preservePrecision
     * @return
     */
    public Object getPadValue(boolean preservePrecision);

    /**
     * Gets the dimensions.
     * @return 
     */
    public int[] getDimensions();

    /**
     * Gets the dimensional variance.This determines the effective
 dimensionality of values of the variable.
     * @return 
     */
    public boolean[] getVarys();

    /**
     * Gets a list of regions that contain data for the variable.Each element of the vector describes a region as an int[3] array.
     * Array elements are: record number of first point
 in the region, record number of last point in the
 region, and offset of the start of region.
     * @return 
     */
    public VariableDataLocator getLocator();

    /**
     * Gets an array of VariableDataBuffer objects that provide location of
     * data for this variable if this variable is not compressed.This method
 throws a Throwable if invoked for a compressed variable.getBuffer method of VariableDataBuffer object returns a read only 
 ByteBuffer that contains data for this variable for a range of
 records. getFirstRecord() and getLastRecord() define the
 range of records.
     * @return
     * @throws java.lang.Throwable
     */
    public VariableDataBuffer[] getDataBuffers() throws Throwable;

    /**
     *
     * @param raw
     * @return
     * @throws Throwable
     */
    public VariableDataBuffer[] getDataBuffers(boolean raw) throws Throwable;

    /**
     * Returns effective rank of this variable.Dimensions for which dimVarys is false do not count.
     * @return
     */
    public int getEffectiveRank();

    /**
     * Returns ByteBuffer containing uncompressed values converted to
     * a stream of numbers of the type specified by 'type' using the
     * specified byte ordering (specified by bo) for the specified range
     * of records.Original  ordering of values (row majority) is preserved.recordRange[0] specifies the first record, and recordRange[1] the last
 record.If 'preserve' is true, a Throwable is thrown if the conversion
 to specified type will result in loss of precision.If 'preserve' is
 false, compatible conversions will be made even if it results in loss
 of precision.
     * @param type
     * @param bo
     * @param recordRange
     * @param preserve
     * @return 
     * @throws java.lang.Throwable 
     */
    public ByteBuffer getBuffer(Class type, int[] recordRange, boolean preserve,
        ByteOrder bo) throws Throwable;

    /**
     * Shows whether one or more records (in the range returned by
     * getRecordRange()) are missing. 
     * @return 
     */
    public boolean isMissingRecords();

    /**
     * Returns record range for this variable
     * @return 
     */
    public int[] getRecordRange();

    /**
     * returns whether conversion of this variable to type specified by
     * cl is supported while preserving precision.equivalent to isCompatible(Class cl, true)
     * @param cl
     * @return
     */
    public boolean isCompatible(Class cl);

    /**
     * returns whether conversion of this variable to type specified by
     * cl is supported under the given precision preserving constraint.
     * @param cl
     * @param preserve
     * @return 
     */
    public boolean isCompatible(Class cl, boolean preserve);

    /**
     * Return whether the missing record should be assigned the last 
     * seen value.If none has been seen, pad value is assigned.
     * @return 
     */
    public boolean missingRecordValueIsPrevious();

    /**
     * Return whether the missing record should be assigned the pad 
     * value.
     * @return 
     */
    public boolean missingRecordValueIsPad();

    /**
     * Return element count for this variable's dimensions.
     * @return 
     */
    public Vector getElementCount();

    /**
     * Returns effective dimensions
     * @return 
     */
    public int[] getEffectiveDimensions();

    /**
     *
     * @return
     */
    public int getBlockingFactor();

    /**
     *
     * @return
     */
    public boolean isTypeR();
}
