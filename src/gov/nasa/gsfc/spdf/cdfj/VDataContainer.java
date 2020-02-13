package gov.nasa.gsfc.spdf.cdfj;
//import gov.nasa.gsfc.spdf.common.*;
import java.nio.*;
/**
 * Data Container for a variable
 */
public interface VDataContainer extends Runnable {
    /**
     * Returns ByteBuffer for this container.
     * @return 
     */
    public ByteBuffer getBuffer();

    /**
     * Returns range of records in this container.
     * @return 
     */
    public int[] getRecordRange();

    /**
     * Returns the one dimensional array representation.
     * @return 
     */
    public Object as1DArray();

    /**
     *
     * @param cmtarget
     * @return
     */
    public Object asOneDArray(boolean cmtarget);

    /**
     *
     * @return
     * @throws Throwable
     */
    public AArray asArray() throws Throwable ;

    /**
     *
     * @param direct
     */
    public void setDirect(boolean direct);

    /**
     * Returns the {@link Variable Variable} for this container.
     * @return 
     */
    public Variable getVariable();

    /**
     * Double Data Container.
     */
    public static interface CDouble extends VDataContainer {
        /**
         * Returns the one dimensional array representation.
         * @return 
         */
        @Override
        public double[] as1DArray();

        /**
         *
         * @return
         */
        public double[] asOneDArray();

        /**
         *
         * @param cmtarget
         * @return
         */
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
         * @return 
         */
        @Override
        public float[] as1DArray();

        /**
         *
         * @return
         */
        public float[] asOneDArray();

        /**
         *
         * @param cmtarget
         * @return
         */
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
         * @return 
         */
        @Override
        public int[] as1DArray();

        /**
         *
         * @return
         */
        public int[] asOneDArray();

        /**
         *
         * @param cmtarget
         * @return
         */
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
         * @return 
         */
        @Override
        public short[] as1DArray();

        /**
         *
         * @return
         */
        public short[] asOneDArray();

        /**
         *
         * @param cmtarget
         * @return
         */
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
         * @return 
         */
        @Override
        public long[] as1DArray();

        /**
         *
         * @return
         */
        public long[] asOneDArray();

        /**
         *
         * @param cmtarget
         * @return
         */
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
         * @return 
         */
        @Override
        public byte[] as1DArray();

        /**
         *
         * @return
         */
        public byte[] asOneDArray();

        /**
         *
         * @param cmtarget
         * @return
         */
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
         * @return 
         */
        @Override
        public byte[] as1DArray();

        /**
         *
         * @return
         */
        public byte[] asOneDArray();

        /**
         *
         * @param cmtarget
         * @return
         */
        @Override
        public byte[] asOneDArray(boolean cmtarget);

        /**
         * Returns the multi dimensional array representation.
         */
        //public StringArray asArray() throws Throwable ;
    }

    /**
     *
     * @param buffer
     * @return
     */
    public boolean setUserBuffer(ByteBuffer buffer);

    /**
     *
     * @return
     */
    public int getCapacity();
}
