/* IcosaMapper - an rpg map editor based on equilateral triangles that form an icosahedron
 * Copyright (C) 2013  Ville Jokela, James Pearce
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
 *
 * @author Ville Jokela
 */
public class Util {
    /**
     * A helper method for encoding three values as one int.
     * @param r red component
     * @param g green component
     * @param b blue component
     * @return an RGB value encoded as an integer. Bits: 0-7: blue, 8-15: green, 16-23: red.
     */
    public static int encodeAsInt(int r, int g, int b) {
        return b | g << 8 | r << 16;
    }
    
    /**
     * Interpret the byte value as unsigned.
     * @param value the byte value
     * @return the value of the byte as an int, as if the byte was unsigned
     */
    public static int toInt(byte value) {
        return value & 0xFF;
    }
}