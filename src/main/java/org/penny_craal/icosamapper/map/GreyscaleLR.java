/* IcosaMapper - an rpg map editor based on equilateral triangles that form an icosahedron
 * Copyright (C) 2013  Ville Jokela
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * contact me <ville.jokela@penny-craal.org>
 */

package org.penny_craal.icosamapper.map;

/**
 * A LayerRenderer that simply interprets the byte value as a shade of grey.
 * @author Ville Jokela
 */
public class GreyscaleLR extends LayerRendererHelper {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs this object.
     */
    public GreyscaleLR() {
    }

    @Override
    public int renderByte(byte value) {
        return Util.encodeAsInt(Util.toInt(value), Util.toInt(value), Util.toInt(value));
    }

    @Override
    public String getType() {
        return "Grayscale";
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof GreyscaleLR;    // all GreyscaleLRs are identical
    }
    
    @Override
    public int hashCode() {
        return 144;     // all GreyscaleLRs are identical, so a random number is sufficient.
    }
}
