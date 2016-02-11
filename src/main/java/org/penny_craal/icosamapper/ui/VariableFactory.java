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

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.penny_craal.icosamapper.map.layerrenderers.variabletypes.IntegerType;
import org.penny_craal.icosamapper.map.layerrenderers.variabletypes.VariableType;
import org.penny_craal.icosamapper.ui.events.*;

/**
 * @author Ville Jokela
 */
public class VariableFactory {
    public static VariableComponent makeComponentFor(String variableName, VariableType var, Object initValue) {
        if (var instanceof IntegerType) {
            return makeInteger(variableName, (IntegerType) var, initValue);
        } else {
            throw new RuntimeException("unrecognized VariableType: " + var);
        }
    }

    private static VariableComponent makeInteger(String variableName, IntegerType var, Object initValue) {
        return new IntegerComponent(variableName, var, initValue);
    }

    public static abstract class VariableComponent extends JPanel implements IMEventSource {
        ///////////////////
        // Listener crap //
        ///////////////////
        public abstract Object getValue();

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
    }

    private static class IntegerComponent extends VariableComponent {
        private final IntegerType integerType;
        private TriangleValueModel tvm;
        private final String variableName;

        public IntegerComponent(String variableName, IntegerType integerType, Object initValue) {
            this.integerType = integerType;
            this.variableName = variableName;
            Listener listener = new Listener();
            tvm = new TriangleValueModel((Integer) initValue, integerType.minimum, integerType.maximum);
            setLayout(new BorderLayout());
            JSlider slider = new JSlider(tvm.getBoundedRangeModel());
            JSpinner spinner = new JSpinner(tvm.getSpinnerModel());
            int minLength = Integer.toString(integerType.minimum).length();
            int maxLength = Integer.toString(integerType.maximum).length();
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
            editor.getTextField().setColumns(Math.max(minLength, maxLength));
            slider.addChangeListener(listener);
            spinner.addChangeListener(listener);
            add(slider, BorderLayout.CENTER);
            add(spinner, BorderLayout.LINE_END);
        }

        @Override
        public Object getValue() {
            return tvm.getValue();
        }

        private class Listener implements ChangeListener {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                fireEvent(new LayerRendererReconfigured(IntegerComponent.this, variableName));
            }
        }
    }
}
