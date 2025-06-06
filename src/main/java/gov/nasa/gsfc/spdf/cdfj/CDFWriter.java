package gov.nasa.gsfc.spdf.cdfj;
import java.nio.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * Extends GenericWriter with methods to include user selected data from CDFs
 */
public class CDFWriter extends GenericWriter {

    Hashtable variableMap = new Hashtable();
    Hashtable gamap = new Hashtable();
    SelectedVariableCollection vcol = new Selector();
    static final Logger anonymousLogger = Logger.getAnonymousLogger();
    static Logger logger = Logger.getLogger("cdfj.cdfwriter");
    static List<String> doNotCheckListGlobal = new ArrayList<String>();
    static {
        doNotCheckListGlobal.add("Logical_file_id");
        doNotCheckListGlobal.add("Generation_date");
        doNotCheckListGlobal.add("Software_version");
    }

    /**
     * Constructs a {@link CDFWriter CDFWriter} of given row majority.
     * @param bln
     */
    public CDFWriter(boolean targetMajority) {
        super(targetMajority);
    }

    /**
     * Constructs a {@link CDFWriter CDFWriter} populated 
     * with data from the given {@link GenericReader GenericReader}.
     * @param reader
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(GenericReader cdf) throws CDFException.WriterError,
        CDFException.ReaderError {
        super(cdf.rowMajority());
        try {
            _addCDF(cdf);
        } catch (Throwable th) {
            throw new CDFException.WriterError(th.getMessage());
        }
    }

    /**
     * Constructs a column major {@link CDFWriter CDFWriter} populated
     * with data from the given CDF file.
     * @param string
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(String fname) throws CDFException.WriterError,
        CDFException.ReaderError {
        super(false);
        GenericReader cdf = getFileReader(fname);
        try {
            _addCDF(cdf);
        } catch (Throwable th) {
            throw new CDFException.WriterError(th.getMessage());
        }
    }

    /**
     * Constructs a column major {@link CDFWriter CDFWriter} populated
     * with data from the given files.
     * @param strings
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(String[] files) throws
        CDFException.WriterError, CDFException.ReaderError {
        this(files[0]);
        for (int i = 1; i < files.length; i++) addCDF(files[i]);
    }

    /**
     * Constructs a column major {@link CDFWriter CDFWriter} populated
     * with data from the given URL.
     * @param url
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(URL url) throws
        CDFException.WriterError, CDFException.ReaderError {
        super(false);
        GenericReader cdf = null;
        try {
            cdf = new GenericReader(url);
        } catch (CDFException.ReaderError th) {
            throw new CDFException.ReaderError(th.getMessage());
        }
        try {
            _addCDF(cdf);
        } catch (Throwable th) {
            throw new CDFException.WriterError(th.getMessage());
        }
    }

    /**
     * Constructs a column major {@link CDFWriter CDFWriter} populated
     * with data from the given array of URLs.
     * @param urls
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(URL[] urls) throws
        CDFException.WriterError, CDFException.ReaderError {
        this(urls[0]);
        for (int i = 1; i < urls.length; i++) addCDF(urls[i]);
    }

    /**
     * Constructs a column major {@link CDFWriter CDFWriter} populated with
     * selected variables, and variables they depend on, from the given file.
     * @param string   
     * @param svc
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(String fname, SelectedVariableCollection col) throws
        CDFException.WriterError, CDFException.ReaderError {
        super(false);
        GenericReader cdf = getFileReader(fname);
        try {
            _addCDF(cdf, variableNames(cdf, col));
        } catch (CDFException.ReaderError | CDFException.WriterError th) {
            throw new CDFException.WriterError(th.getMessage());
        }
    }

    /**
     * Constructs a column major {@link CDFWriter CDFWriter} populated with
     * selected variables, and variables they depend on, from the given files.
     * @param strings   
     * @param svc
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(String[] files, SelectedVariableCollection col) throws
        CDFException.WriterError, CDFException.ReaderError {
        this(files[0], col);
        for (int i = 1; i < files.length; i++) addCDF(files[i]);
    }

    /**
     * Constructs a column major {@link CDFWriter CDFWriter} populated with
     * selected variables, and variables they depend on, from the given URL.
     * @param  url   
     * @param svc
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(URL url, SelectedVariableCollection col) throws
        CDFException.WriterError, CDFException.ReaderError {
        super(false);
        GenericReader cdf = null;
        try {
            cdf = new GenericReader(url);
        } catch (CDFException.ReaderError th) {
            throw new CDFException.ReaderError(th.getMessage());
        }
        try {
            _addCDF(cdf, variableNames(cdf, col));
        } catch (CDFException.ReaderError | CDFException.WriterError th) {
            throw new CDFException.WriterError(th.getMessage());
        }
    }

    /**
     * Constructs a column major {@link CDFWriter CDFWriter} populated with
     * selected variables, and variables they depend on, from the given
     * array of URLs.
     * @param  urls
     * @param svc
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(URL[] urls, SelectedVariableCollection col) throws
        CDFException.WriterError, CDFException.ReaderError {
        this(urls[0], col);
        for (int i = 1; i < urls.length; i++) addCDF(urls[i]);
    }

    String[] variableNames(GenericReader cdf, SelectedVariableCollection col) 
        throws CDFException.ReaderError {
        String[] vnames;
        if (col == null) {
            vnames = cdf.getVariableNames();
            for (String vname : vnames) {
                vcol.add(vname, cdf.isCompressed(vname), sparseRecordOption(cdf, vname));
            }
        } else {
            vnames = getSelected(cdf, col);
        }
        return vnames;
    }

    /**
     * Constructs a {@link CDFWriter CDFWriter} of specified row majority,
     * populated with data from the given file.
     * @param string
     * @param bln
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(String fname, boolean targetMajority) throws
        CDFException.WriterError, CDFException.ReaderError {
        super(targetMajority);
        GenericReader cdf = getFileReader(fname);
        try {
            _addCDF(cdf);
        } catch (Throwable th) {
            throw new CDFException.WriterError(th.getMessage());
        }
    }

    GenericReader getFileReader(String fname) throws CDFException.ReaderError {
        GenericReader cdf = null;
        File file = new File(fname);
        if (!file.exists()) throw new CDFException.ReaderError(
            "file " + fname + " does not exist.");
        try {
            long size = file.length();
            if (size > Integer.MAX_VALUE) {
                cdf = ReaderFactory.getReader(fname);
            } else {

                if (isWindows()) {
                    return ReaderFactory.getReader(fname, true);
                }

                cdf = new GenericReader(fname);
            }
        } catch (CDFException.ReaderError th) {
            throw new CDFException.ReaderError(th.getMessage());
        }
        return cdf;
    }
    /**
     * Constructs a {@link CDFWriter CDFWriter} of specified row majority,
     * populated with data from the given files.
     * @param strings
     * @param bln
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(String[] files, boolean targetMajority) throws
        CDFException.WriterError, CDFException.ReaderError {
        this(files[0], targetMajority);
        for (int i = 1; i < files.length; i++) addCDF(files[i]);
    }

    /**
     * Constructs a {@link CDFWriter CDFWriter} of specified row majority,
     * populated with data from the given URL.
     * @param url
     * @param bln
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(URL url, boolean targetMajority) throws
        CDFException.WriterError, CDFException.ReaderError {
        super(targetMajority);
        GenericReader cdf = null;
        try {
            cdf = new GenericReader(url);
        } catch (CDFException.ReaderError th) {
            throw new CDFException.ReaderError(th.getMessage());
        }
        try {
            _addCDF(cdf);
        } catch (Throwable th) {
            throw new CDFException.WriterError(th.getMessage());
        }
    }

    /**
     * Constructs a {@link CDFWriter CDFWriter} of specified row majority,
     * populated with data from the given array of URLs.
     * @param urls
     * @param bln
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(URL[] urls, boolean targetMajority) throws
        CDFException.WriterError, CDFException.ReaderError {
        this(urls[0], targetMajority);
        for (int i = 1; i < urls.length; i++) addCDF(urls[i]);
    }

    /**
     * Constructs a {@link CDFWriter CDFWriter} of specified row majority,
     * populated with selected variables, and variables they depend on, from
     * the given file.
     * @param string
     * @param svc
     * @param bln
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(String fname, boolean targetMajority,
        SelectedVariableCollection col) throws
        CDFException.WriterError, CDFException.ReaderError {
        super(targetMajority);
        GenericReader cdf = getFileReader(fname);
        try {
            _addCDF(cdf, variableNames(cdf, col));
        } catch (CDFException.ReaderError | CDFException.WriterError th) {
            throw new CDFException.WriterError(th.getMessage());
        }
    }

    /**
     * Constructs a {@link CDFWriter CDFWriter} of specified row majority,
     * populated with selected variables, and variables they depend on, from
     * the given files.
     * @param strings
     * @param svc
     * @param bln
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(String[] files, boolean targetMajority,
        SelectedVariableCollection col) throws
        CDFException.WriterError, CDFException.ReaderError {
        this(files[0], targetMajority, col);
        for (int i = 1; i < files.length; i++) addCDF(files[i]);
    }


    /**
     * Constructs a {@link CDFWriter CDFWriter} of specified row majority,
     * populated with selected variables, and variables they depend on, from
     * the given URL.
     * @param url
     * @param svc
     * @param bln
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(URL url, boolean targetMajority,
        SelectedVariableCollection col) throws
        CDFException.WriterError, CDFException.ReaderError {
        super(targetMajority);
        GenericReader cdf = null;
        try {
            cdf = new GenericReader(url);
        } catch (CDFException.ReaderError th) {
            throw new CDFException.ReaderError(th.getMessage());
        }
        try {
            _addCDF(cdf, variableNames(cdf, col));
        } catch (CDFException.ReaderError | CDFException.WriterError th) {
            throw new CDFException.WriterError(th.getMessage());
        }
    }

    /**
     * Constructs a {@link CDFWriter CDFWriter} of specified row majority,
     * populated with selected variables, and variables they depend on, from
     * the given array of URLs.
     * @param urls
     * @param svc
     * @param bln
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public CDFWriter(URL[] urls, boolean targetMajority,
        SelectedVariableCollection col) throws
        CDFException.WriterError, CDFException.ReaderError {
        this(urls[0], targetMajority, col);
        for (int i = 1; i < urls.length; i++) addCDF(urls[i]);
    }

    /**
     * Adds previously selected variables, and variables they depend on, from
     * the given file.
     * @param fname
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public void addCDF(String fname) throws
        CDFException.WriterError, CDFException.ReaderError {
        GenericReader cdf = getFileReader(fname);
        addCDF(cdf);
    }

    /**
     * Adds previously selected variables, and variables they depend on, from
     * the given URL.
     * @param url
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     */
    public void addCDF(URL url) throws 
        CDFException.WriterError, CDFException.ReaderError {
        GenericReader cdf = null;
        try {
            cdf = new GenericReader(url);
        } catch (CDFException.ReaderError th) {
            throw new CDFException.ReaderError(th.getMessage());
        }
        addCDF(cdf);
    }

