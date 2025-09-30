
package test;

import gov.nasa.gsfc.spdf.cdfj.CDFException;
import gov.nasa.gsfc.spdf.cdfj.CDFReader;
import gov.nasa.gsfc.spdf.cdfj.TimeSeries;

/**
 * Demo where the CDFJ library cannot read files with both rvars and zvars.
 * @see https://cdaweb.gsfc.nasa.gov/pub/software/cdawlib/0MASTERS/ac_or_ssc_00000000_v01.cdf
 * @author faden@cottagesystems.com
 */
public class Demo20250930 {
    public static void main( String[] args ) throws CDFException.ReaderError {
        CDFReader cdf= new CDFReader("/home/jbf/ct/jenkins/data/cdf/rvar/ac_or_ssc_00000000_v01.cdf");
        TimeSeries ts= cdf.getTimeSeries("XYZ_GSE"); // zVariable
        System.err.println("ts.getTimes().length="+ts.getTimes().length);
        ts= cdf.getTimeSeries("RADIUS"); // rVariable
        System.err.println("ts.getTimes().length="+ts.getTimes().length);
        
        
    }
    
}
