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

import java.io.*;

/**
 * Serializes using Java's own serialization mechanism.
 * @author Ville Jokela
 */
public class JavaMapSerializer implements MapSerializer {
    @Override
    public void serialize(Map map, OutputStream dst) throws DAException {
        try (ObjectOutputStream oos = new ObjectOutputStream(dst)) {
            oos.writeObject(map);
        } catch (IOException e) {
            throw new DAException("Serialization failed", e);
        }
    }

    @Override
    public Map deserialize(InputStream src) throws DAException {
        try (ObjectInputStream ois = new ObjectInputStream(src)) {
            return (Map) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DAException("Deserialization failed", e);
        }
    }
}