    private void _addCDF(GenericReader cdf, String[] vnames) throws
         CDFException.WriterError, CDFException.ReaderError {
         checkLastLeapSecondId(cdf);
         copyGlobalAttributes(cdf);
         addGlobalAttributeEntry("cdfj_source", cdf.getSource());
        for (String vn : vnames) {
            copyVariableAttributes(cdf, vn);
        }
        for (String vname : vnames) {
            String tvar;
            if (!cdf.recordVariance(vname)) {
                continue;
            }
            if (cdf.isTimeType(vname)) {
                continue;
            }
            try {
                tvar = cdf.getTimeVariableName(vname);
            }catch(Throwable th) {
                tvar = null;
            }
            if (tvar != null) {
                DataContainer dc = dataContainers.get(vname);
                if (cdf.getNumberOfValues(vname) == cdf.getNumberOfValues(tvar)) {
                    dc.setTimeContainer(dataContainers.get(tvar));
                }
            }
        }
        for (String vname : vnames) {
            if (cdf.getNumberOfValues(vname) == 0) {
                ((DataContainer) dataContainers.get(vname)).addPhantomEntry();
            } else {
                copyVariableData(cdf, vname);
            }
            vcol.add(vname, cdf.isCompressed(vname), sparseRecordOption(cdf, vname));
        }
    }
    private void _addCDF(GenericReader cdf) throws Throwable {
        String[] vnames = cdf.getVariableNames();
        for (String vname : vnames) {
            vcol.add(vname, cdf.isCompressed(vname));
        }
        _addCDF(cdf, vnames);
    }

