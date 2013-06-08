package org.penny_craal.icosamapper.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 *
 * @author Ville
 */
public class ToolsPanel extends JPanel {
    private PaintPanel paintPanel;
    private LayerList layerList;
    
    public ToolsPanel() {
        paintPanel = new PaintPanel();
        layerList = new LayerList();
        
        setLayout(new BorderLayout());
        
        add(paintPanel, BorderLayout.PAGE_START);
        add(layerList, BorderLayout.CENTER);
    }
}
