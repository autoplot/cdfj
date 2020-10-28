package gov.nasa.gsfc.spdf.cdfj;
/**
 * Base class for exceptions thrown by methods in this package.
 */
public class CDFException extends Exception {

    /**
     *
     * @param string
     */
    public CDFException(String message) {
        super(message);
    }

    /**
     * Exceptions thrown by methods of CDFReader and its superclasses.
     */
    public static class ReaderError extends CDFException {

        /**
         *
         * @param string
         */
        public ReaderError(String message) {
            super(message);
        }
    }
    /**
     * Exceptions thrown by methods of CDFWriter and its superclass.
     */
    public static class WriterError extends CDFException {

        /**
         *
         * @param string
         */
        public WriterError(String message) {
            super(message);
        }
    }

    /**
     * Exceptions thrown by methods of CDFReader to indicate absence of
     * data for a variable.
     */
    public static class NoRecords extends CDFException {

        /**
         *
         * @param varName
         */
        public NoRecords(String varName) {
            super("Variable " + varName + " has no records.");
        }
    }
}
