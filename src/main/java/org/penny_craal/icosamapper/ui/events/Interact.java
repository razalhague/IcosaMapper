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

import org.penny_craal.icosamapper.map.Path;

/**
 *
 * @author Ville Jokela
 */
public class Interact extends IMEvent {
    private static final long serialVersionUID = 1L;
    public final Path path;
    public final boolean isPrimary;

    public Interact(Object source, Path path, boolean isPrimary) {
        super(source, EventType.interact);
        this.path = path;
        this.isPrimary = isPrimary;
    }

    @Override
    public String toString() {
        return "Interact: " + path + ", " + (isPrimary ? "primary button" : "secondary button");
    }
}