    void copyGlobalAttributes(GenericReader cdf) throws 
        CDFException.ReaderError, CDFException.WriterError {
//      try {
            String[] gan = cdf.globalAttributeNames();
        for (String gan1 : gan) {
            Vector entries = null;
            try {
                entries = cdf.getAttributeEntries(gan1);
            }catch (CDFException.ReaderError th) {
                throw new CDFException.ReaderError(th.getMessage());
            }
            gamap.put(gan1, entries);
            for (int e = 0; e < entries.size(); e++) {
                AttributeEntry entry =
                        (AttributeEntry)entries.get(e);
                addGlobalAttributeEntry(gan1, SupportedTypes.cdfType(entry.getType()), entry.getValue());
            }
        }
//      } catch (Throwable t) {
//          t.printStackTrace();
//          throw new Throwable("Faulty original CDF, or program error " +
//          "while processing global variables. Quitting");
//      }
    }

    void copyVariableAttributes(GenericReader cdf, String vn) throws
        CDFException.ReaderError, CDFException.WriterError {
        boolean compressed = vcol.isCompressed(vn);
        SparseRecordOption sro = vcol.getSparseRecordOption(vn);
        CDFDataType ctype = SupportedTypes.cdfType(cdf.getType(vn));
        Hashtable vmap = new Hashtable();
        vmap.put("ctype", ctype);
        vmap.put("compressed", compressed);
        vmap.put("dimensions", cdf.getDimensions(vn));
        vmap.put("varys", cdf.getVarys(vn));
        vmap.put("variance", cdf.recordVariance(vn));
        vmap.put("padValue", cdf.getPadValue(vn, true));
        vmap.put("numberOfElements", cdf.getNumberOfElements(vn));
        // GenericReader returns EPOCH16 as a 1 dim variable
        int[] dims = cdf.getDimensions(vn);
        boolean[] varys = cdf.getVarys(vn);
        if (ctype == CDFDataType.EPOCH16) {
            dims = new int[0];
            varys = new boolean[0];
        }
        try {
        defineVariable(vn, ctype, /*cdf.getDimensions(vn)*/dims,
            /*cdf.getVarys(vn)*/varys, cdf.recordVariance(vn), compressed,
            cdf.getPadValue(vn,true), cdf.getNumberOfElements(vn), sro);
        } catch (CDFException.ReaderError | CDFException.WriterError ex) {
            ex.printStackTrace();
            throw new CDFException.WriterError("Failed to define " + vn);
        }
        Hashtable amap = new Hashtable();
        String[] anames = cdf.variableAttributeNames(vn);
        for (String aname : anames) {
            Vector entries = null;
            try {
                entries = cdf.getAttributeEntries(vn, aname);
            }catch (CDFException.ReaderError th) {
                throw new CDFException.ReaderError(th.getMessage());
            }
            amap.put(aname, entries);
            AttributeEntry entry = (AttributeEntry)entries.get(0);
            ctype = SupportedTypes.cdfType(entry.getType());
            setVariableAttributeEntry(vn, aname, ctype, entry.getValue());
            for (int e = 1; e < entries.size(); e++) {
                entry = (AttributeEntry)entries.get(e);
                ctype = SupportedTypes.cdfType(cdf.getType(vn));
                addVariableAttributeEntry(vn, aname, ctype, entry.getValue());
            }
        }
        vmap.put("amap", amap);
        variableMap.put(vn, vmap);
    }

