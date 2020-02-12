package gov.nasa.gsfc.spdf.cdfj;
import java.nio.*;
import java.lang.reflect.*;
public final class StringVarContainer extends ByteVarContainer implements 
    VDataContainer.CString {
    public StringVarContainer(CDFImpl thisCDF, Variable var, int[] pt) throws
        IllegalAccessException, InvocationTargetException, Throwable {
        super(thisCDF, var, pt);
    }
    public static boolean isCompatible(int type, boolean preserve) {
        if  (isCompatible(type, preserve, Byte.TYPE)) {
            boolean stringType = DataTypes.isStringType(type);
            return stringType;
        }
        return false;
    }

    @Override
    public Object _asArray() throws Throwable {
        int rank = var.getEffectiveRank();
        if (rank > 1) {
            throw new Throwable("Rank > 1 not supported for strings.");
        }
        ByteBuffer buf = getBuffer();
        if (buf == null) return null;
        int words = buf.remaining();
        int records = -1;
        int len = var.getNumberOfElements();
        byte[] ba = new byte[len];
        switch (rank) {
        case 0:
            records = words/len;
            String[] sa = new String[records];
            for (int r = 0; r < records; r++) {
                buf.get(ba);
                sa[r] = new String(ba);
            }
            return sa;
        case 1:
            int n0 =
                    (((Integer)var.getElementCount().elementAt(0)));
            records = words/(n0*len);
            String[][] sa1 = new String[records][n0];
            for (int r = 0; r < records; r++) {
                for (int e = 0; e < n0; e++) {
                    buf.get(ba);
                    sa1[r][e] = new String(ba);
                }
            }
            return sa1;
        default:
            throw new Throwable("Internal error");
        }
    }

    @Override
    public byte[] as1DArray() {return (byte[])super.as1DArray();}
    @Override
    public byte[] asOneDArray() {return (byte[])super.asOneDArray();}
    @Override
    public byte[] asOneDArray(boolean cmtarget) {
        return (byte[])super.asOneDArray(cmtarget);
    }

    @Override
    public AArray asArray() throws Throwable {
        return new StringArray(_asArray());
    }
}
