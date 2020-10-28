package gov.nasa.gsfc.spdf.cdfj;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Attribute Descriptor Record
 * <p>
 * Information about an attribute
 *
 * @author nand
 * @author jmax01
 */
public class ADR {

    /** The Constant ADR_RECORD_TYPE_ID. */
    public static final int ADR_RECORD_TYPE_ID = 4;

    /** The Constant ADR_RECORD_SIZE_FIELD_OFFSET. */
    public static final int ADR_RECORD_SIZE_FIELD_OFFSET = 0;

    /** The Constant ADR_RECORD_SIZE_FIELD_BYTE_LEN. */
    public static final int ADR_RECORD_SIZE_FIELD_LENGTH = 8;

    /** The Constant ADR_RECORD_TYPE_FIELD_OFFSET. */
    public static final int ADR_RECORD_TYPE_OFFSET = ADR_RECORD_SIZE_FIELD_OFFSET + ADR_RECORD_SIZE_FIELD_LENGTH;

    /** The Constant ADR_RECORD_TYPE_FIELD_BYTE_LEN. */
    public static final int ADR_RECORD_TYPE_FIELD_LENGTH = 4;

    /** The Constant ADR_ADR_NEXT_FIELD_OFFSET. */
    public static final int ADR_ADR_NEXT_FIELD_OFFSET = ADR_RECORD_TYPE_OFFSET + ADR_RECORD_TYPE_FIELD_LENGTH;

    /** The Constant ADR_ADR_NEXT_FIELD_LENGTH. */
    public static final int ADR_ADR_NEXT_FIELD_LENGTH = 8;

    /** The Constant ADR_AGR_EDR_HEAD_FIELD_OFFSET. */
    public static final int ADR_AGR_EDR_HEAD_FIELD_OFFSET = ADR_ADR_NEXT_FIELD_OFFSET + ADR_ADR_NEXT_FIELD_LENGTH;

    /** The Constant ADR_AGR_EDR_HEAD_FIELD_LENGTH. */
    public static final int ADR_AGR_EDR_HEAD_FIELD_LENGTH = 8;

    /** The Constant AADR_SCOPE_FIELD_OFFSET. */
    public static final int AADR_SCOPE_FIELD_OFFSET = ADR_AGR_EDR_HEAD_FIELD_OFFSET + ADR_AGR_EDR_HEAD_FIELD_LENGTH;

    /** The Constant ADR_SCOPE_FIELD_LENGTH. */
    public static final int ADR_SCOPE_FIELD_LENGTH = 4;

    /** The Constant ADR_GLOBAL_SCOPE. */
    public static final int ADR_GLOBAL_SCOPE = 1;

    /** The Constant ADR_VARIABLE_SCOPE. */
    public static final int ADR_VARIABLE_SCOPE = 2;

    /** The Constant ADR_GLOBAL_SCOPE_ASSUMED. */
    public static final int ADR_GLOBAL_SCOPE_ASSUMED = 3;

    /** The Constant ADR_VARIABLE_SCOPE_ASSUMED. */
    public static final int ADR_VARIABLE_SCOPE_ASSUMED = 4;

    /** The Constant ADR_NUM_FIELD_OFFSET. */
    public static final int ADR_NUM_FIELD_OFFSET = AADR_SCOPE_FIELD_OFFSET + ADR_SCOPE_FIELD_LENGTH;

    /** The Constant ADR_NUM_FIELD_LENGTH. */
    public static final int ADR_NUM_FIELD_LENGTH = 4;

    /** The Constant ADR_NGR_ENTRIES_FIELD_OFFSET. */
    public static final int ADR_NGR_ENTRIES_FIELD_OFFSET = ADR_NUM_FIELD_OFFSET + ADR_NUM_FIELD_LENGTH;

    /** The Constant ADR_NGR_ENTRIES_FIELD_LENGTH. */
    public static final int ADR_NGR_ENTRIES_FIELD_LENGTH = 4;

    /** The Constant ADR_MAX_GR_ENTRY_FIELD_OFFSET */
    public static final int ADR_MAX_GR_ENTRY_FIELD_OFFSET = ADR_NGR_ENTRIES_FIELD_OFFSET + ADR_NGR_ENTRIES_FIELD_LENGTH;

    /** The Constant ADR_MAX_GR_ENTRY_FIELD_LENGTH. */
    public static final int ADR_MAX_GR_ENTRY_FIELD_LENGTH = 4;

