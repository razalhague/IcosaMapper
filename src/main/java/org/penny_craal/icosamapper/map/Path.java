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

import java.util.ArrayList;
import java.util.List;

/**
 * A path to a specific triangle. A Path object is immutable.
 * @author Ville Jokela
 */
public class Path {
    private final byte[] path;
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
     * @param path  list of bytes that defines the path
     */
    public Path(List<Byte> path) {
        byte[] bytePath = new byte[path.size()];
        for (int i = 0; i < path.size(); i++) {
            bytePath[i] = path.get(i);
        }
        this.path =  bytePath;
        this.index = 0;
    }
    
    /**
     * Constructs a Path. This is essentially an optimization for the rest() method. Probably unnecessary, too.
     * @param path      array of bytes that defines the path
     * @param index     index in that array that marks the first element in the path. all elements before this are ignored.
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
        if (length() == 1) {
            throw new RuntimeException("Trying to do .rest() on a Path of length one");
        }
        return new Path(path, index + 1);
    }

    /**
     * Returns a new Path that is this path combined with the other
     * @param that  path to append onto this path
     * @return      combined path
     */
    public Path append(Path that) {
        List<Byte> fullPath = new ArrayList<>(this.length() + that.length());
        for (int i = this.index; i < this.path.length; i++) {
            fullPath.add(this.path[i]);
        }
        for (int i = that.index; i < that.path.length; i++) {
            fullPath.add(that.path[i]);
        }

        return new Path(fullPath);
    }

    /**
     * Returns a new Path without the final element.
     * @return a new path that lacks the final element.
     */
    public Path clipped() {
        if (length() == 1) {
            return null;
        }
        List<Byte> newPath = new ArrayList<>(length() - 1);
        for (int i = index; i < path.length - 1; i++) {
            newPath.add(path[i]);
        }

        return new Path(newPath);
    }

    /**
     * Returns a string representation of the Path.
     * @return  as described above
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        sb.append("CUR: ");

        for (int i = index; i < path.length; i++) {
            sb.append(path[i]);
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }");
        
        return sb.toString();
    }
}