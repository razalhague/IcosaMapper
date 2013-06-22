package org.penny_craal.icosamapper.map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 *
 * @author Ville Jokela
 */
public class ArrayIcosahedronTest {
    private ArrayIcosahedron ih;
    
    public ArrayIcosahedronTest() {
    }
    
    @BeforeMethod
    public void setUp() {
        ih = new ArrayIcosahedron((byte) 0);
    }
    
    @AfterMethod
    public void tearDown() {
        ih = null;
    }
    
    @DataProvider(name="path-arrays")
    public Object[][] pathArrays() {
        return new Object[][] {
            { new byte[] {1},                true },
            { new byte[] {1, 1, 1, 1, 1},    false },
        };
    }
    
    /**
     * Test of isValidPath method, of class Icosahedron.
     */
    @Test(dataProvider="path-arrays")
    public void isValidPath(byte[] apa, boolean expected) {
        assertEquals(expected, ih.isValidPath(new Path(apa)));
    }

    /**
     * Test of access method, of class Icosahedron.
     */
    @Test(dataProvider="path-arrays")
    public void access(byte[] apa, boolean expectedSuccessful) throws InvalidPathException {
        if (expectedSuccessful)
            assertEquals(0, ih.getElement(new Path(apa)));
    }

    /**
     * Test of subdivide method, of class Icosahedron.
     */
    @Test
    public void subdivide() throws InvalidPathException {
        // TODO: test subdivide all
        byte[] apa1 = {1};
        byte[] apa2 = {1, 7};
        ih.divide(new Path(apa1));
        assertEquals(0, ih.getElement(new Path(apa2)));
    }

    /**
     * Test of setAtPath method, of class Icosahedron.
     */
    @Test
    public void setAtPath() throws InvalidPathException {
        final int value = 100;
        byte[] apa1 = {1};
        byte[] apa2 = {1, 7};
        Path ap1 = new Path(apa1);
        Path ap2 = new Path(apa2);
        ih.divide(ap1);
        ih.setElement(ap2, (byte) value);
        assertEquals(ih.getElement(ap2), value);
    }
}
