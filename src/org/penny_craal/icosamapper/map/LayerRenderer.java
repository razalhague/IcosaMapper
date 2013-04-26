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
 * A translator between a byte value and a colour.
 * @author Ville Jokela
 */
public interface LayerRenderer {
    /**
     * Transforms a byte value into a colour (encoded as an int).
     * @param value the byte value to be rendered
     * @return an RGB value encoded as an integer. Bits: 0-7: blue, 8-15: green, 16-23: red.
     */
    abstract public int renderByte(byte value);
    
    /**
     * Transforms an array of values into an array of colours.
     * @param values the values to be rendered
     * @return an array of RGB values encoded as integers. Bits: 0-7: blue, 8-15: green, 16-23: red.
     */
    abstract public int[] renderArray(byte[] values);
    
    /**
     * Returns the type of this renderer.
     * @return the type of this renderer
     */
    abstract public String getType();
}