    /** The Constant ADR_RFUA_FIELD_OFFSET. */
    public static final int ADR_RFUA_FIELD_OFFSET = ADR_MAX_GR_ENTRY_FIELD_OFFSET + ADR_MAX_GR_ENTRY_FIELD_LENGTH;

    /** The Constant ADR_RFUA_FIELD_LENGTH. */
    public static final int ADR_RFUA_FIELD_LENGTH = 4;

    /** The Constant ADR_AZ_EDR_HEAD_FIELD_OFFSET. */
    public static final int ADR_AZ_EDR_HEAD_FIELD_OFFSET = ADR_RFUA_FIELD_OFFSET + ADR_RFUA_FIELD_LENGTH;

    /** The Constant ADR_AZ_EDR_HEAD_FIELD_LENGTH. */
    public static final int ADR_AZ_EDR_HEAD_FIELD_LENGTH = 8;

    /** The Constant ADR_AZ_EDR_HEAD_FIELD_OFFSET. */
    public static final int ADR_NZ_ENTRIES_FIELD_OFFSET = ADR_AZ_EDR_HEAD_FIELD_OFFSET + ADR_AZ_EDR_HEAD_FIELD_LENGTH;

    /** The Constant ADR_AZ_EDR_HEAD_FIELD_LENGTH. */
    public static final int ADR_NZ_ENTRIES_FIELD_LENGTH = 4;

    /** The Constant ADR_MAX_Z_ENTRIES_FIELD_OFFSET. */
    public static final int ADR_MAX_Z_ENTRIES_FIELD_OFFSET = ADR_NZ_ENTRIES_FIELD_OFFSET + ADR_NZ_ENTRIES_FIELD_LENGTH;

    /** The Constant ADR_MAX_Z_ENTRIES_FIELD_LENGTH. */
    public static final int ADR_MAX_Z_ENTRIES_FIELD_LENGTH = 4;

    /** The Constant ADR_RFUE_FIELD_OFFSET. */
    public static final int ADR_RFUE_FIELD_OFFSET = ADR_MAX_Z_ENTRIES_FIELD_OFFSET + ADR_MAX_Z_ENTRIES_FIELD_LENGTH;

    /** The Constant ADR_RFUE_FIELD_LENGTH. */
    public static final int ADR_RFUE_FIELD_LENGTH = 4;

    /** The Constant ADR_NAME_FIELD_OFFSET. */
    public static final int ADR_NAME_FIELD_OFFSET = ADR_RFUE_FIELD_OFFSET + ADR_RFUE_FIELD_LENGTH;

    /** The Constant ADR_NAME_FIELD_LENGTH. */
    public static final int ADR_NAME_FIELD_LENGTH = 256;

    /** The Constant ADR_RECORD_SIZE. */
    public static final int ADR_RECORD_SIZE = ADR_NAME_FIELD_OFFSET + ADR_NAME_FIELD_LENGTH;

    /** The name. */
    protected String name;

    /** The padded name. */
    protected byte[] paddedName;

    /** The num. */
    protected int num;

    // FIXME: Not used
    /** The position. */
    protected long position;

    /** The a DR next. */
    long aDRNext;

    /** The agr EDR head. */
    long agrEDRHead;

    /** The az EDR head. */
    long azEDRHead;

    /** The m A xgr entry. */
    int mAXgrEntry = -1;

    /** The m A xz entry. */
    int mAXzEntry;

    /** The ngr entries. */
    int ngrEntries;

    /** The nz entries. */
    int nzEntries;

    /**
     * The rfu A.
     * Reserved for future used.
     * Signed 4-byte integer, big-endian byte ordering.
     * Always set to zero (0).
     */
    int rfuA = 0;

    /**
     * The rfu E.
     * Reserved for future use.
     * Signed 4-byte integer, big-endian byte ordering.
     * Always set to negative one (-1).
     */
    int rfuE = -1;

    int scope;

    // FIXME It is safer to only generate the buffer on demand.
    ByteBuffer record = ByteBuffer.allocate(ADR_RECORD_SIZE);

    /**
     * Gets the.
     *
     * @return the byte buffer
     */

