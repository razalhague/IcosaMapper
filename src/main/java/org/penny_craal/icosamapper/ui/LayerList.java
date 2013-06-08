package org.penny_craal.icosamapper.ui;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Ville
 */
public class LayerList extends JPanel {
    private JList layerList;
    private ButtonBar buttonBar;
    
    private final static Map<String, String> layerButtons = new HashMap<String, String>() {{
        put("new",         "create new layer");
        put("duplicate",   "duplicate layer");
        put("rename",      "rename layer");
        put("properties",  "edit layer's properties");
        put("underlay",    "create an underlay");
        put("delete",      "delete layer");
    }};
    
    public LayerList () {
        layerList = new JList();
        layerList.setBorder(new BevelBorder(BevelBorder.LOWERED));
        buttonBar = new ButtonBar(layerButtons);
        
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Layers"));
        setLayout(new BorderLayout());
        
        add(buttonBar, BorderLayout.LINE_END);
        add(layerList, BorderLayout.CENTER);
    }
}
