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

package org.penny_craal.icosamapper.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * ListModel for the layer list.
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class LayerListModel extends AbstractListModel<String> {
    List<String> layers;

    public LayerListModel(Collection<String> layers) {
        this.layers = new ArrayList<>(layers);
    }

    public LayerListModel() {
        this(Collections.<String>emptyList());
    }

    public void addElement(String element) throws NullPointerException, IllegalArgumentException {
        if (canAdd(element)) {
            layers.add(element);
        }
        fireIntervalAdded(this, layers.indexOf(element), layers.indexOf(element));
    }

    public void addAll(Collection<String> cs) {
        int length = layers.size();
        layers.addAll(cs);
        fireIntervalAdded(this, length, cs.size());
    }

    public void rename(String oldName, String newName) throws NullPointerException, IllegalArgumentException {
        if (canAdd(newName) && layers.contains(oldName)) {
            layers.set(layers.indexOf(oldName), newName);
        } else {    // TODO: discriminate between bad newName and non-existent oldName
            throw new IllegalArgumentException("Cannot rename " + oldName + " to " + newName);
        }
        fireContentsChanged(this, layers.indexOf(newName), layers.indexOf(newName));
    }

    public void removeElement(String element) {
        int index = layers.indexOf(element);
        layers.remove(element);
        fireIntervalRemoved(this, index, index);
    }

    public void removeAll() {
        int length = layers.size();
        layers.clear();
        fireIntervalRemoved(this, 0, length);
    }

    public boolean canAdd(String element) {
        if (element == null) {
            return false; //throw new NullPointerException("Cannot add a null element");
        }
        if (element.trim().isEmpty()) {
            return false; //throw new IllegalArgumentException("Cannot add an empty string");
        } else if (layers.contains(element)) {
            return false; //throw new IllegalArgumentException("List already contains " + element);
        }

        return true;
    }

    @Override
    public int getSize() {
        return layers.size();
    }

    @Override
    public String getElementAt(int i) {
        return layers.get(i);
    }
}