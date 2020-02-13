package gov.nasa.gsfc.spdf.cdfj;
import java.nio.*;
import java.util.Arrays;
import java.lang.reflect.Array;

/**
 *
 * @author nand
 */
public abstract class AArray {
    ArrayAttribute aa;
    Object o;
    int dim;
    boolean rowMajority = true;

    /**
     *
     * @param o
     * @throws Throwable
     */
    public AArray(Object o) throws Throwable {
        this(o, true);
    }

    /**
     *
     * @param o
     * @param bln
     * @throws Throwable
     */
    public AArray(Object o, boolean rowMajority) throws Throwable {
        Class<?> cl = o.getClass();
        if (!cl.isArray()) throw new Throwable("AArray: Object " + o +
            " is not an array");
        this.o = o;
        aa = new ArrayAttribute(o);
        dim = aa.getDimensions().length;
        this.rowMajority = rowMajority;
    }

    /**
     *
     * @return
     */
    public abstract Object array();

    /**
     *
     * @return
     */
    public int[] getDimensions() {
        return aa.getDimensions();
    }
    ByteBuffer allocate(int elementSize) {
        int size = elementSize;
        int[] _dim = aa.getDimensions();
        for (int i = 0; i < _dim.length; i++) {
            size *= _dim[i];
        }
        ByteBuffer buf = ByteBuffer.allocateDirect(size);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        return buf;
    }

    /**
     *
     * @param size
     * @return
     * @throws Throwable
     */
    public ByteBuffer buffer(int size) throws Throwable {
        if (aa.getType() == String.class) {
            return buffer(String.class, size);
        }
        return buffer();
    }

    /**
     *
     * @return
     * @throws Throwable
     */
    public ByteBuffer buffer() throws Throwable {
        if (aa.getType() == String.class) {
            throw new Throwable("Invalid call for String type");
        }
        return buffer(aa.getType(), 0);
    }

    /**
     *
     * @param cl
     * @return
     * @throws Throwable
     */
    public ByteBuffer buffer(Class<?> cl) throws Throwable {
        return buffer(cl, 0);
    }

    /**
     *
     * @param cl
     * @param size
     * @return
     * @throws Throwable
     */
    public abstract ByteBuffer buffer(Class<?> cl, int size) throws Throwable;

    /**
     *
     * @param dimensions
     * @return
     */
    public boolean validateDimensions(int[] dimensions) {
        return Arrays.equals(dimensions, aa.getDimensions());
    }

    /**
     *
     * @param o
     * @return
     * @throws Throwable
     */
    public static Object getPoint(Object o) throws Throwable {
        ArrayAttribute aa = new ArrayAttribute(o);
        int [] dim = aa.getDimensions();
        Object a = null;
        if (dim.length == 1) {
            a = Array.newInstance(aa.getType(), 1, dim[0]);
        }
        if (dim.length == 2) {
            a = Array.newInstance(aa.getType(), 1, dim[0], dim[1]);
        }
        if (dim.length == 3) {
            a = Array.newInstance(aa.getType(), 1, dim[0], dim[1],
                 dim[2]);
        }
        if (dim.length == 4) {
            a = Array.newInstance(aa.getType(), 1, dim[0], dim[1],
                dim[2], dim[3]);
        }
        if (a == null) return null;
        Array.set(a, 0, o); 
        return a;
    }
}
