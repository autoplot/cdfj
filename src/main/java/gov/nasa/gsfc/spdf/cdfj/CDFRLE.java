package gov.nasa.gsfc.spdf.cdfj;

import java.io.*;
import java.nio.*;
import java.text.*;
import java.util.*;
import java.lang.reflect.*;
import java.util.*;

public class CDFRLE {

    public static byte[] compress(byte[] input) {
        byte aByte, zCount, zero, max255 = (byte)255;
        int byteN, count, outx, istop, total = input.length;
        byte[] output = new byte[3*total]; // Assuming the compressed data is
                                           // 3x or less than the original 
        byteN = count = outx = istop = 0;
        zero = zCount = (byte) 0;
        for (;;) {
          if (byteN == total) break;
            aByte = input[byteN];
            byteN++;
            if (aByte == 0) {
              count = 1;
              for (;;) {
                if (byteN == total) {
                  output[outx] = (byte) zero;
                  outx++;
                  zCount = (byte) (count - 1);
                  output[outx] = (byte) zCount;
                  outx++;
                  istop = 1;
                  break;
                }
                aByte = input[byteN];
                byteN++;
                if (aByte != 0) {
                  output[outx] = zero;
                  outx++;
                  zCount = (byte) (count - 1);
                  output[outx] = zCount;
                  outx++;
                  output[outx] = aByte;
                  outx++;
                  break;
                }
                count++;
                if (count == 256) {
                  output[outx] = zero;
                  outx++;
                  output[outx] = max255;
                  outx++;
                  break;
               }
             }
             if (istop  == 1) break;
           }
           else {
             output[outx] = aByte;
             outx++;
           }
        }
        byte[] noutput = new byte[outx];
        System.arraycopy(noutput, 0, output, 0, outx);
        return noutput;
    }

    public static byte[] decompress(byte[] input, int osize) {
        byte[] output = new byte[osize];
        byte aByte, zCount, zero;
        int byteN, count, outx, total = input.length;
        byteN = count = outx = 0;
        outx = 0;
        zero = (byte) 0;
        for (;;) {
          if (byteN == total) break;
          aByte = input[byteN];
          byteN++;
          if (aByte == 0) {
            zCount = input[byteN];
            byteN++;
            count = (zCount >= 0 ? zCount : (256+zCount)) + 1;
            for (int i = 0; i < count; ++i) {
               output[outx] = (byte) zero;
               outx++;
            }
          } else {
            output[outx] = aByte;
            outx++;
          }
        }
        return output;
    }
}