    public ByteBuffer get() {
        this.record.position(0);
        this.record.putLong(ADR_RECORD_SIZE);
        this.record.putInt(ADR_RECORD_TYPE_ID);
        this.record.putLong(this.aDRNext);
        this.record.putLong(this.agrEDRHead);
        this.record.putInt(this.scope);
        this.record.putInt(this.num);
        this.record.putInt(this.ngrEntries);
        this.record.putInt(this.mAXgrEntry);
        this.record.putInt(this.rfuA);
        this.record.putLong(this.azEDRHead);
        this.record.putInt(this.nzEntries);
        this.record.putInt(this.mAXzEntry);
        this.record.putInt(this.rfuE);

        // should be replaced by this.record.put(this.paddedName);
        this.record.put(getNameBytes(this.name));

        this.record.position(0);

        return this.record;
    }

    /**
     * Gets the name bytes.
     *
     * @param s the s
     *
     * @return the name bytes
     * 
     */
    public byte[] getNameBytes(final String s) {

        final byte[] padded = new byte[ADR_NAME_FIELD_LENGTH];

        final byte[] bs = s.getBytes(StandardCharsets.US_ASCII);

        int i = 0;

        for (; i < bs.length; i++) {
            padded[i] = bs[i];
        }

        // This is unneeded as byte defaults to 0
        for (; i < padded.length; i++) {
            padded[i] = 0;
        }
        return padded;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    // FIXME: This is a constant
    public int getSize() {
        return this.record.limit();
    }


    /**
     * Sets the ADR next.
     * <p>
     * The file offset of the next ADR.
     * <p>
     * Signed 8-byte integer, big-endian byte ordering.
     * <p>
     * Beginning with CDF V2.1 the last ADR will
     * contain a file offset of 0x0000000000000000 in this field (to indicate the
     * end of the ADRs).
     * <p>
     * Prior to CDF V2.1 this file offset is undefined in the last ADR.
     *
     * @param aDRNext the new ADR next
     */
    public void setADRNext(final long aDRNext) {
        this.aDRNext = aDRNext;
    }


    /**
     * Sets the agr EDR head.
     * <p>
     * The file offset of the first Attribute g/rEntry Descriptor Record (AgrEDR)
     * for this attribute.
     * <p>
     * Signed 8-byte integer, big-endian byte ordering.
     * <p>
     * The first AgrEDR contains a file offset to the next AgrEDR and so on.
     * An AgrEDR will exist for each g/rEntry for this attribute. This field will
     * contain
     * 0x0000000000000000 if the attribute has no g/rEntries. Beginning with CDF
     * V2.1 the last AgrEDR will contain a file
     * offset of 0x0000000000000000 for the file offset of the next AgrEDR (to
     * indicate the end of 11the AgrEDRs).
     * <p>
     * Prior to CDF V2.1 the “next AgrEDR" file offset in the last AgrEDR is
     * undefined.
     * <p>
     * Note that the term g/rEntry is used to refer to an entry that may be either a
     * gEntry or an rEntry.
     * <p>
     * The type of entry described by an AgrEDR depends on the scope of the
     * corresponding attribute.
     * <p>
     * AgrEDRs of a global-scoped attribute describe gEntries.
     * <p>
     * AgrEDRs of a variable-scoped attribute describe rEntries.
     * 
     * @param agrEDRHead the new agr EDR head
     */
    public void setAgrEDRHead(final long agrEDRHead) {
        this.agrEDRHead = agrEDRHead;
    }


    /**
     * Sets the az EDR head.
     * <p>
     * The file offset of the first Attribute zEntry Descriptor Record (AzEDR) for
     * this attribute.
     * <p>
     * Signed 8-byte integer, big-endian byte ordering.
     * <p>
     * The first AzEDR contains a file offset to the next AzEDR and so on.
     * <p>
     * An AzEDR will exist for each zEntry for this attribute.
     * <p>
     * This field will contain 0x0000000000000000 if this attribute has no zEntries.
     * <p>
     * The last AzEDR will contain a file offset of 0x0000000000000000 for the file
     * offset of the next AzEDR (to indicate the end of the AzEDRs).
     * 
     * @param azEDRHead the new az EDR head
     */
    public void setAzEDRHead(final long azEDRHead) {
        this.azEDRHead = azEDRHead;
    }

    /**
     * Sets the Max gr entry.
     * <p>
     * The maximum numbered g/rEntry for this attribute.
     * <p>
     * Signed 4-byte integer, big-endian byte ordering.
     * <p>
     * g/rEntries are numbered beginning with zero (0).
     * <p>
     * If there are no g/rEntries, this field will contain negative one (-1).
     * @param mAXgrEntry the new MA xgr entry
     */
    public void setMAXgrEntry(final int mAXgrEntry) {
        this.mAXgrEntry = mAXgrEntry;
    }


    /**
     * Sets the Max z entry.
     * <p>
     * The maximum numbered zEntry for this attribute.
     * <p>
     * Signed 4-byte integer, big-endian byte ordering.
     * <p>
     * zEntries are numbered beginning with zero (0).
     * <p>
     * Prior to CDF V2.2 this field will always contain a value of negative one
     * (-1).
     * 
     * @param mAXzEntry the new MA xz entry
     */
    public void setMAXzEntry(final int mAXzEntry) {
        this.mAXzEntry = mAXzEntry;
    }


    /**
     * Sets the name.
     * <p>
     * The name of this attribute.
     * <p>
     * Character string, ASCII character set.
     * <p>
     * This field is always 256 bytes in length.
     * <p>
     * If the number of characters in the name is less than 256, a NUL character
     * (0x00) will be used to terminate the string.
     * <p>
     * In that case, the characters beyond the NUL-terminator (up to the size of
     * this field) are undefined.
     *
     * @param name the new name, may not be null.
     * 
     * @throws IllegalArgumentException if the name is longer than
     *                                  {@link ADR_NAME_LENGTH}.
     */
    public void setName(final String name) {

        Objects.requireNonNull(name, "name cannot be null.");

        byte[] nameAsBytes = name.getBytes(StandardCharsets.US_ASCII);

        int nameLength = nameAsBytes.length;

        if (nameLength > ADR_NAME_FIELD_LENGTH) {
            throw new IllegalArgumentException("ADR names cannot exceed " + ADR_NAME_FIELD_LENGTH
                    + " bytes. The supplied name, " + name + " is " + nameLength + " bytes long.");
        }

        this.name = name;

        @SuppressWarnings("hiding")
        final byte[] paddedName = new byte[256];

        System.arraycopy(nameAsBytes, 0, paddedName, 0, nameLength);

        this.paddedName = paddedName;
    }


    /**
     * Sets the ngr entries.
     * <p>
     * The number of g/rEntries for this attribute.
     * <p>
     * Signed 4-byte integer, big-endian byte ordering.
     * 
     *
     * @param ngrEntries the new ngr entries
     */
    public void setNgrEntries(final int ngrEntries) {
        this.ngrEntries = ngrEntries;
    }

    /**
     * Sets the num.
     * <p>
     * This attribute's number.
     * <p>
     * Signed 4-byte integer, big-endian byte ordering.
     * <p>
     * Attributes are numbered beginning with zero (0)
     * 
     * @param num the new num
     */
    public void setNum(final int num) {
        this.num = num;
    }


    /**
     * Sets the nz entries.
     * <p>
     * The number of zEntries for this attribute.
     * <p>
     * Signed 4-byte integer, big-endian byte ordering.
     * <p>
     * Prior to CDF V2.2 this field will always contain a value of zero (0).
     * 
     * @param nzEntries the new nz entries
     */
    public void setNzEntries(final int nzEntries) {
        this.nzEntries = nzEntries;
    }


    /**
     * Sets the scope.
     * <p>
     * The intended scope of this attribute.
     * <p>
     * Signed 4-byte integer, big-endian byte ordering.
     * <p>
     * The following internal values are valid:
     * <p>
     * 1 Global scope.
     * <p>
     * 2 Variable scope.
     * <p>
     * 3 Global scope assumed.
     * <p>
     * 4 Variable scope assumed.
     * 
     * @param scope the new scope
     * 
     * @throws {@link IllegalArgumentException} if the supplied scope is not 1,
     *                2, 3,or 4;
     */
    public void setScope(final int scope) {

        switch (scope) {
        case ADR_GLOBAL_SCOPE:
        case ADR_VARIABLE_SCOPE:
        case ADR_GLOBAL_SCOPE_ASSUMED:
        case ADR_VARIABLE_SCOPE_ASSUMED:
            break;
        default:
            throw new IllegalArgumentException("Scope, " + scope + ", is not valid. Scope must be 1, 2, 3, or 4.");

        }
        this.scope = scope;
    }
}
