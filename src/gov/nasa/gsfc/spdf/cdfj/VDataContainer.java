package gov.nasa.gsfc.spdf.cdfj;
//import gov.nasa.gsfc.spdf.common.*;
import java.nio.*;
/**
 * Data Container for a variable
 */
public interface VDataContainer extends Runnable {
    /**
     * Returns ByteBuffer for this container.
     */
    public ByteBuffer getBuffer();

    /**
     * Returns range of records in this container.
     */
    public int[] getRecordRange();

    /**
     * Returns the one dimensional array representation.
     */
    public Object as1DArray();
    public Object asOneDArray(boolean cmtarget);
    public AArray asArray() throws Throwable ;
    public void setDirect(boolean direct);

    /**
     * Returns the {@link Variable Variable} for this container.
     */
    public Variable getVariable();

    /**
     * Double Data Container.
     */
    public static interface CDouble extends VDataContainer {
        /**
         * Returns the one dimensional array representation.
         */
        @Override
        public double[] as1DArray();
        public double[] asOneDArray();
        @Override
        public double[] asOneDArray(boolean cmtarget);

        /**
         * Returns the multi dimensional array representation.
         */
        @Override
        public DoubleArray asArray() throws Throwable ;
    }

    /**
     * Float Data Container.
     */
    public static interface CFloat extends VDataContainer {
        /**
         * Returns the one dimensional array representation.
         */
        @Override
        public float[] as1DArray();
        public float[] asOneDArray();
        @Override
        public float[] asOneDArray(boolean cmtarget);

        /**
         * Returns the multi dimensional array representation.
         */
        @Override
        public FloatArray asArray() throws Throwable ;
    }

    /**
     * Int Data Container.
     */
    public static interface CInt extends VDataContainer {
        /**
         * Returns the one dimensional array representation.
         */
        @Override
        public int[] as1DArray();
        public int[] asOneDArray();
        @Override
        public int[] asOneDArray(boolean cmtarget);

        /**
         * Returns the multi dimensional array representation.
         */
        @Override
        public IntArray asArray() throws Throwable ;
    }

    /**
     * Short Data Container.
     */
    public static interface CShort extends VDataContainer {
        /**
         * Returns the one dimensional array representation.
         */
        @Override
        public short[] as1DArray();
        public short[] asOneDArray();
        @Override
        public short[] asOneDArray(boolean cmtarget);

        /**
         * Returns the multi dimensional array representation.
         */
        @Override
        public ShortArray asArray() throws Throwable ;
    }

    /**
     * Long Data Container.
     */
    public static interface CLong extends VDataContainer {
        /**
         * Returns the one dimensional array representation.
         */
        @Override
        public long[] as1DArray();
        public long[] asOneDArray();
        @Override
        public long[] asOneDArray(boolean cmtarget);

        /**
         * Returns the multi dimensional array representation.
         */
        @Override
        public LongArray asArray() throws Throwable ;
    }

    /**
     * Byte Data Container.
     */
    public static interface CByte extends VDataContainer {
        /**
         * Returns the one dimensional array representation.
         */
        @Override
        public byte[] as1DArray();
        public byte[] asOneDArray();
        @Override
        public byte[] asOneDArray(boolean cmtarget);

        /**
         * Returns the multi dimensional array representation.
         */
        //public ByteArray asArray() throws Throwable ;
    }

    /**
     * String Data Container.
     */
    public static interface CString extends VDataContainer {
        /**
         * Returns the one dimensional array representation.
         */
        @Override
        public byte[] as1DArray();
        public byte[] asOneDArray();
        @Override
        public byte[] asOneDArray(boolean cmtarget);

        /**
         * Returns the multi dimensional array representation.
         */
        //public StringArray asArray() throws Throwable ;
    }
    public boolean setUserBuffer(ByteBuffer buffer);
    public int getCapacity();
}
