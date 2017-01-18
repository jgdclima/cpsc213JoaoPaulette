import static java.lang.System.out;

public class Endianness {

  public static int bigEndianValue (Byte[] mem) {
    int result = 0;
    
  	int a, b, c, d;
     
	a = ((mem[0].intValue() << 24) & 0xFFFFFFFF);
	b = ((mem[1].intValue() << 16) & 0xFFFFFF);
	c = ((mem[2].intValue() << 8) & 0xFFFF);
	d = mem[3].intValue() & 0xFF;

    	return result = a + b + c + d;
  }


  
  public static int littleEndianValue (Byte[] mem) {
    int result = 0;
    Byte[] list = new Byte[4];

    int counter = mem.length-1;
    for (Byte j: mem) {
      list[counter] = j;
      counter--;
    }
    for (Byte i: list) {


    }

    return result;
  }
  
  public static void main (String[] args) {
    Byte mem[] = new Byte[4];
    try {
      for (int i=0; i<4; i++)
        mem [i] = Integer.valueOf (args[i], 16) .byteValue();
    } catch (Exception e) {
      out.printf ("usage: java Endianness n0 n1 n2 n3\n");
      out.printf ("where: n0..n3 are byte values in memory at addresses 0..3 respectively, in hex (no 0x).\n");
      return;
    }
  
    int bi = bigEndianValue    (mem);
    int li = littleEndianValue (mem);
    
    out.printf ("Memory Contents\n");
    out.printf ("  Addr   Value\n");
    for (int i=0; i<4; i++)
      out.printf ("  %3d:   0x%-5x\n", i, mem[i]);
    out.printf ("The big    endian integer value at address 0 is %d\n", bi);
    out.printf ("The little endian integer value at address 0 is %d\n", li);
  }

}
