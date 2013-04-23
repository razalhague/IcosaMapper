package org.penny_craal.icosamapper.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Ville Jokela & James Pearce
 */
public class ColourPicker extends JPanel {
    
    public ColourPicker(/*LayerRenderer lr*/) {
        JLabel label = new JLabel("Colour");
        add(label);
        JSpinner color = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));//(initial value, minimum value, maximum value, step)
        add(color);
    }
}