
package gov.nasa.gsfc.spdf.cdfj;

/**
 * Demos where lookup tables izvariableTable and irvariableTable
 * @author jbf
 */
public class CDFTest_Ap2019Oct23_IvariableTable {
    
    public static void main(String[] args ) throws Throwable {    
        demoRVariable( );
        demoZVariable( );
    }
    
    /**
     * demo irvariableTable.get( number ) for R variable.
     * @throws Throwable 
     */
    private static void demoZVariable() throws Throwable { 
        CDFImpl cdf= CDFFactory.getCDF("/home/jbf/autoplot_data/fscache/https/cdaweb.gsfc.nasa.gov/pub/software/cdf/cdf_test_files/twins1_l1_imager_2010102701_v01.cdf");
        Variable v= cdf.getVariable("twins_image_e8");
        System.err.println( v.asDoubleArray()[7] ); // 2.651
    }
    /**
     * demo irvariableTable.get( number ) for R variable.
     * @throws Throwable 
     */
    private static void demoRVariable() throws Throwable { 
        CDFImpl cdf= CDFFactory.getCDF("/home/jbf/autoplot_data/fscache/https/cdaweb.gsfc.nasa.gov/pub/software/cdf/cdf_test_files/ulysses.cdf");
        Variable v= cdf.getVariable("Lat_HGI");
        System.err.println( v.asDoubleArray()[0] ); // 5.300
    }
}
