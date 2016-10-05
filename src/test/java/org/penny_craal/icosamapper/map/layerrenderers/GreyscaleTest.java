package org.penny_craal.icosamapper.map.layerrenderers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Ville Jokela
 */
public class GreyscaleTest {
    private Greyscale greyscale;

    @BeforeMethod
    public void setUp() {
        greyscale = new Greyscale();
    }

    @AfterMethod
    public void tearDown() {
        greyscale = null;
    }

    @Test
    public void testRenderByte() {
        byte min = 0, max = -1;
        assertEquals(greyscale.renderByte(min), 0x00000000);
        assertEquals(greyscale.renderByte(max), 0x00FFFFFF);
    }

    @Test
    public void testGetType() {
        assertEquals(greyscale.getType(), Greyscale.type);
    }

    @Test
    public void testGetVariables() {
        assertTrue(greyscale.getVariables().isEmpty());
    }

    @Test
    public void testGetValue() {
        try {
            greyscale.getValue("asdasd");
        } catch (RuntimeException rte) {
            return;
        }
        fail();
    }

    @Test
    public void testSetCheckedVariable() {
        try {
            greyscale.setCheckedVariable("asdasd", null);
        } catch (RuntimeException rte) {
            return;
        }
        fail();
    }

    @Test
    public void testEquals() {
        assertEquals(greyscale, new Greyscale());
        assertNotEquals(greyscale, null);
        assertNotEquals(greyscale, "sadasd");
    }

    @Test
    public void testCopy() {
        assertEquals(greyscale.copy(), greyscale);
    }
}