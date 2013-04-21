package org.penny_craal.icosamapper.map;

/**
 *
 * @author Ville Jokela
 */
public class GreyscaleLR extends LayerRenderer {
    public GreyscaleLR() {
    }

    @Override
    public int renderByte(byte value) {
        return encodeAsInt(toInt(value), toInt(value), toInt(value));
    }

    @Override
    public String getType() {
        return "Grayscale";
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof GreyscaleLR;
    }
    
    @Override
    public int hashCode() {
        return 144;     // all GreyscaleLRs are identical, so a random number is sufficient.
    }
}
