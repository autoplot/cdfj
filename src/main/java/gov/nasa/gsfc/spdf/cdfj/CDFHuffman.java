package gov.nasa.gsfc.spdf.cdfj;

public class CDFHuffman {
    Bit_File bit_file;
    long[] counts;
    Tree_Node[] tnodes;
    Code[] codes;
    byte[] input;
    byte[] output;
    int iSize;
    int iByteN;
    int oByteN;

/******************************************************************************
* Macros/prototypes.
******************************************************************************/

  protected static final int END_OF_STREAM =    256;
  protected static final int ESCAPE =           257;
  protected static final int SYMBOL_COUNT =     258;
  protected static final int NODE_TABLE_COUNT = (SYMBOL_COUNT*2)-1; //515
  protected static final int ROOT_NODE =          0;
  protected static final int MAX_WEIGHT =     32768; // 0x8000
  protected static final int UBYTE_MAX =        256;
  protected static final int USHORT_MAX =     65535;
  protected static final long UINT_MAX = 4294967295L;

  class Tree_Node {
    int count;
    int saved_count;
    int child_0;
    int child_1;
  };

  class Code {
    int code;
    int code_bits;
  };

  class Bit_File {
    short mask;
    int  rack;
    private Bit_File() {
    }
    void startBit() {
      rack = 0;
      mask = 128; // 0x80
    }
  };

