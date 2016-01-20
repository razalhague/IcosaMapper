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

package org.penny_craal.icosamapper;

import java.io.*;

import org.penny_craal.icosamapper.map.Map;

/**
 * An implementation of MapDAO using the file system.
 * @author Ville Jokela
 */
public class FileMapDAO implements MapDAO {
    private File file;
    
    /**
     * Constructs the DAO
     * @param file  file to save to
     */
    public FileMapDAO(File file) {
        this.file = file;
    }

    @Override
    public void save(Map map, MapSerializer serializer) throws DAException {
        try (OutputStream dst = new FileOutputStream(file)) {
            serializer.serialize(map, dst);
        } catch (IOException e) {
            throw new DAException("Serialization failed", e);
        }
    }

    @Override
    public Map load(MapSerializer serializer) throws DAException {
        try (InputStream src = new FileInputStream(file)) {
            return serializer.deserialize(src);
        } catch (IOException e) {
            throw new DAException("Deserialization failed", e);
        }
    }
}