    void copyVariableData(GenericReader cdf, String vn) throws 
        CDFException.ReaderError, CDFException.WriterError {
        if ((cdf.getByteOrder() == ByteOrder.LITTLE_ENDIAN) &&
            (cdf.rowMajority() == rowMajority)) {
            VariableDataBuffer[] dbufs = null;
            try {
                Variable var = cdf.thisCDF.getVariable(vn);
                dbufs = var.getDataBuffers(true);
            } catch (Throwable th) {
                throw new CDFException.ReaderError(th.getMessage());
            }
            for (VariableDataBuffer dbuf : dbufs) {
                ByteBuffer b = dbuf.getBuffer();
                addBuffer(vn, dbuf);
            }
        } else {
            VDataContainer _container = null;
            try {
                 _container = getContainer(cdf, vn);
            } catch (Throwable th) {
                throw new CDFException.ReaderError(th.getMessage());
            }
            _container.run();
            int[] rr = new int[]{0, cdf.getNumberOfValues(vn) - 1, 1};
            DataContainer container = dataContainers.get(vn);
            if (container != null) {
                int _last = container.getLastRecord();
                System.out.println("last: " + _last);
                if (_last >= 0) {
                    _last++;
                    rr[0] += _last;
                    rr[1] += _last;
                }
            }
                System.out.println("rr: " + rr[0] + "," + rr[1]);
            if (cdf.rowMajority() == rowMajority) {
                addData(vn, _container.getBuffer(), rr);
            } else {
                addOneD(vn, _container.asOneDArray(!rowMajority), rr, true);
            }
        }
    }

