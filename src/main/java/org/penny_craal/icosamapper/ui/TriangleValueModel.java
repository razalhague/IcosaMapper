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

import javax.swing.BoundedRangeModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import org.penny_craal.icosamapper.map.Util;

import static org.penny_craal.icosamapper.map.Constants.*;

/**
 * TriangleValueModel presents two views (BoundedRangeModel and SpinnerModel) of one byte value.
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class TriangleValueModel  {
    private byte value;
    // for some reason JSpinner and JSlider insist on sending two events whenever the value is changed.
    // oldValue is used to filter
    private byte oldValue;
    private EventListenerList ell;
    private BRM brm;
    private SM sm;
    
    public TriangleValueModel(byte value) {
        this.value = value;
        oldValue = value;
        ell = new EventListenerList();
        brm = new BRM();
        sm = new SM();
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        oldValue = this.value;
        this.value = value;
        if (oldValue != value)
            fireStateChanged();
    }

    public BRM getBoundedRangeModel() {
        return brm;
    }

    public SM getSpinnerModel() {
        return sm;
    }
    
    public void addChangeListener(ChangeListener cl) {
        ell.add(ChangeListener.class, cl);
    }

    public void removeChangeListener(ChangeListener cl) {
        ell.remove(ChangeListener.class, cl);
    }

    private void fireStateChanged() {
        brm.fireStateChanged();
        sm.fireStateChanged();
        ChangeEvent ce = new ChangeEvent(TriangleValueModel.this);
        Object[] listeners = ell.getListenerList();     // Guaranteed to return a non-null array
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                ((ChangeListener) listeners[i + 1]).stateChanged(ce);
            }
        }
    }
    
    public class BRM implements BoundedRangeModel {
        private EventListenerList ell;
        private boolean valueIsAdjusting;
        
        public BRM() {
            ell = new EventListenerList();
            valueIsAdjusting = false;
        }
        
        @Override
        public int getMinimum() {
            return MIN_VALUE;
        }

        @Override
        public void setMinimum(int i) {
            throw new UnsupportedOperationException("TriangleValueModel has a static range");
        }

        @Override
        public int getMaximum() {
            return MAX_VALUE;
        }

        @Override
        public void setMaximum(int i) {
            throw new UnsupportedOperationException("TriangleValueModel has a static range");
        }

        @Override
        public int getValue() {
            return Util.toInt(value);
        }

        @Override
        public void setValue(int i) {
            oldValue = value;
            value = (byte) i;
            if (oldValue != value) {
                TriangleValueModel.this.fireStateChanged();
            }
        }

        @Override
        public void setValueIsAdjusting(boolean via) {
            valueIsAdjusting = via;
        }

        @Override
        public boolean getValueIsAdjusting() {
            return valueIsAdjusting;
        }

        @Override
        public int getExtent() {
            return 0;
        }

        @Override
        public void setExtent(int i) {
            throw new UnsupportedOperationException("TriangleValueModel has a static range");
        }

        @Override
        public void setRangeProperties(int val, int ext, int min, int max, boolean via) {
            throw new UnsupportedOperationException("TriangleValueModel has a static range");
        }
        
          ///////////////////
         // Listener crap //
        ///////////////////
    
        @Override
        public void addChangeListener(ChangeListener cl) {
            ell.add(ChangeListener.class, cl);
        }

        @Override
        public void removeChangeListener(ChangeListener cl) {
            ell.remove(ChangeListener.class, cl);
        }

        public void fireStateChanged() {
            ChangeEvent ce = new ChangeEvent(TriangleValueModel.this);
            Object[] listeners = ell.getListenerList();     // Guaranteed to return a non-null array
            // Process the listeners last to first, notifying
            // those that are interested in this event
            for (int i = listeners.length - 2; i >= 0; i -= 2) {
                if (listeners[i] == ChangeListener.class) {
                    ((ChangeListener) listeners[i + 1]).stateChanged(ce);
                }
            }
        }
    }
    
    public class SM implements SpinnerModel {
        private EventListenerList ell;

        public SM() {
            ell = new EventListenerList();
        }
        
        @Override
        public Object getValue() {
            return Util.toInt(value);
        }

        @Override
        public void setValue(Object o) {
            oldValue = value;
            int v = (int) o;
            if (v < MIN_VALUE || v > MAX_VALUE) {
                throw new IllegalArgumentException("Value must be between " + MIN_VALUE + " and " + MAX_VALUE);
            }
            value = (byte) v;
            if (oldValue != value) {
                TriangleValueModel.this.fireStateChanged();
            }
        }

        @Override
        public Object getNextValue() {
            if (Util.toInt(value) == MAX_VALUE) {
                return null;
            } else {
                return Util.toInt(value) + 1;
            }
        }

        @Override
        public Object getPreviousValue() {
            if (Util.toInt(value) == MIN_VALUE) {
                return null;
            } else {
                return Util.toInt(value) - 1;
            }
        }
        
          ///////////////////
         // Listener crap //
        ///////////////////

        @Override
        public void addChangeListener(ChangeListener cl) {
            ell.add(ChangeListener.class, cl);
        }

        @Override
        public void removeChangeListener(ChangeListener cl) {
            ell.remove(ChangeListener.class, cl);
        }

        public void fireStateChanged() {
            ChangeEvent ce = new ChangeEvent(TriangleValueModel.this);
            Object[] listeners = ell.getListenerList();     // Guaranteed to return a non-null array
            // Process the listeners last to first, notifying
            // those that are interested in this event
            for (int i = listeners.length - 2; i >= 0; i -= 2) {
                if (listeners[i] == ChangeListener.class) {
                    ((ChangeListener) listeners[i + 1]).stateChanged(ce);
                }
            }
        }
    }
}