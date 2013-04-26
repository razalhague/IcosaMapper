/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.penny_craal.icosamapper.map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ville Jokela
 */
public class ArrayIcosahedronTest {
    private ArrayIcosahedron ih;
    
    public ArrayIcosahedronTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ih = new ArrayIcosahedron((byte)0);
    }
    
    @After
    public void tearDown() {
        ih = null;
    }
    
    /**
     * Test of isValidPath method, of class Icosahedron.
     */
    @Test
    public void testIsValidPath() {
        System.out.println("isValidPath");
        byte[] apa1 = {1};
        byte[] apa2 = {1, 1, 1, 1, 1};
        assertEquals(true, ih.isValidPath(new Path(apa1)));
        assertEquals(false, ih.isValidPath(new Path(apa2)));
    }

    /**
     * Test of access method, of class Icosahedron.
     */
    @Test
    public void testAccess() throws InvalidPathException {
        System.out.println("access");
        byte[] apa = {1};
        assertEquals(0, ih.getElement(new Path(apa)));
    }

    /**
     * Test of subdivide method, of class Icosahedron.
     */
    @Test
    public void testSubdivide() throws InvalidPathException {
        // TODO: test subdivide all
        System.out.println("subdivide");
        byte[] apa1 = {1};
        byte[] apa2 = {1, 7};
        ih.divide(new Path(apa1));
        assertEquals(0, ih.getElement(new Path(apa2)));
    }

    /**
     * Test of setAtPath method, of class Icosahedron.
     */
    @Test
    public void testSetAtPath() throws InvalidPathException {
        System.out.println("setAtPath");
        byte[] apa1 = {1};
        byte[] apa2 = {1, 7};
        Path ap1 = new Path(apa1);
        Path ap2 = new Path(apa2);
        ih.divide(ap1);
        ih.setElement(ap2, (byte)100);
        assertEquals(100, ih.getElement(ap2));
    }
}