    /**
     * Adds previously selected variables, and variables they depend on, from
     * the given {@link GenericReader GenericReader}..
     * @param cdf
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.WriterError
     */
    public void addCDF(GenericReader cdf) throws
        CDFException.ReaderError, CDFException.WriterError {
        checkLastLeapSecondId(cdf);
        checkGlobalAttributes(cdf);
        List timeVariableList = getTimeVariableList(cdf);
        String[] vnames = vcol.getNames();
        for (String vn : vnames) {
            Hashtable vmap = (Hashtable)variableMap.get(vn);
            if (((Boolean)vmap.get("variance"))) {
                if (timeVariableList.contains(vn)) {
                    DataContainer dc = dataContainers.get(vn);
                    if (cdf.getNumberOfValues(vn) > 0) {
                        Object firstTime;
                        try {
                            if (cdf.isCompatible(vn, Double.TYPE)) {
                                firstTime = cdf.getOneDArray(vn, "double",
                                        new int[]{0,0}, true, !rowMajority);
                            } else {
                                firstTime = cdf.getOneDArray(vn, "long",
                                        new int[]{0,0}, true, !rowMajority);
                            }
                        } catch (CDFException.ReaderError th) {
                            throw new CDFException.WriterError(th.getMessage());
                        }
                        if (!dc.timeOrderOK(firstTime)) {
                            throw new CDFException.WriterError("Time Backup -"
                                    + "Time of first record for variable " + vn +
                                    " of CDF " + cdf.thisCDF.getSource().getName() +
                                    " starts before the end of previous CDF");
                        }
                    }
                }
                if (cdf.getNumberOfValues(vn) > 0) {
                    copyVariableData(cdf, vn);
                }
            }
        }
    }

