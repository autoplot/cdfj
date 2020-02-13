package gov.nasa.gsfc.spdf.cdfj;

/**
 *
 * @author nand
 */
public class DefaultPadValues {
    static Number[] padValues = new Number[50];
    static {
        padValues[1] = -127;
        padValues[2] = -32767;
        padValues[4] = -2147483647;
        padValues[8] = -9223372036854775807L;
        padValues[11] = 254;
        padValues[12] = 65534;
        padValues[14] = 4294967294l;
        padValues[44] = new Float(-1.0E30);
        padValues[45] = -1.0E30;
        padValues[31] = new Double(0);
        padValues[32] = new Double(0);
        padValues[33] = -9223372036854775807L;
    }

    /**
     *
     * @param type
     * @return
     */
    public static Object value(int type) {
        return padValues[type];
    }
}
