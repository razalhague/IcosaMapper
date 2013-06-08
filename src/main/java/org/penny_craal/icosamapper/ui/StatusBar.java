package org.penny_craal.icosamapper.ui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Ville
 */
public class StatusBar extends JPanel {
    private int renderDepth;
    private JLabel status;

    public int getRenderDepth() {
        return renderDepth;
    }

    public void setRenderDepth(int renderDepth) {
        this.renderDepth = renderDepth;
    }
    
    public StatusBar(int renderDepth) {
        status = new JLabel("Render depth: " + renderDepth);
        
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
        add(status);
    }
}