    List getTimeVariableList(GenericReader cdf) {
        ArrayList<String> list = new ArrayList<>();
        String[] vnames = vcol.getNames();
        for (String vname : vnames) {
            String tvar;
            try {
                tvar = cdf.getTimeVariableName(vname);
            }catch (Throwable th) {
                tvar = null;
            }
            if (tvar != null) list.add(tvar);
        }
        return list;
    }

    void checkGlobalAttributes(GenericReader cdf) throws
        CDFException.ReaderError, CDFException.WriterError {
        String[] gan = cdf.globalAttributeNames();
        for (String gan1 : gan) {
            Vector _entries = (Vector) gamap.get(gan1);
            Vector entries = null;
            try {
                entries = cdf.getAttributeEntries(gan1);
            }catch (CDFException.ReaderError th) {
                throw new CDFException.ReaderError(th.getMessage());
            }
            for (int e = 0; e < entries.size(); e++) {
                AttributeEntry entry = (AttributeEntry)entries.get(e);
                boolean found = false;
                for (int b = 0; b < _entries.size(); b++) {
                    AttributeEntry _entry = (AttributeEntry)_entries.get(b);
                    found = _entry.isSameAs(entry);
                    if (found) break;
                }
                if (!found) {
                    if (!doNotCheckListGlobal.contains(gan1)) {
                        logger.log(Level.FINE, "Global attribute entry for attribute {0} not in base, or differs from the value in base.", gan1);
                    }
                }
            }
        }
    }

    void updateVariableAttributes(GenericReader cdf, String vn) throws
        Throwable {
        String[] anames = cdf.variableAttributeNames(vn);
        Hashtable vmap = (Hashtable)variableMap.get(vn);
        validateVariableProperties(cdf, vn);
        Hashtable amap = (Hashtable)vmap.get("amap");
        for (String aname : anames) {
            Vector entries = cdf.getAttributeEntries(vn, aname);
            Vector _entries = (Vector) amap.get(aname);
            for (int e = 0; e < entries.size(); e++) {
                AttributeEntry entry = (AttributeEntry)entries.get(e);
                boolean found = false;
                for (int b = 0; b < _entries.size(); b++) {
                    AttributeEntry _entry = (AttributeEntry)_entries.get(b);
                    found |= _entry.isSameAs(entry);
                    if (found) break;
                }
                if (!found) {
                    logger.log(Level.FINE, "Attribute entry for attribute {0} for variable {1} not in base.", new Object[]{aname, vn});
                }
            }
        }
    }

    void validateVariableProperties(GenericReader cdf, String vn) throws
        Throwable {
        Hashtable vmap = (Hashtable)variableMap.get(vn);
        boolean compressed = cdf.isCompressed(vn);
        boolean failed = ((CDFDataType)vmap.get("ctype") != 
        SupportedTypes.cdfType(cdf.getType(vn)));
        //if (!failed) failed = ((boolean)vmap.get("compressed") != compressed);
        if (!failed) failed = !Arrays.equals((int[])vmap.get("dimensions"),
            cdf.getDimensions(vn));
        if (!failed) failed = !Arrays.equals((boolean[])vmap.get("varys"),
            cdf.getVarys(vn));
        if (!failed) failed = (((Boolean)vmap.get("variance")) != 
            cdf.recordVariance(vn));
        if (!failed) failed = (((Integer)vmap.get("numberOfElements")) != 
            cdf.getNumberOfElements(vn));
        //vmap.put("padValue", cdf.getPadValue(vn, true));
        if (failed) throw new Throwable("Properties of variable " + vn +
            "do not match.");
    }

    boolean isTimeType(int type) {
        boolean isTimeType = (CDFTimeType.EPOCH.getValue() == type);
        isTimeType |= (CDFTimeType.EPOCH16.getValue() == type);
        isTimeType |= (CDFTimeType.TT2000.getValue() == type);
        return isTimeType;
    }

