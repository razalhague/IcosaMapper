package org.penny_craal.icosamapper.map;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Ville Jokela
 */
public class UtilTest {

    @Test
    public void testEncodeAsInt() {
        assertEquals(Util.encodeAsInt(0x12, 0x56, 0xAC), 0x001256AC);
    }

    @Test
    public void testToInt() throws Exception {
        byte max = -1;
        assertEquals(Util.toInt(max), 255);
    }
}