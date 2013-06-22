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

import javax.swing.SwingUtilities;

import org.penny_craal.icosamapper.ui.UI;
import org.penny_craal.icosamapper.ui.events.IMEvent;
import org.penny_craal.icosamapper.ui.events.IMEventListener;

/**
 * The main class for the program.
 * @author Ville Jokela
 * @author James Pearce
 */
public class IcosaMapper {
    /**
     * The main method for the program.
     * @param args ignored
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UI ui = new UI();
                ui.setExtendedState(UI.MAXIMIZED_BOTH);
                ui.setVisible(true);
                Listener listener = new Listener();
                ui.addIMEventListener(listener);
            }
        });
    }
    
    private static class Listener implements IMEventListener {
        @Override
        public void actionPerformed(IMEvent ime) {
        }
    }
}