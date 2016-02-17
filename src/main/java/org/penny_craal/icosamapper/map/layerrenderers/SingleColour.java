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
import java.util.HashMap;
import java.util.Map;

import org.penny_craal.icosamapper.map.Util;
import org.penny_craal.icosamapper.map.layerrenderers.variabletypes.IntegerType;
import org.penny_craal.icosamapper.map.layerrenderers.variabletypes.VariableType;

/**
 * @author Ville Jokela
 */
public class SingleColour extends LayerRendererHelper {
    private int hue;
    private int saturation;
    private int maxLightness;

    private static final String HUE = "hue";
    private static final String SAT = "saturation";
    private static final String MAX_LIGHT = "maxLightness";
    private static final int minValue = 0;
    private static final int maxValue = 255;
    private static final Map<String, VariableType> variables = Collections.unmodifiableMap(new HashMap<String, VariableType>() {{
        put(HUE, new IntegerType(minValue, maxValue));
        put(SAT, new IntegerType(minValue, maxValue));
        put(MAX_LIGHT, new IntegerType(minValue, maxValue));
    }});

    public static final String type = "SingleColour";

    public SingleColour() {
        this(minValue, maxValue, maxValue/2);
    }

    public SingleColour(int hue, int saturation, int maxLightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.maxLightness = maxLightness;
    }

    protected SingleColour(SingleColour that) {
        this.hue = that.hue;
        this.saturation = that.saturation;
        this.maxLightness = that.maxLightness;
    }

    @Override
    public int renderByte(byte value) {
        double h, s, l, c, x, m, r = 0, g = 0, b = 0, mv = maxValue;
        int hSection;
        l = (Util.toInt(value) / mv) * (maxLightness / (double) maxValue);
        h = hue / mv;
        s = saturation / mv;
        c = (1 - Math.abs(2*l - 1)) * s;
        x = c * (1 - Math.abs(((h*6) % 2) - 1));
        hSection = (int) Math.floor(h * 6);
        switch (hSection) {
            case 0:
                r = c;
                g = x;
                break;
            case 1:
                r = x;
                g = c;
                break;
            case 2:
                g = c;
                b = x;
                break;
            case 3:
                g = x;
                b = c;
                break;
            case 4:
                r = x;
                b = c;
                break;
            case 5:
            case 6:
                r = c;
                b = x;
                break;
            default:
                throw new RuntimeException("error");
        }

        m = l - (c/2);

        return Util.encodeAsInt(
                (int) ((r+m) * 255),
                (int) ((g+m) * 255),
                (int) ((b+m) * 255)
        );
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Map<String, VariableType> getVariables() {
        return variables;
    }

    @Override
    public Object getValue(String variableName) {
        switch (variableName) {
            case HUE:
                return hue;
            case SAT:
                return saturation;
            case MAX_LIGHT:
                return maxLightness;
            default:
                throw new RuntimeException("unrecognized variable name " + variableName);
        }
    }

    @Override
    public void setCheckedVariable(String variableName, Object value) {
        switch (variableName) {
            case HUE:
                hue = (Integer) value;
                break;
            case SAT:
                saturation = (Integer) value;
                break;
            case MAX_LIGHT:
                maxLightness = (Integer) value;
                break;
            default:
                throw new RuntimeException("unrecognized variable name " + variableName + " that passed helper class check, problem in code");
        }
    }

    @Override
    public SingleColour copy() {
        return new SingleColour(this);
    }
}