    String[] getSelected(GenericReader cdf, SelectedVariableCollection col) 
        throws CDFException.ReaderError {
        String[] sorted = null;
        Vector selected = new Vector();
        int n = 0;
        String[] names = col.getNames();
        while (n < names.length) {
            String name = names[n];
            logger.log(Level.FINE, "requested: {0}", name);
            if (!hasVariable(cdf, name)) {
                logger.log(Level.FINE, "{0} not found in original. ignoring.", name);
                n++;
                continue;
            }
            if (!selected.contains(name)) {
                selected.add(name);
                vcol.add(name, col.isCompressed(name),
                         col.getSparseRecordOption(name));
            }
            Vector depends = getDependent(cdf, name);
            for (int i = 0; i < depends.size(); i++) {
                String dvar = (String)depends.get(i);
                if (selected.contains(dvar)) continue;
                selected.add(dvar);
                boolean compressed = cdf.isCompressed(dvar);
                SparseRecordOption sro = sparseRecordOption(cdf, name);
                if (col.hasVariable(dvar)) {
                    compressed = col.isCompressed(dvar);
                    sro = col.getSparseRecordOption(name);
                }
                vcol.add(dvar, compressed, sro);
                logger.log(Level.FINE, "added: {0}", depends.get(i));
            }
            n++;
        }
        if (selected.size() == 0) {
            logger.fine("No valid variables selected.");
            return new String[0];
        }
        sorted = new String[selected.size()];
        for (int i = 0; i < selected.size(); i++) {
            sorted[i] = (String)selected.get(i);
        }
        return sorted;
    }
    static Vector getDependent(GenericReader cdf, String vname) throws
        CDFException.ReaderError {
        String[] anames = cdf.variableAttributeNames(vname);
        Vector dependent = new Vector();
        if (anames == null) return dependent;
        for (String aname : anames) {
            if (!aname.startsWith("DEPEND_")) {
                continue;
            }
            dependent.add(((Vector) cdf.getAttribute(vname, aname)).get(0));
        }
        return dependent;
    }

    /**
     * return the type of the variable.
     * @param name 
     * @return  the CDFDataType of the variable.
     */
    public CDFDataType getVariableType(String name) {
        Hashtable o= (Hashtable)variableMap.get(name);
        if ( o!=null ) {
            CDFDataType t= (CDFDataType)o.get("ctype");
            if ( t==null ) {
                throw new IllegalArgumentException("internal error, expected to see type for: "+name);
            } else {
                return t;
            }
        } else {
            throw new IllegalArgumentException("variable with the name does not exist: "+name );
        }
    }
    
    /**
     * Returns a new instance of the {@link SelectedVariableCollection
     * SelectedVariableCollection}.
     * @return 
     */
    public static SelectedVariableCollection selectorInstance() {
        return new Selector();
    }

