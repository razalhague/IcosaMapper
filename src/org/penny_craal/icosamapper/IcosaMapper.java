package org.penny_craal.icosamapper;

import javax.swing.SwingUtilities;
import org.penny_craal.icosamapper.ui.UI;

/**
 *
 * @author Ville Jokela & James Pearce
 */
public class IcosaMapper {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UI ui = new UI();
                ui.setVisible(true);
            }
        });
    }
}