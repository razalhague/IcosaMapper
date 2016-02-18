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
 * An object that contains TriangleContainers.
 * @author Ville Jokela
 */
public interface TriangleContainer {
    /**
     * Returns the size of the container. Must remain constant throughout the objects life.
     * @return the size of the container.
     */
    int getSize();
    
    /**
     * Checks whether the provided Path points to an existing triangle.
     * @param p the Path to be tested
     * @return <code>true</code> if the path is valid, <code>false</code> otherwise
     */
    boolean isValidPath(Path p);
    
    /**
     * Gets the element addressed by the Path.
     * @param p the Path of the element
     * @return the value of the element
     */
    byte getElement(Path p);

    /**
     * Sets the element addressed by the Path.
     * @param p the Path of the element
     * @param value the value to be set
     * @throws InvalidPathException when the given Path is invalid
     */
    void setElement(Path p, byte value) throws InvalidPathException;
    
    /**
     * Divides the element addressed by the Path.
     * @param p the Path of the element
     * @throws InvalidPathException when the given Path is invalid
     */
    void divide(Path p) throws InvalidPathException;
    
    /**
     * Unites the element addressed by the Path (removes the children).
     * @param p the Path to the element
     * @throws InvalidPathException when the given Path is invalid
     */
    void unite(Path p) throws InvalidPathException;
    
    /**
     * Calculates the mean value of all the children of this element.
     * @return the mean
     */
    byte getMeanValue();
    
    /**
     * Renders the values of the container at the given depth. Elements that do not exist have the value of their parent.
     * @param depth the depth of the render
     * @return the values of the children at the specified depth.
     */
    byte[] render(int depth);

    byte[] render(Path zoom, int depth) throws InvalidPathException;

    /**
     * Returns an independent copy of this object. Not using Object.clone(), because it's a mess.
     * @return the copy
     */
    TriangleContainer copy();
}