/* IcosaMapper - an rpg map editor based on equilateral triangles that form an icosahedron
 * Copyright (C) 2013  Ville Jokela, James Pearce
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

import java.util.EventObject;

/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class IMEvent extends EventObject {
    public final EventType type;

    protected IMEvent(Object source, EventType type) {
        super(source);
        this.type = type;
    }

    public enum EventType {
        about,
        colourSelected,
        deleteLayer,
        duplicateLayer,
        exit,
        layerActionWithoutLayer,
        layerRendererChanged,
        layerSelected,
        newLayer,
        newMap,
        openMap,
        opSizeSelected,
        interact,
        renameLayer,
        saveMap,
        saveMapAs,
        toolSelected,
        underlayLayer,
    }
}