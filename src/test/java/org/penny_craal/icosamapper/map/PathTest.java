package org.penny_craal.icosamapper.map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 *
 * @author Ville Jokela
 */
public class PathTest {
    private byte[] path;

    @BeforeMethod
    public void setUp() throws InvalidPathException {
        path = new byte[] {1,2,3,4,5};
    }

    @AfterMethod
    public void tearDown() {
        path = null;
    }
    /**
     * Test of first method, of class Path.
     */
    @Test
    public void testFirst() {
        System.out.println("first");
        Path instance = new Path(path);
        for (int i = 0; i < path.length; i++, instance = instance.rest()) {
            assertEquals(path[i], instance.first());
        }
    }

    /**
     * Test of length method, of class Path.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        Path instance = new Path(path);
        for (int i = 0; i < path.length; i++, instance = instance.rest()) {
            assertEquals(path.length - i, instance.length());
        }
    }

    /**
     * Test of rest method, of class Path.
     */
    @Test
    public void testRest() {
        System.out.println("rest");
        // the test for first() tests this method as well as it can be tested
        testFirst();
    }

    /**
     * Test of toString method, of class Path.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Path instance = new Path(path);
        assertEquals("{ CUR: 1, 2, 3, 4, 5 }", instance.toString());
    }
}