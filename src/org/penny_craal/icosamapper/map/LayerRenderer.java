package org.penny_craal.icosamapper.map;

import java.io.Serializable;

/**
 *
 * @author Ville Jokela
 */
public abstract class LayerRenderer implements Serializable {
    /**
     * Renders a byte value into a colour.
     * @param value the byte value to be rendered.
     * @return an RGB value encoded as an integer. Bits: 0-7: blue, 8-15: green, 16-23: red.
     */
    abstract public int renderByte(byte value);
    abstract public String getType();
    
    protected static int encodeAsInt(int r, int g, int b) {
        return b | g << 8 | r << 16;
    }
    
    /**
     * Interpret the byte value as unsigned
     * @param value
     * @return 
     */
    protected static int toInt(byte value) {
        return value & 0xFF;
    }
}