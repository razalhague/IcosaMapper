package org.penny_craal.icosamapper.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author Ville
 */
public class ButtonBar extends JToolBar {
    private List<JButton> buttonList;
    
    public ButtonBar(Map<String, String> buttons) {
        buttonList = new ArrayList<>(buttons.size());
        
        setOrientation(JToolBar.VERTICAL);
        setFloatable(false);
        
        for (Map.Entry<String, String> e: buttons.entrySet()) {
            JButton button = new JButton(new ImageIcon(getClass().getResource("/gfx/" + e.getKey() + ".png")));
            button.setToolTipText(e.getValue());
            buttonList.add(button);
            add(button);
        }
    }
}
