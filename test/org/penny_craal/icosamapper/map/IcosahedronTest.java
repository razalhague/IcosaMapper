/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.penny_craal.icosamapper.map;

import org.penny_craal.icosamapper.map.AccessPath;
import org.penny_craal.icosamapper.map.Icosahedron;
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
     * Test of access method, of class Icosahedron.
     */
    @Test
    public void testAccess() {
        System.out.println("access");
        byte[] apa = {1};
        AccessPath ap = new AccessPath(apa);
        assertEquals(0, ih.access(ap));
    }

    /**
     * Test of subdivide method, of class Icosahedron.
     */
    @Test
    public void testSubdivide() {
        System.out.println("subdivide");
        byte[] apa1 = {1};
        byte[] apa2 = {1, 7};
        AccessPath ap1 = new AccessPath(apa1);
        AccessPath ap2 = new AccessPath(apa2);
        ih.subdivide(ap1);
        assertEquals(0, ih.access(ap2));
    }

    /**
     * Test of setAtPath method, of class Icosahedron.
     */
    @Test
    public void testSetAtPath() {
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
