package arch.sm213.machine.student;

import static org.junit.Assert.*;
import machine.AbstractMainMemory.InvalidAddressException;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by jgdcl on 13/01/17.
 */
public class MainMemoryTest {
    MainMemory mainMemory;



    @Before
    public void setUp() throws Exception {
        mainMemory = new MainMemory(10);
    }

    private void loadMemory(MainMemory mem, int numBytes) throws InvalidAddressException {
        byte[] bytes = new byte[numBytes];
        bytes[0] = new Byte("16");
        bytes[1] = new Byte("10");
        bytes[2] = new Byte("31");
        bytes[3] = new Byte("32");
        bytes[4] = new Byte("11");
        bytes[5] = new Byte("22");
        bytes[6] = new Byte("15");
        bytes[7] = new Byte("7");
        bytes[8] = new Byte("21");
        bytes[9] = new Byte("00");
        mem.set(0, bytes);

    }


    @Test
    public void testIsAccessAligned() throws Exception {
        loadMemory(mainMemory, 10);
        //Shouldn't be aligned
        assertFalse(mainMemory.isAccessAligned(16, 10));
        System.out.print(mainMemory.isAccessAligned(16, 10));
        //Should be aligned?
        System.out.print(mainMemory.isAccessAligned(10, 5));
        assertTrue(mainMemory.isAccessAligned(10, 5));


    }

    @Test
    public void testBytesToInteger() throws Exception {
        byte[] b0001 = {00, 00, 00, 01};
        byte[] b1000 = {10, 00, 00, 00};
        byte[] b1234 = {10, 20, 30, 40};
        byte[] b0500 = {00, 50, 00, 00};

        //Bytes to Integer Test
        assertEquals(mainMemory.bytesToInteger(b0001[0], b0001[1], b0001[2], b0001[3]), 1);
        assertEquals(mainMemory.bytesToInteger(b1000[0], b1000[1], b1000[2], b1000[3]), 268435456);
        assertEquals(mainMemory.bytesToInteger(b1234[0], b1234[1], b1234[2], b1234[3]), 270544960);
        assertEquals(mainMemory.bytesToInteger(b0500[0], b0500[1], b0500[2], b0500[3]), 5242880);


        assertEquals(mainMemory.integerToBytes(1), b0001);
        assertEquals(mainMemory.integerToBytes(268435456), b1000);
        assertEquals(mainMemory.integerToBytes(270544960), b1234);
        assertEquals(mainMemory.integerToBytes(5242880), b0500);
    }

    // tests get() method at start of array and going 2 bytes in
    @Test
    public void testGet1() throws Exception {
        loadMemory(mainMemory, 10);
        assertEquals(16, (int) mainMemory.get(16, 2)[0]);
        assertEquals(10, (int) mainMemory.get(16, 2)[1]);
    }

    // tests get() method with address in the middle of the array and length reaching the end of the memory
    @Test
    public void testGet2() throws Exception {
        loadMemory(mainMemory, 10);
        assertEquals(32, (int) mainMemory.get(32, 7)[0]);
        assertEquals(7, (int) mainMemory.get(32, 7)[4]);
        assertEquals(0, (int) mainMemory.get(32, 7)[6]);

    }

    // tests get() method - ensures that an exception is thrown if address not present in memory
    @Test
    public void testGetError1() throws Exception {
        loadMemory(mainMemory, 10);
        try {
            mainMemory.get(255, 1);
            throw new Error("test not failed: exception not thrown");
        } catch (InvalidAddressException e) {
            System.out.println("invalid address exception caught - address not found");
        }
    }

    // tests get() method - ensures that an exception is thrown if byte length greater than mem length
    @Test
    public void testGetError2() throws Exception {
        loadMemory(mainMemory, 10);
        try {
            mainMemory.get(0, 300);
            throw new Error("test not failed: exception not thrown");
        } catch (InvalidAddressException e) {
            System.out.println("invalid address exception caught - byte length larger than mem length");
        }
    }

    // tests get() method - ensures that an exception is thrown if address not present in memory
    @Test
    public void testSetError1() throws Exception {
        byte[] bytes = {0, 0, 0, 0};
        loadMemory(mainMemory, 10);
        try {
            mainMemory.set(255, bytes);
            throw new Error("test not failed: exception not thrown");
        } catch (InvalidAddressException e) {
            System.out.println("invalid address exception caught");
        }
    }

    // tests get() method - ensures that an exception is thrown if byte length greater than mem length
    @Test
    public void testSetError2() throws Exception {
        byte[] bytes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        loadMemory(mainMemory, 10);
        try {
            mainMemory.set(7, bytes);
            throw new Error("test not failed: exception not thrown");
        } catch (InvalidAddressException e) {
            System.out.println("invalid address exception caught - byte length larger than mem length");
        }
    }

}
