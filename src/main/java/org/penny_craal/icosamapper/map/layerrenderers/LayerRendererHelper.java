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

import java.io.Serializable;
import java.util.Map;

import org.penny_craal.icosamapper.map.layerrenderers.variabletypes.VariableType;

/**
 * An abstract helper class for implementations of LayerRenderer.
 * @author Ville Jokela
 */
public abstract class LayerRendererHelper implements LayerRenderer, Serializable {
    @Override
    public int[] renderArray(byte[] values) {
        int[] rendered = new int[values.length];
        
        for (int i = 0; i < rendered.length; i++) {
            rendered[i] = renderByte(values[i]);
        }
        
        return rendered;
    }

    @Override
    public void setVariable(String variableName, Object value) {
        Map<String, VariableType> variables = getVariables();

        if (variables.containsKey(variableName)) {
            if (variables.get(variableName).isValid(value)) {
                setCheckedVariable(variableName, value);
            }
        } else {
            throw new RuntimeException("tried to set variable '" + variableName + "' to: " + value);
        }
    }

    abstract void setCheckedVariable(String variableName, Object value);
}