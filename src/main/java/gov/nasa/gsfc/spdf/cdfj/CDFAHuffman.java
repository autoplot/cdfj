package gov.nasa.gsfc.spdf.cdfj;

public class CDFAHuffman extends CDFHuffman {

  Tree tree;

  class Tree_Node {
    int count;
    int saved_count;
    int child_0;
    int child_1;
  };

  class Node {
    int weight;
    int parent;
    boolean child_is_leaf;
    int child;
  };

  class Tree {
    int[] leaf = new int[ SYMBOL_COUNT ]; // 258
    int next_free_node;
    Node[] nodes = new Node[ NODE_TABLE_COUNT ]; // 258*2-1
  };

public CDFAHuffman () {
  super();
  tree  = new Tree();
  for (int i = 0; i < tree.nodes.length; ++i)
    tree.nodes[i] = new Node();
}

/******************************************************************************
* compress.
******************************************************************************/

public byte[] compress (
byte[] input,
long iSize)
{
  int c; int i;
  byte[] output = new byte[10*(int)iSize];
  initializeTree ();
  for (i = 0; i < iSize; i++) {
     c = (int) input[i];
     encodeSymbol((int)c);
     updateModel (c);
  }
  encodeSymbol((int)END_OF_STREAM);
  endOutputBitFile();
  
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
  int c;
  this.output = new byte[oSize];
  this.iByteN = 0;
  this.oByteN = 0;
  this.input = input;
  this.iSize = input.length;
  initializeTree ();
  while ((c = decodeSymbol()) != END_OF_STREAM) {
    if (c == -1) {
      return null;
    }
    output[oByteN] = (byte) c;
    oByteN++;
     
    updateModel (c);
  }
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
* endOutputBitFile.
******************************************************************************/

void endOutputBitFile ()
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
* inputBits.
******************************************************************************/

long inputBits ( // DWORD
int bit_count)
{
    long mask; //DWORD
    long return_value; //DWORD

    mask = 1L << ( bit_count - 1 );
    return_value = 0;
    while ( mask != 0) {
	if ( bit_file.mask == 128 ) { //0x80
	    bit_file.rack = (input[iByteN] >= 0 ? input[iByteN] :
                                                  UBYTE_MAX + input[iByteN]);
            iByteN++;
	    if ( bit_file.rack == -1 ) return ((int) -1);
	}
	if ( (bit_file.rack & bit_file.mask) != 0 ) return_value |= mask;
	mask >>= 1;
	bit_file.mask >>= 1;
	if ( bit_file.mask == 0 ) bit_file.mask = 128; // 0x80
    }
    return( return_value );
}

/******************************************************************************
* initializeTree.
******************************************************************************/

void initializeTree ()
{
    int i;
    tree.nodes[ ROOT_NODE ].child             = ROOT_NODE + 1;
    tree.nodes[ ROOT_NODE ].child_is_leaf     = false;
    tree.nodes[ ROOT_NODE ].weight            = 2;
    tree.nodes[ ROOT_NODE ].parent            = -1;

    tree.nodes[ ROOT_NODE + 1 ].child         = END_OF_STREAM;
    tree.nodes[ ROOT_NODE + 1 ].child_is_leaf = true;
    tree.nodes[ ROOT_NODE + 1 ].weight        = 1;
    tree.nodes[ ROOT_NODE + 1 ].parent        = ROOT_NODE;
    tree.leaf[ END_OF_STREAM ]                = ROOT_NODE + 1;

    tree.nodes[ ROOT_NODE + 2 ].child         = ESCAPE;
    tree.nodes[ ROOT_NODE + 2 ].child_is_leaf = true;
    tree.nodes[ ROOT_NODE + 2 ].weight        = 1;
    tree.nodes[ ROOT_NODE + 2 ].parent        = ROOT_NODE;
    tree.leaf[ ESCAPE ]                       = ROOT_NODE + 2;

    tree.next_free_node                       = ROOT_NODE + 3;
    for ( i = 0 ; i < END_OF_STREAM ; i++ ) tree.leaf[ i ] = -1;
}

/******************************************************************************
* encodeSymbol.
******************************************************************************/

void encodeSymbol (
int c)
{
    long code; //DWORD
    long current_bit; //DWORD
    int code_size;
    int current_node;

    code = 0;
    current_bit = 1;
    code_size = 0;
    current_node = tree.leaf[(int)c];
    if ( current_node == -1 ) current_node = tree.leaf[ ESCAPE ];
    while ( current_node != ROOT_NODE ) {
	if ( ( current_node & 1 ) == 0 ) code |= current_bit;
	current_bit <<= 1;
	code_size++;
	current_node = tree.nodes[ current_node ].parent;
    };
    outputBits((long)code,code_size);
    if ( tree.leaf[c] == -1 ) {
	outputBits((long)c,8);
	add_new_node( c );
    }
}

/******************************************************************************
* decodeSymbol.
******************************************************************************/

int decodeSymbol ()
{
    int current_node;
    int c;
    int bit;

    current_node = ROOT_NODE;
    while ( !tree.nodes[ current_node ].child_is_leaf ) {
	current_node = tree.nodes[ current_node ].child;
	bit = inputBit ();
	if (bit == -1) return -1;
	current_node += bit;
    }
    c = tree.nodes[ current_node ].child;
    if ( c == ESCAPE ) {
	c = (int) inputBits( 8 );
	if (c == -1) return -1;
	add_new_node( c );
    }
    return( c );
}

/******************************************************************************
* updateModel.
******************************************************************************/

void updateModel (
int c)
{
    int current_node;
    int new_node;

    if ( tree.nodes[ ROOT_NODE].weight == MAX_WEIGHT ) rebuildTree();
    current_node = tree.leaf[ c ];
    while ( current_node != -1 ) {
	tree.nodes[ current_node ].weight++;
	for ( new_node = current_node ; new_node > ROOT_NODE ; new_node-- ) {
	    if ( tree.nodes[ new_node - 1 ].weight >=
		 tree.nodes[ current_node ].weight )
		break;
        }
	if ( current_node != new_node ) {
	    swap_nodes( current_node, new_node );
	    current_node = new_node;
	}
	current_node = tree.nodes[ current_node ].parent;
    }

    return;
}

/******************************************************************************
* rebuildTree.
******************************************************************************/

void rebuildTree ()
{
    int i;
    int j;
    int k;
    int weight; // WORD

    j = tree.next_free_node - 1;
    for ( i = j ; i >= ROOT_NODE ; i-- ) {
	if ( tree.nodes[ i ].child_is_leaf ) {
	    tree.nodes[ j ].parent = tree.nodes[ i ].parent;
	    tree.nodes[ j ].child = tree.nodes[ i ].child;
	    tree.nodes[ j ].child_is_leaf = tree.nodes[ i ].child_is_leaf;
	    tree.nodes[ j ].weight = tree.nodes[i].weight;
	    tree.nodes[ j ].weight = (tree.nodes[j].weight + 1) / 2;
	    j--;
	}
    }

    for ( i = tree.next_free_node - 2 ; j >= ROOT_NODE ; i -= 2, j-- ) {
	k = i + 1;
	tree.nodes[ j ].weight = tree.nodes[ i ].weight +
				  tree.nodes[ k ].weight;
	weight = tree.nodes[ j ].weight;
	tree.nodes[ j ].child_is_leaf = false;
	for ( k = j + 1 ; weight < tree.nodes[ k ].weight ; k++ )
	    ;
	k--;
        for (int ix = 0; ix < (k-j); ++ix) {
          tree.nodes[j+ix].weight = tree.nodes[j+ix+1].weight;
          tree.nodes[j+ix].parent = tree.nodes[j+ix+1].parent;
          tree.nodes[j+ix].child_is_leaf = tree.nodes[j+ix+1].child_is_leaf;
          tree.nodes[j+ix].child = tree.nodes[j+ix+1].child;
        }
	tree.nodes[ k ].weight = weight;
	tree.nodes[ k ].child = i;
	tree.nodes[ k ].child_is_leaf = false;
    }
 
    for ( i = tree.next_free_node - 1 ; i >= ROOT_NODE ; i-- ) {
	if ( tree.nodes[ i ].child_is_leaf ) {
	    k = tree.nodes[ i ].child;
	    tree.leaf[ k ] = i;
	} else {
	    k = tree.nodes[ i ].child;
	    tree.nodes[ k ].parent = tree.nodes[ k + 1 ].parent = i;
	}
    }

    return;
}

/******************************************************************************
* swap_nodes - swap nodes, but leave parent field intact.
******************************************************************************/

void swap_nodes (
int i,
int j)
{

    if ( tree.nodes[ i ].child_is_leaf )
	tree.leaf[ tree.nodes[ i ].child ] = j;
    else {
	tree.nodes[ tree.nodes[ i ].child ].parent = j;
	tree.nodes[ tree.nodes[ i ].child + 1 ].parent = j;
    }
    if ( tree.nodes[ j ].child_is_leaf )
	tree.leaf[ tree.nodes[ j ].child ] = i;
    else {
	tree.nodes[ tree.nodes[ j ].child ].parent = i;
	tree.nodes[ tree.nodes[ j ].child + 1 ].parent = i;
    }

    Node temp = new Node();
    temp.child = tree.nodes[ i ].child;
    temp.weight = tree.nodes[ i ].weight;
    temp.child_is_leaf = tree.nodes[ i ].child_is_leaf;
    tree.nodes[ i ].weight = tree.nodes[ j ].weight;
    tree.nodes[ i ].child = tree.nodes[ j ].child;
    tree.nodes[ i ].child_is_leaf = tree.nodes[ j ].child_is_leaf;
    tree.nodes[ j ].weight = temp.weight;
    tree.nodes[ j ].child = temp.child;
    tree.nodes[ j ].child_is_leaf = temp.child_is_leaf;

    return;
}

/******************************************************************************
* add_new_node.
******************************************************************************/

void add_new_node (
int c)
{
    int lightest_node;
    int new_node;
    int zero_weight_node;

    lightest_node = tree.next_free_node - 1;
    new_node = tree.next_free_node;
    zero_weight_node = tree.next_free_node + 1;
    tree.next_free_node += 2;

    tree.nodes[ new_node ].weight = tree.nodes[ lightest_node ].weight;
    tree.nodes[ new_node ].child = tree.nodes[ lightest_node ].child;
    tree.nodes[ new_node ].child_is_leaf = tree.nodes[ lightest_node ].child_is_leaf;
    tree.nodes[ new_node ].parent = lightest_node;
    tree.leaf[ tree.nodes[ new_node ].child ] = new_node;

    tree.nodes[ lightest_node ].child         = new_node;
    tree.nodes[ lightest_node ].child_is_leaf = false;

    tree.nodes[ zero_weight_node ].child           = c;
    tree.nodes[ zero_weight_node ].child_is_leaf   = true;
    tree.nodes[ zero_weight_node ].weight          = 0;
    tree.nodes[ zero_weight_node ].parent          = lightest_node;
    tree.leaf[ c ] = zero_weight_node;

    return;
}

}
