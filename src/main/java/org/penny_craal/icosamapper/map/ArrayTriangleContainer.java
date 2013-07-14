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

import java.io.Serializable;

/**
 * A TriangleContainer that uses arrays to store the values of its children's values within itself. Only works with sizes of 9 and 20.
 * @author Ville Jokela
 */
abstract public class ArrayTriangleContainer implements TriangleContainer, Serializable {
    private byte[] vals;                // values
    private TriangleContainer[] tris;   // triangles
    
    public ArrayTriangleContainer(byte init) {
        if (getSize() != 9 && getSize() != 20) {
            throw new RuntimeException("ArrayTriangleContainer only works for containers of size 9 and 20");
        }
        
        vals = new byte[getSize()];
        tris = null;
        
        for (int i = 0; i < vals.length; i++) {
            vals[i] = init;
        }
    }

    @Override
    public boolean isValidPath(Path p) {
        if (p.length() <= 1) {
            return true;
        } else if (tris != null && tris[p.first()] != null) {
            return tris[p.first()].isValidPath(p.rest());
        } else {
            return false;
        }
    }
    
    @Override
    public byte getElement(Path p) throws InvalidPathException {
        if (p.length() == 0) {
            return this.getMeanValue();
        } else if (p.length() == 1) {
            return vals[p.first()];
        } else if (tris != null || tris[p.first()] != null) {
            return tris[p.first()].getElement(p.rest());
        } else {
            throw new InvalidPathException(p);
        }
    }

    @Override
    public void setElement(Path ap, byte val) throws InvalidPathException {
        if (ap.length() == 1) {
            vals[ap.first()] = val;
        } else if (tris != null && tris[ap.first()] != null) {
            tris[ap.first()].setElement(ap.rest(), val);
            vals[ap.first()] = tris[ap.first()].getMeanValue();
        } else {
            throw new InvalidPathException(ap);
        }
    }
    
    @Override
    public void divide(Path p) throws InvalidPathException {
        if (p.length() == 1) {
            if (tris == null) {
                tris = new TriangleContainer[getSize()];
                for (int i = 0; i < tris.length; i++) {
                    tris[i] = null;
                }
                tris[p.first()] = new ArrayTriangle(vals[p.first()]);
            } else if (tris[p.first()] != null) {
                for (int i = 0; i < tris.length; i++) {
                    byte[] path = { (byte) i };
                    tris[p.first()].divide(new Path(path));
                }
            } else {
                tris[p.first()] = new ArrayTriangle(vals[p.first()]);
            }
        } else if (tris != null){
            tris[p.first()].divide(p.rest());
        } else {
            throw new InvalidPathException(p);
        }
    }
    
    @Override
    public void unite(Path p) throws InvalidPathException {
        if (p.length() == 0) {
            throw new InvalidPathException(p, "cannot unite self");
        } else if (p.length() == 1 && tris != null && tris[p.first()] != null) {
            tris[p.first()] = null;
        } else {
            throw new InvalidPathException(p);
        }
    }
    
    @Override
    public byte getMeanValue() {
        int n = 0;
        
        for (byte v: vals) {
            n += v;
        }
        
        return (byte) (n/getSize());
    }
    
    @Override
    public byte[] render(int depth) {
        if (depth == 1) {
            return vals;
        }
        byte[] values = new byte[getSizeAtDepth(depth)];
        byte[] subVals = null;
        for (int i = 0; i < getSize(); i++) {
            if (tris != null && tris[i] != null) {
                subVals = tris[i].render(depth - 1);
            }
            for (int j = 0; j < values.length/getSize(); j++) {
                if (tris == null || tris[i] == null) {
                    values[values.length/getSize()*i + j] = vals[i];
                } else /* tris[i] != null */ {
                    values[values.length/getSize()*i + j] = subVals[j];
                }
            }
        }
        
        return values;
    }
    
    private int getSizeAtDepth(int depth) {
        // TODO: recursive calculation?
        if (getSize() == 20) {
            return (int) (20 * Math.pow(9, depth-1));
        } else /* getSize() == 9 */ {
            return (int) Math.pow(9, depth);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        for (int i = 0; i < vals.length; i++) {
            sb.append(vals[i] & 0xFF);
            if (tris != null) {
                if (tris[i] != null) {
                    sb.append(": ");
                    sb.append(tris[i].toString());
                }
            }
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }");
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ArrayTriangleContainer)) {
            if (other instanceof TriangleContainer) {
                // TODO: equality test for when the other object is a TC, but not an ATC
                throw new UnsupportedOperationException(
                        "Comparing with TriangleContainers other than ArrayTriangleContainers not implemented yet"
                );
            } else {
                return false;
            }
        }
        final ArrayTriangleContainer that = (ArrayTriangleContainer) other;
        if (this.getSize() != that.getSize()) {
            return false;
        }
        if ((this.tris == null) != (that.tris == null)) {       // either both must be null, or both must be non-null
            return false;
        }
        for (int i = 0; i < this.getSize(); i++) {
            if (this.vals[i] != that.vals[i]) {
                return false;
            }
            // it is established higher up that if this.tris is not null, neither is that.tris
            if (this.tris != null) {
                if (this.tris[i] != null && !this.tris[i].equals(that.tris[i])) {
                    return false;
                } else if (this.tris[i] == null && that.tris[i] != null) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = 89;
        for (byte v: vals) {
            result = 37 * result + (int) v;
        }
        if (tris != null) {
            for (TriangleContainer tc: tris) {
                if (tc != null) {
                    result = 37 * result + tc.hashCode();
                } else {
                    result = 37 * result;
                }
            }
        }
        
        return result;
    }
}