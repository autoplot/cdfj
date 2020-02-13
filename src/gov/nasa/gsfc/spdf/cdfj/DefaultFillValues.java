package gov.nasa.gsfc.spdf.cdfj;

/**
 *
 * @author nand
 */
public class DefaultFillValues {
    static Number[] fillValues = new Number[50];
    static {
        fillValues[1] = -128;
        fillValues[2] = -32768;
        fillValues[4] = -2147483648;
        fillValues[8] = -9223372036854775808L;
        fillValues[11] = 255;
        fillValues[12] = 65535;
        fillValues[14] = 4294967295l;
        fillValues[44] = new Float(-1.0E31);
        fillValues[45] = -1.0E31;
        fillValues[31] = -1.0E31;
        fillValues[32] = -1.0E31;
        fillValues[33] = -9223372036854775808L;
    }

    /**
     *
     * @param type
     * @return
     */
    public static Object value(int type) {
        return fillValues[type];
    }
}
