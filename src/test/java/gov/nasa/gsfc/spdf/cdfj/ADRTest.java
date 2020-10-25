package gov.nasa.gsfc.spdf.cdfj;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

class ADRTest {
    {
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }

    static final Logger LOGGER = Logger.getLogger(ADRTest.class.getCanonicalName());

    @Test
    void testSetADRNext() {
        ADR adr = new ADR();
        int capacity = adr.record.capacity();
        LOGGER.log(Level.ALL, "adr capacity {0}", capacity);
    }

    @Test
    void testSetAgrEDRHead() {
        fail("Not yet implemented");
    }

    @Test
    void testSetScope() {
        fail("Not yet implemented");
    }

    @Test
    void testSetNum() {
        fail("Not yet implemented");
    }

    @Test
    void testSetNgrEntries() {
        fail("Not yet implemented");
    }

    @Test
    void testSetMAXgrEntry() {
        fail("Not yet implemented");
    }

    @Test
    void testSetAzEDRHead() {
        fail("Not yet implemented");
    }

    @Test
    void testSetNzEntries() {
        fail("Not yet implemented");
    }

    @Test
    void testSetMAXzEntry() {
        fail("Not yet implemented");
    }

    @Test
    void testSetName() {
        fail("Not yet implemented");
    }

    @Test
    void testGetNameBytes() {
        fail("Not yet implemented");
    }

    @Test
    void testGetSize() {
        fail("Not yet implemented");
    }

    @Test
    void testGet() {
        fail("Not yet implemented");
    }

}
