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
        Path instance = new Path(path);
        for (int i = 0; i < path.length; i++) {
            assertEquals(path[i], instance.first());
            if (instance.length() != 1) {
                instance = instance.rest();
            }
        }
    }

    /**
     * Test of length method, of class Path.
     */
    @Test
    public void testLength() {
        Path instance = new Path(path);
        for (int i = 0; i < path.length; i++) {
            assertEquals(path.length - i, instance.length());
            if (instance.length() != 1) {
                instance = instance.rest();
            }
        }
    }

    /**
     * Test of rest method, of class Path.
     */
    @Test
    public void testRest() {
        // the test for first() tests this method as well as it can be tested
        testFirst();
    }

    /**
     * Test of append method, of class Path.
     */
    @Test
    public void testAppend() {
        Path instance = new Path(path);
        instance = instance.append(instance);
        for (int i = 0; i < path.length*2; i++) {
            assertEquals(path[i%path.length], instance.first());
            if (instance.length() != 1) {
                instance = instance.rest();
            }
        }
    }

    /**
     * Test of clipped method, of class Path.
     */
    @Test
    public void testClipped() {
        Path instance = new Path(path);
        for (int i = 1; i < path.length; i++) {
            Path clipped = instance.clipped();
            if (i == path.length) {
                assertNull(clipped);
            } else {
                assertEquals(path.length - i, clipped.length());
                for (int j = 0; j < path.length - i; j++) {
                    assertEquals(path[j], clipped.first());
                    if (clipped.length() != 1) {
                        clipped = clipped.rest();
                    }
                }
            }
            instance = instance.clipped();
        }
    }

    /**
     * Test of toString method, of class Path.
     */
    @Test
    public void testToString() {
        Path instance = new Path(path);
        assertEquals("{ CUR: 1, 2, 3, 4, 5 }", instance.toString());
    }
}