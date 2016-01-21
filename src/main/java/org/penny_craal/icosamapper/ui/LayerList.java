/* IcosaMapper - an rpg map editor based on equilateral triangles that form an icosahedron
 * Copyright (C) 2013, 2016  Ville Jokela
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

package org.penny_craal.icosamapper.ui;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.penny_craal.icosamapper.ui.events.IMEvent;
import org.penny_craal.icosamapper.ui.events.IMEventHelper;
import org.penny_craal.icosamapper.ui.events.IMEventListener;
import org.penny_craal.icosamapper.ui.events.IMEventSource;
import org.penny_craal.icosamapper.ui.events.LayerSelected;

/**
 * Lists the layers in a Map.
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class LayerList extends JList<String> implements IMEventSource {
    LayerList(LayerListModel layerListModel) {
        super(layerListModel);
        Listener listener = new Listener();
        addMouseListener(listener);
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    @Override
    public void addIMEventListener(IMEventListener imel) {
        IMEventHelper.addListener(listenerList, imel);
    }
    
    @Override
    public void removeIMEventListener(IMEventListener imel) {
        IMEventHelper.removeListener(listenerList, imel);
    }
    
    protected void fireEvent(IMEvent ime) {
        IMEventHelper.fireEvent(listenerList, ime);
    }
    
    private class Listener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent me) {
            if (me.getClickCount() <= 1) {
                return;
            }
            Rectangle r = getCellBounds(0, getLastVisibleIndex());
            if (r == null) {
                throw new RuntimeException("getCellBounds() returned null");
            }
            if (!r.contains(me.getPoint())) {
                return;
            }
            
            fireEvent(new LayerSelected(LayerList.this, LayerList.this.getSelectedValue()));
        }
    }
}