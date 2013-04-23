package org.penny_craal.icosamapper.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.penny_craal.icosamapper.map.LayerRenderer;

/**
 *
 * @author Ville Jokela & James Pearce
 */
public class ColourPicker extends JPanel {
    
    public ColourPicker(LayerRenderer lr) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Colour");
        add(label, BorderLayout.NORTH);
        
        JSpinner color = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));//(initial value, minimum value, maximum value, step)
        add(color, BorderLayout.SOUTH);
        
        JSlider slider = new JSlider();
        slider.setOrientation(javax.swing.JSlider.VERTICAL);
        add(slider, BorderLayout.EAST);
        
        JPanel current = new JPanel();
        add(current, BorderLayout.CENTER);
        current.setBackground(new Color(lr.renderByte((byte)0)));
    }
}