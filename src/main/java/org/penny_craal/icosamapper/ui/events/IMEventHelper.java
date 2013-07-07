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

import javax.swing.event.EventListenerList;

/**
 *
 * @author Ville Jokela
 */
public abstract class IMEventHelper {
    private IMEventHelper() {}
    
    public static void addListener(EventListenerList ell, IMEventListener imel) {
        ell.add(IMEventListener.class, imel);
    }
    
    public static void removeListener(EventListenerList ell, IMEventListener imel) {
        ell.remove(IMEventListener.class, imel);
    }
    
    public static void fireEvent(EventListenerList ell, IMEvent ime) {
        Object[] listeners = ell.getListenerList();     // Guaranteed to return a non-null array
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2)
            if (listeners[i] == IMEventListener.class)
                ((IMEventListener) listeners[i + 1]).handleEvent(ime);
    }
}