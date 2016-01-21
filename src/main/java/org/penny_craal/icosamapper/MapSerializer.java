/* IcosaMapper - an rpg map editor based on equilateral triangles that form an icosahedron
 * Copyright (C) 2016  Ville Jokela
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

package org.penny_craal.icosamapper;

import org.penny_craal.icosamapper.map.Map;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Ville Jokela
 */
public interface MapSerializer {
    /**
     * Serializes a Map into an OutputStream
     * @param map Map to serialize
     * @param dst destination stream
     */
    void serialize(Map map, OutputStream dst) throws DAException;

    /**
     * Deserializes a Map from an InputStream
     * @param src stream to deserialize from
     * @return
     */
    Map deserialize(InputStream src) throws DAException;
}
