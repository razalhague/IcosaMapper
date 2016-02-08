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

package org.penny_craal.icosamapper.map.layerrenderers;

import java.util.Collections;
import java.util.Map;

import org.penny_craal.icosamapper.map.Util;
import org.penny_craal.icosamapper.map.layerrenderers.variabletypes.VariableType;

/**
 * A LayerRenderer that simply interprets the byte value as a shade of grey.
 * @author Ville Jokela
 */
public class Greyscale extends LayerRendererHelper {
    private static final long serialVersionUID = 1L;

    public static final String type = "Greyscale";

    /**
     * Constructs this object.
     */
    public Greyscale() {
    }

    @Override
    public int renderByte(byte value) {
        return Util.encodeAsInt(Util.toInt(value), Util.toInt(value), Util.toInt(value));
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Map<String, VariableType> getVariables() {
        return Collections.emptyMap();
    }

    @Override
    public Object getValue(String variableName) {
        throw new RuntimeException("Greyscale does not support any configurables");
    }

    @Override
    public void setCheckedVariable(String variableName, Object value) {
        throw new RuntimeException("Greyscale does not support any configurables");
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Greyscale;    // all GreyscaleLRs are identical
    }
    
    @Override
    public int hashCode() {
        return 144;     // all GreyscaleLRs are identical, so a pre-determined number is sufficient.
    }
}
