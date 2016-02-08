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

package org.penny_craal.icosamapper.map.layerrenderers.variabletypes;

/**
 * @author Ville Jokela
 */
public interface VariableType {
    /**
     * Gets the name of this type.
     * @return  name of this type
     */
    String getName();

    /**
     * Tests if the object is valid for this variable type.
     * @param object    object to be tested
     * @return          is the object valid
     */
    boolean isValid(Object object);
}
