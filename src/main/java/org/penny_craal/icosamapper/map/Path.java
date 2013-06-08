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
 * A path to a specific triangle. A Path object is immutable.
 * @author Ville Jokela
 */
public class Path {
    private final byte [] path;
    private final int index;
    
    /**
     * Constructs a Path from an array of bytes.
     * @param path the array of bytes that defines the path.
     */
    public Path(byte[] path) {
        this.path = path.clone();
        index = 0;
    }
    
    /**
     * Constructs a Path
     * @param path
     * @param index 
     */
    private Path(byte[] path, int index) {
        this.path = path;
        this.index = index;
    }
    
    /**
     * Returns the first element of the Path.
     * @return the first element of the Path.
     */
    public byte first() {
        return path[index];
    }
    
    /**
     * Returns the length of the Path.
     * @return the length of the Path.
     */
    public int length() {
        return path.length - index;
    }
    
    /**
     * Returns a new Path without the first element.
     * @return a Path without the first element.
     */
    public Path rest() {
        return new Path(path, index + 1);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        
        for (int i = 0; i < path.length; i++) {
            if (i == index) {
                sb.append("CUR: ");
            }
            sb.append(path[i]);
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }");
        
        return sb.toString();
    }
}