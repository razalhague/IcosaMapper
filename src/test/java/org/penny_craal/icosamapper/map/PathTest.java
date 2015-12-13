package org.penny_craal.icosamapper.map;

import junit.framework.TestCase;

/**
 *
 * @author Ville Jokela
 */
public class PathTest extends TestCase {
    public PathTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of first method, of class Path.
     */
    public void testFirst() {
        System.out.println("first");
        byte[] path = {1,2,3,4,5};
        Path instance = new Path(path);
        for (int i = 0; i < path.length; i++, instance = instance.rest()) {
            assertEquals(path[i], instance.first());
        }
    }

    /**
     * Test of length method, of class Path.
     */
    public void testLength() {
        System.out.println("length");
        byte[] path = {1,2,3,4,5};
        Path instance = new Path(path);
        for (int i = 0; i < path.length; i++, instance = instance.rest()) {
            assertEquals(path.length - i, instance.length());
        }
    }

    /**
     * Test of rest method, of class Path.
     */
    public void testRest() {
        System.out.println("rest");
        // the test for first() tests this method as well as it can be tested
        testFirst();
    }

    /**
     * Test of toString method, of class Path.
     */
    public void testToString() {
        System.out.println("toString");
        byte[] path = {1,2,3,4,5};
        Path instance = new Path(path);
        assertEquals("{ CUR: 1, 2, 3, 4, 5 }", instance.toString());
    }
}