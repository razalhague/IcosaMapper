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
public class IcosahedronTest {
    private Icosahedron ih;
    
    public IcosahedronTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ih = new Icosahedron((byte)0);
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
        assertEquals(true, ih.isValidPath(new AccessPath(apa1)));
        assertEquals(false, ih.isValidPath(new AccessPath(apa2)));
    }

    /**
     * Test of access method, of class Icosahedron.
     */
    @Test
    public void testAccess() throws BadPathException {
        System.out.println("access");
        byte[] apa = {1};
        assertEquals(0, ih.access(new AccessPath(apa)));
    }

    /**
     * Test of subdivide method, of class Icosahedron.
     */
    @Test
    public void testSubdivide() throws BadPathException {
        // TODO: test subdivide all
        System.out.println("subdivide");
        byte[] apa1 = {1};
        byte[] apa2 = {1, 7};
        ih.subdivide(new AccessPath(apa1));
        assertEquals(0, ih.access(new AccessPath(apa2)));
    }

    /**
     * Test of setAtPath method, of class Icosahedron.
     */
    @Test
    public void testSetAtPath() throws BadPathException {
        System.out.println("setAtPath");
        byte[] apa1 = {1};
        byte[] apa2 = {1, 7};
        AccessPath ap1 = new AccessPath(apa1);
        AccessPath ap2 = new AccessPath(apa2);
        ih.subdivide(ap1);
        ih.setAtPath(ap2, (byte)100);
        assertEquals(100, ih.access(ap2));
    }
}
