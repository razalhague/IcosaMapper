package org.penny_craal.icosamapper.ui;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;

import org.penny_craal.icosamapper.map.GreyscaleLR;

/**
 *
 * @author Ville
 */
public class PaintPanel extends JPanel {
    private JPanel opSize;
    private JSpinner opSizeSpinner;
    private ButtonBar buttonBar;
    private ColourPicker colourPicker;
    
    private final static Map<String, String> paintButtons = new HashMap<String, String>() {{
        put("draw",       "draw on the map");
        put("fill",       "fill an area");
        put("subdivide",  "divide a triangle");
        put("unite",      "unite a triangle");
        put("zoom-in",    "zoom in to a triangle");
        put("zoom-out",   "zoom out");
    }};
    
    public PaintPanel() {
        opSize = new JPanel();
        opSize.add(new JLabel("Operating size"));
        opSizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        opSize.add(opSizeSpinner);
        buttonBar = new ButtonBar(paintButtons);
        colourPicker = new ColourPicker(new GreyscaleLR());
        
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Paint"));
        setLayout(new BorderLayout());
        
        add(colourPicker, BorderLayout.CENTER);
        add(buttonBar, BorderLayout.LINE_END);
        add(opSize, BorderLayout.PAGE_END);
    }
}
