package gov.nasa.gsfc.spdf.cdfj;

import static gov.nasa.gsfc.spdf.cdfj.ADR.*;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

class ADRTest {

    static final String DEFAULT_NAME_VALUE = "namevalue";

    static final Logger LOGGER = Logger.getLogger(ADRTest.class.getCanonicalName());

    static ADR newDefaultAdrInstance() {
        final ADR adr = new ADR();
        adr.setName(DEFAULT_NAME_VALUE);
        return adr;
    }

    @Test
    void testLogger() {
        LOGGER.log(Level.WARNING, "Warning");
        LOGGER.log(Level.SEVERE, "SEVERE");
        LOGGER.log(Level.INFO, "INFO");
    }

    @Test
    void testGetNameBytes() {
        final ADR adr = newDefaultAdrInstance();
        final String name = "A";
        final byte[] expected = new byte[ADR_NAME_FIELD_LENGTH];
        expected[0] = name.getBytes()[0];
        final byte[] actual = adr.getNameBytes(name);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetSize() {
        final ADR adr = newDefaultAdrInstance();
        assertEquals(ADR_RECORD_SIZE, adr.getSize());

    }

    @Test
    void testRecordCapacity() {
        final ADR adr = newDefaultAdrInstance();
        final int capacity = adr.record.capacity();
        assertEquals(ADR_RECORD_SIZE, capacity);
    }

    @Test
    void testSetADRNext() {

        final ADR adr = newDefaultAdrInstance();
        final long expected = 1L;
        adr.setADRNext(expected);
        assertEquals(expected, adr.aDRNext);

        final ByteBuffer adrRecord = adr.get();
        final long adrNextFromRecord = adrRecord.getLong(ADR_ADR_NEXT_FIELD_OFFSET);
        assertEquals(expected, adrNextFromRecord);
    }

    @Test
    void testSetAgrEDRHead() {
        final ADR adr = newDefaultAdrInstance();
        final long expected = 2L;
        adr.setAgrEDRHead(expected);
        assertEquals(expected, adr.agrEDRHead);

        final ByteBuffer adrRecord = adr.get();
        assertEquals(expected, adrRecord.getLong(ADR_AGR_EDR_HEAD_FIELD_OFFSET));
    }

    @Test
    void testSetAzEDRHead() {
        final ADR adr = newDefaultAdrInstance();
        final long expected = 3L;
        adr.setAzEDRHead(expected);
        assertEquals(expected, adr.azEDRHead);

        final ByteBuffer adrRecord = adr.get();
        assertEquals(expected, adrRecord.getLong(ADR_AZ_EDR_HEAD_FIELD_OFFSET));
    }

    @Test
    void testSetMAXgrEntry() {
        final ADR adr = newDefaultAdrInstance();
        final int expected = 2;
        adr.setMAXgrEntry(expected);
        assertEquals(expected, adr.mAXgrEntry);

        final ByteBuffer adrRecord = adr.get();
        assertEquals(expected, adrRecord.getInt(ADR_MAX_GR_ENTRY_FIELD_OFFSET));
    }

    @Test
    void testSetMAXzEntry() {
        final ADR adr = newDefaultAdrInstance();
        final int expected = 3;
        adr.setMAXzEntry(expected);
        assertEquals(expected, adr.mAXzEntry);

        final ByteBuffer adrRecord = adr.get();
        assertEquals(expected, adrRecord.getInt(ADR_MAX_Z_ENTRIES_FIELD_OFFSET));
    }

    @Test
    void testSetName() {
        final ADR adr = newDefaultAdrInstance();
        final int expected = 5;
        adr.setNum(expected);
        assertEquals(expected, adr.num);

        final ByteBuffer adrRecord = adr.get();
        assertEquals(expected, adrRecord.getInt(ADR_NUM_FIELD_OFFSET));

    }

    @Test
    void testSetNameTooLong() {

        final String tooLong = new String(new byte[ADR_NAME_FIELD_LENGTH + 1]);

        final Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new ADR().setName(tooLong);
        });
        assertEquals("ADR names cannot exceed " + ADR_NAME_FIELD_LENGTH + " bytes. The supplied name, " + tooLong
                + " is " + tooLong.length() + " bytes long.", e.getMessage());
    }

    @Test
    void testSetNgrEntries() {
        final ADR adr = newDefaultAdrInstance();
        final int expected = 4;
        adr.setNgrEntries(expected);
        assertEquals(expected, adr.ngrEntries);

        final ByteBuffer adrRecord = adr.get();
        assertEquals(expected, adrRecord.getInt(ADR_NGR_ENTRIES_FIELD_OFFSET));
    }

    @Test
    void testSetNum() {
        final ADR adr = newDefaultAdrInstance();
        final int expected = 5;
        adr.setNum(expected);
        assertEquals(expected, adr.num);

        final ByteBuffer adrRecord = adr.get();
        assertEquals(expected, adrRecord.getInt(ADR_NUM_FIELD_OFFSET));
    }

    @Test
    void testSetNzEntries() {
        final ADR adr = newDefaultAdrInstance();
        final int expected = 6;
        adr.setNzEntries(expected);
        assertEquals(expected, adr.nzEntries);

        final ByteBuffer adrRecord = adr.get();
        assertEquals(expected, adrRecord.getInt(ADR_NZ_ENTRIES_FIELD_OFFSET));
    }

    @Test
    void testSetScope() {

        final int[] expected = { ADR_GLOBAL_SCOPE, ADR_VARIABLE_SCOPE, ADR_GLOBAL_SCOPE_ASSUMED,
                ADR_VARIABLE_SCOPE_ASSUMED };

        for (final int scope : expected) {
            final ADR adr = newDefaultAdrInstance();
            adr.setScope(scope);
            assertEquals(scope, adr.scope);

            final ByteBuffer adrRecord = adr.get();
            assertEquals(scope, adrRecord.getInt(AADR_SCOPE_FIELD_OFFSET));
        }

    }

    @Test
    void testSetScopeInvalidScope() {

        final int badScope = 5;

        final ADR adr = newDefaultAdrInstance();

        final Exception e = assertThrows(IllegalArgumentException.class, () -> {
            adr.setScope(badScope);
        });
        assertEquals("Scope, " + badScope + ", is not valid. Scope must be 1, 2, 3, or 4.", e.getMessage());
    }
}
