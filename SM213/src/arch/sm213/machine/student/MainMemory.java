package arch.sm213.machine.student;

import machine.AbstractMainMemory;


/**
 * Main Memory of Simple CPU.
 *
 * Provides an abstraction of main memory (DRAM).
 */

public class MainMemory extends AbstractMainMemory {
  private byte [] mem;
  
  /**
   * Allocate memory.
   * @param byteCapacity size of memory in bytes.
   */
  public MainMemory (int byteCapacity) {
    mem = new byte [byteCapacity];
  }
  
  /**
   * Determine whether an address is aligned to specified length.
   * @param address memory address.
   * @param length byte length.
   * @return true iff address is aligned to length.
   */
//  @Override protected boolean isAccessAligned (int address, int length) {
//    boolean answer = false;
//    if (length % 2 == 0){
//      if (address % length == 0) {
//        answer = true;
//      }
//    }
//    return answer;
//  }

  @Override protected boolean isAccessAligned (int address, int length) {
    boolean answer = false;
    //Check if it is an int
    if (address % 2 == 0) {
      //Check if it is aligned
      if (address % length == 0) {
        answer = true;
      }
    }
    //The address is not an int
    else throw new Error("Invalid Input");
    return answer;
  }
  
  /**
   * Convert an sequence of four bytes into a Big Endian integer.
   * @param byteAtAddrPlus0 value of byte with lowest memory address (base address).
   * @param byteAtAddrPlus1 value of byte at base address plus 1.
   * @param byteAtAddrPlus2 value of byte at base address plus 2.
   * @param byteAtAddrPlus3 value of byte at base address plus 3 (highest memory address).
   * @return Big Endian integer formed by these four bytes.
   */
  @Override public int bytesToInteger (byte byteAtAddrPlus0, byte byteAtAddrPlus1, byte byteAtAddrPlus2, byte byteAtAddrPlus3) {

    int a, b, c, d;

    a = ((int)byteAtAddrPlus0 << 24) & 0xFFFFFFFF;
    b = (((int)byteAtAddrPlus1 << 16) & 0xFFFFFF);
    c = ((int)byteAtAddrPlus2 << 8) & 0xFFFF;
    d = (int)byteAtAddrPlus3 & 0xFF;

    return  a + b + c + d;
  }
//    return answer;
//  }
  
  /**
   * Convert a Big Endian integer into an array of 4 bytes organized by memory address.
   * @param  i an Big Endian integer.
   * @return an array of byte where [0] is value of low-address byte of the number etc.
   */
  @Override public byte[] integerToBytes (int i) {
    byte[] bytearray = new byte[4];
      int i1 = (byte) i >> 24;
      bytearray[0] = (byte) i1;
      int i2 = (byte) i >> 16;
      bytearray[0] = (byte) i2;
      int i3 = (byte) i >> 8;
      bytearray[0] = (byte) i3;
      int i4 = (byte) i;
      bytearray[0] = (byte) i4;
      return bytearray;
  }
  
  /**
   * Fetch a sequence of bytes from memory.
   * @param address address of the first byte to fetch.
   * @param length  number of bytes to fetch.
   * @throws InvalidAddressException  if any address in the range address to address+length-1 is invalid.
   * @return an array of byte where [0] is memory value at address, [1] is memory value at address+1 etc.
   */
  @Override protected byte[] get (int address, int length) throws InvalidAddressException {
    byte[] answer = new byte[length];
    if(length() < length){
      throw new InvalidAddressException();
    }
    for (int j = 0; j < length(); j++){
      if ((int) mem[j] == address){
        for (int i = 0; i < length; i++) {
          answer[i] = mem[j+i];
        }
        return answer;
      }

    } throw new InvalidAddressException();
  }
  
  /**
   * Store a sequence of bytes into memory.
   * @param  address                  address of the first byte in memory to receive the specified value.
   * @param  value                    an array of byte values to store in memory at the specified address.
   * @throws InvalidAddressException  if any address in the range address to address+value.length-1 is invalid.
   */
  @Override protected void set (int address, byte[] value) throws InvalidAddressException {
    if (value.length > length()) {
      throw new InvalidAddressException();
    }
    for (int i = 0; i < length(); i++) {
      if ((int) mem[i] == address){
        for (int j = i; j < value.length+i; j++) {
          mem[j] = value[j-i];
        }
        return;
      }
    } throw new InvalidAddressException();
  }
  
  /**
   * Determine the size of memory.
   * @return the number of bytes allocated to this memory.
   */
  @Override public int length () {
    return mem.length;
  }
}