    static class Selector implements SelectedVariableCollection {
        HashMap<String, Boolean> map = new HashMap<>();
        HashMap<String, SparseRecordOption> smap = 
           new HashMap<>();
        @Override
        public void add(String vname, boolean compression) {
            map.put(vname, compression);
        }
        @Override
        public void add(String vname, boolean compression,
            SparseRecordOption opt) {
            add(vname, compression);
            smap.put(vname, opt);
        }
        @Override
        public boolean isCompressed(String name) {
            return ((Boolean)map.get(name));
        }
        @Override
        public SparseRecordOption getSparseRecordOption(String name) {
            if (smap.get(name) == null) return SparseRecordOption.PADDED;
            return smap.get(name);
        }
        @Override
        public String[] getNames() {
            String[] names = new String[map.size()];
            Set set = map.keySet();
            set.toArray(names);
            return names;
        }
        @Override
        public boolean hasVariable(String name) {
            return (map.get(name) != null);
        }
    }
    boolean hasVariable(GenericReader cdf, String vname) {
        String[] vnames = cdf.getVariableNames();
        for (String vname1 : vnames) {
            if (vname.equals(vname1)) {
                return true;
            }
        }
        return false;
    }
    String getTimeVariableName(GenericReader cdf, String vname) {
        String tvar;
        try {
            tvar = cdf.getTimeVariableName(vname);
        } catch(Throwable th) {
            logger.fine(th.toString());
            tvar = null;
        }
        return tvar;
    }
    VDataContainer getContainer(GenericReader rdr, String varName)
        throws Throwable {
        Object container = null;
        CDFDataType ctype = SupportedTypes.cdfType(rdr.getType(varName));
        Variable var = rdr.thisCDF.getVariable(varName);
        ByteOrder order = ByteOrder.LITTLE_ENDIAN;
        if ((ctype == CDFDataType.INT1) || (ctype == CDFDataType.UINT1)) {
            container = var.getByteContainer(null);
        }
        if (ctype == CDFDataType.INT2) {
            container = var.getShortContainer(null, true, order);
        }
        if (ctype == CDFDataType.INT4) {
            container = var.getIntContainer(null, true, order);
        }
        if (ctype == CDFDataType.UINT2) {
            container = var.getShortContainer(null, false, order);
        }
        if (ctype == CDFDataType.UINT4) {
            container = var.getIntContainer(null, false, order);
        }
        if (ctype == CDFDataType.FLOAT) {
            container = var.getFloatContainer(null, true, order);
        }
        if ((ctype == CDFDataType.DOUBLE) || (ctype == CDFDataType.EPOCH) ||
            (ctype == CDFDataType.EPOCH16)) {
            container = var.getDoubleContainer(null, true, order);
        }
        if ((ctype == CDFDataType.TT2000) || (ctype == CDFDataType.INT8)) {
            container = var.getLongContainer(null, order);
        }
        if (ctype == CDFDataType.CHAR) {
            container = var.getStringContainer(null);
        }
        return (VDataContainer) container;
    }

    /**
     * sets a Logger for this class
     * @param _logger
     */
    public void setLogger(Logger _logger) {
        if (_logger == null) return;
        logger = _logger;
    }

    /**
     * Sets Level for the default anonymous  Logger for this class.If a logger has been set via a call to {@link #setLogger(Logger)},
 this method has no effect
     * @param newLevel
     */
    public static void setLoggerLevel(Level newLevel) {
        if (logger == anonymousLogger) logger.setLevel(newLevel);
    }

    /**
     * Adds an attribute to the list of 'not to be monitored' global attributes.
     * @param aname
     */
    public static void addToDoNotCheckList(String aname) {
        if (doNotCheckListGlobal.contains(aname)) return;
        doNotCheckListGlobal.add(aname);
    }

    /**
     * Removes an attribute from the list 'not to be monitored' global
     * attributes.
     * @param aname
     */
    public static void removeFromDoNotCheckList(String aname) {
        if (!doNotCheckListGlobal.contains(aname)) return;
        doNotCheckListGlobal.remove(aname);
    }

    /**
     * Returns names of 'not to be monitored' global attributes.
     * @return 
     */
    public String[] attributesInDoNotCheckList() {
        String[] sa = new String[doNotCheckListGlobal.size()];
        doNotCheckListGlobal.toArray(sa);
        return sa;
    }

    SparseRecordOption sparseRecordOption(GenericReader cdf, String vname) 
        throws CDFException.ReaderError {
        if (cdf.missingRecordValueIsPad(vname)) {
            return SparseRecordOption.PADDED;
        }
        if (cdf.missingRecordValueIsPrevious(vname)) {
            return SparseRecordOption.PREVIOUS;
        }
        return SparseRecordOption.NONE;
    }

    void checkLastLeapSecondId(GenericReader cdf) throws
        CDFException.WriterError {
        if (lastLeapSecondId == -1) {
            lastLeapSecondId = cdf.getLastLeapSecondId();
        } else {
            if (lastLeapSecondId != cdf.getLastLeapSecondId()) {
                throw new CDFException.WriterError("LastLeapSecondId " +
                cdf.getLastLeapSecondId() +
                " does not match previously found " + lastLeapSecondId);
            }
        }
    }
}