  class Node {
    int weight;
    int parent;
    boolean child_is_leaf;
    int child;
  };

public CDFHuffman () {
  bit_file = new Bit_File();
  bit_file.startBit();
  counts =  new long[256];
  tnodes = new Tree_Node[514];
  for (int i = 0; i < tnodes.length; ++i)
    tnodes[i] = new Tree_Node();
  codes = new Code[257];
}

/******************************************************************************
* compress.
******************************************************************************/

public byte[] compress (
byte[] input,
int iSize)
{
  int root_node;
  int oSize = 0;
  this.input = input;
  this.iSize = iSize;
  this.iByteN = this.oByteN = 0;
  count_bytes(input,counts,iSize);
  byte[] output = new byte[10*(int)iSize];
  scale_counts ();
  output_counts();
  
  root_node = build_tree ();
  convert_tree_to_code (0, 0, root_node);
  compress_data();
  
  endOutputBit();
  
  byte[] noutput = new byte[oByteN];
  System.arraycopy(output, 0, noutput, 0, oByteN);
  return noutput;
}

/******************************************************************************
* decompress.
******************************************************************************/

public byte[] decompress (
byte[] input,
int oSize)
{
  int root_node;
  this.output = new byte[oSize];
  this.iByteN = 0;
  this.oByteN = 0;
  this.input = input;
  this.iSize = input.length;

  input_counts();
  
  root_node = build_tree( );
  expand_data(root_node);
 
  return output;
}

/******************************************************************************
* output_counts.
******************************************************************************/
 
void output_counts ()
{
    int first;
    int last;
    int next;
    int i;

    first = 0;
    last = 0;
    while ( first < 255 && tnodes[ first ].count == 0 )
	    first++;
 
    for ( ; first < 256 ; first = next ) {
	last = first + 1;
	for ( ; ; ) {
	    for ( ; last < 256 ; last++ )
		if ( tnodes[ last ].count == 0 )
		    break;
	    last--;
	    for ( next = last + 1; next < 256 ; next++ )
		if ( tnodes[ next ].count != 0 )
		    break;
	    if ( next > 255 )
		break;
	    if ( ( next - last ) > 3 )
		break;
	    last = next;
	};

        output[oByteN] = (byte) first;
        oByteN++;
        output[oByteN] = (byte) last;
        oByteN++;
	for ( i = first ; i <= last ; i++ ) {
            output[oByteN] = (byte) tnodes[ i ].count;
            oByteN++;
	}
    }
    output[oByteN] = 0;
    oByteN++;
}

/******************************************************************************
* count_bytes.
******************************************************************************/

void count_bytes (
byte[] input,
long[] counts, //DWORD
long iSize)
{
    int c;
    int i;

    for (i = 0; i < iSize; i++) {
      c = (int) input[i];
      counts[c]++;
    }

}

/******************************************************************************
* scale_counts.
******************************************************************************/
 
void scale_counts ()
{
    long max_count; // DWORD
    int i;

    max_count = 0;
    for ( i = 0 ; i < 256 ; i++ )
       if ( counts[ i ] > max_count )
	   max_count = counts[ i ];
    if ( max_count == 0 ) {
	counts[ 0 ] = 1;
	max_count = 1;
    }
    max_count = max_count / 255;
    max_count = max_count + 1;
    for ( i = 0 ; i < 256 ; i++ ) {
	tnodes[ i ].count = (int) (counts[ i ] / max_count) ;
	if ( tnodes[ i ].count == 0 && counts[ i ] != 0 )
	    tnodes[ i ].count = 1;
    }
    tnodes[ END_OF_STREAM ].count = 1;
}

/******************************************************************************
* build_tree.
******************************************************************************/
 
int build_tree ()
{
    int next_free;
    int i;
    int min_1;
    int min_2;
    tnodes[ 513 ].count = 65535; // 0xffff
    for ( next_free = END_OF_STREAM + 1 ; ; next_free++ ) {
	min_1 = 513;
	min_2 = 513;
	for ( i = 0 ; i < next_free ; i++ ) {
	    if ( tnodes[ i ].count != 0 ) {
		if ( tnodes[ i ].count < tnodes[ min_1 ].count ) {
		    min_2 = min_1;
		    min_1 = i;
		} else if ( tnodes[ i ].count < tnodes[ min_2 ].count )
		    min_2 = i;
	    }
        }
	if ( min_2 == 513 )
	    break;
	tnodes[ next_free ].count = tnodes[ min_1 ].count
				   + tnodes[ min_2 ].count;
	tnodes[ min_1 ].saved_count = tnodes[ min_1 ].count;
	tnodes[ min_1 ].count = 0;
	tnodes[ min_2 ].saved_count =  tnodes[ min_2 ].count;
	tnodes[ min_2 ].count = 0;
	tnodes[ next_free ].child_0 = min_1;
	tnodes[ next_free ].child_1 = min_2;
    }
    next_free--;
    tnodes[ next_free ].saved_count = tnodes[ next_free ].count;
    return( next_free );
}

/******************************************************************************
* convert_tree_to_code.
******************************************************************************/
 
void convert_tree_to_code (
int code_so_far, // INT4
int bits,
int node)
{
  if (node <= END_OF_STREAM) {
    codes[node].code = (short) code_so_far;
    codes[node].code_bits = bits;
    return;
  }
  code_so_far <<= 1;
  bits++;
  convert_tree_to_code (code_so_far, bits, tnodes[node].child_0 );
  convert_tree_to_code ((code_so_far | 1), bits, tnodes[node].child_1 );
  return;
}

/******************************************************************************
* compress_data.
******************************************************************************/

int compress_data ()
{
    int c;
    long i;
    for (i = 0; i < iSize; i++) {
       c = (int) input[iByteN];
       iByteN++;
       outputBits((long)codes[c].code,codes[c].code_bits);
    }
    outputBits((long)codes[END_OF_STREAM].code,
	       codes[END_OF_STREAM].code_bits);
    return 0;
}

/******************************************************************************
* endOutputBit.
******************************************************************************/

void endOutputBit ()
{
  if (bit_file.mask != 128) { //0x80
    output[oByteN] = (byte) bit_file.rack;
    oByteN++;
  }
}

/******************************************************************************
* outputBits.
******************************************************************************/

void outputBits (
long code, //DWORD
int count)
{
    long mask; //DWORD
    mask = 1 << ( count - 1 );
    while ( mask != 0) {
	if ( (mask & code) != 0 ) bit_file.rack |= bit_file.mask;
	bit_file.mask >>= 1;
	if ( bit_file.mask == 0 ) {
            output[oByteN] = (byte) bit_file.rack;
            oByteN++;
	    bit_file.rack = 0;
	    bit_file.mask = 128; // 0x80
	}
	mask >>= 1;
    }
}

/******************************************************************************
* inputBit.
******************************************************************************/

int inputBit ()
{
    int value;
    if ( bit_file.mask == 128 ) { //  0x80
	bit_file.rack = (input[iByteN] >= 0 ? input[iByteN] :
                                              UBYTE_MAX + input[iByteN]);
        iByteN++;
	if ( bit_file.rack == -1 ) return -1;
    }
    value = bit_file.rack & bit_file.mask;
    bit_file.mask >>= 1;
    if ( bit_file.mask == 0 ) bit_file.mask = 128; // 0x80
    return ( (value != 0)? 1 : 0 );
}

/******************************************************************************
* input_counts.
******************************************************************************/

void input_counts ()
{
    int first;
    int last;
    int i;
    int c;
    for ( i = 0 ; i < 256 ; i++ ) tnodes[ i ].count = 0;
    first = (int) input[iByteN];
    iByteN++;
    first = (first >= 0 ? first : (UBYTE_MAX+first));
    last = (int) input[iByteN];
    iByteN++;
    last = (last >= 0 ? last : (UBYTE_MAX+last));
    for ( ; ; ) {
	for ( i = first ; i <= last ; i++ ) {
            c = (int) input[iByteN];
            iByteN++; 
	    tnodes[ i ].count = (c >= 0 ? c : (UBYTE_MAX + c)); // (WORD) c;
	}
        first = (int) input[iByteN];
        iByteN++;
	if ( first == 0 ) break;
        first = (first > 0 ? first : (UBYTE_MAX+first));
        last = (int) input[iByteN];
        iByteN++;
        last = (last >= 0 ? last : (UBYTE_MAX+last));
    }
    tnodes[ END_OF_STREAM ].count = 1;
}

/******************************************************************************
* expand_data.
******************************************************************************/

void expand_data (
int root_node)
{
    int node1;
    for ( ; ; ) {
	node1 = root_node;
	do {
	    int bit = inputBit ();
	    if (bit != 0)
		node1 = tnodes[ node1 ].child_1;
	    else
		node1 = tnodes[ node1 ].child_0;
	} while ( node1 > END_OF_STREAM );

	if ( node1 == END_OF_STREAM ) break;
        output[oByteN] = (byte) node1;
        oByteN++;
    }
}

}
