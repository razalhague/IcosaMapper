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

package org.penny_craal.icosamapper.ui.events;

/**
 *
 * @author Ville Jokela
 */
public class DuplicateLayer extends IMEvent {
    private static final long serialVersionUID = 1L;
    public final String layerName;

    public DuplicateLayer(Object source, String layerName) {
        super(source, EventType.duplicateLayer);
        this.layerName = layerName;
    }

    @Override
    public String toString() {
        return "DuplicateLayer: " + layerName;
    }
}