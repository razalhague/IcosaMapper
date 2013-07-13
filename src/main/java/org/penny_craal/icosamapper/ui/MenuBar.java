/* IcosaMapper - an rpg map editor based on equilateral triangles that form an icosahedron
 * Copyright (C) 2013  Ville Jokela, James Pearce
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.penny_craal.icosamapper.ui.events.About;
import org.penny_craal.icosamapper.ui.events.Exit;
import org.penny_craal.icosamapper.ui.events.IMEvent;
import org.penny_craal.icosamapper.ui.events.IMEventHelper;
import org.penny_craal.icosamapper.ui.events.IMEventListener;
import org.penny_craal.icosamapper.ui.events.IMEventSource;
import org.penny_craal.icosamapper.ui.events.NewMap;
import org.penny_craal.icosamapper.ui.events.OpenMap;
import org.penny_craal.icosamapper.ui.events.SaveMap;
import org.penny_craal.icosamapper.ui.events.SaveMapAs;

/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements IMEventSource {
    private final Menu[] menus = {
        new Menu("File", KeyEvent.VK_F, new Item[] {
            new Item("New Map",         "new-map",      KeyEvent.VK_N,  KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK)),
            new Item("Open Map...",     "open-map",     KeyEvent.VK_O,  KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK)),
            new Item("Save Map",        "save-map",     KeyEvent.VK_S,  KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK)),
            new Item("Save Map As...",  "save-map-as",  KeyEvent.VK_A,  null),
            null,
            new Item("Exit",            "exit",         KeyEvent.VK_E,  KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK)),
        }),
        new Menu("Help", KeyEvent.VK_H, new Item[] {
            new Item("About", "about", KeyEvent.VK_A, null),
        }),
    };
    
    public MenuBar() {
        Listener listener = new Listener();
        for (Menu m: menus) {
            JMenu menu = new JMenu(m.text);
            menu.setMnemonic(m.mnemonic);
            for (Item i: m.items) {
                if (i == null) {
                    menu.addSeparator();
                    continue;
                }
                JMenuItem item = new JMenuItem(i.text, i.mnemonic);
                item.setActionCommand(i.action);
                if (i.accelerator != null) {
                    item.setAccelerator(i.accelerator);
                }
                item.addActionListener(listener);
                menu.add(item);
            }
            add(menu);
        }
    }
    
    private static final class Menu {
        public final String text;
        public final int mnemonic;
        public final Item[] items;

        public Menu(String text, int mnemonic, Item[] items) {
            this.text = text;
            this.mnemonic = mnemonic;
            this.items = items;
        }
    }
    
    private static final class Item {
        public final String text;
        public final String action;
        public final int mnemonic;
        public final KeyStroke accelerator;

        public Item(String text, String action, int mnemonic, KeyStroke accelerator) {
            this.text = text;
            this.action = action;
            this.mnemonic = mnemonic;
            this.accelerator = accelerator;
        }
    }
    
      ///////////////////
     // Listener crap //
    ///////////////////
    
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
    
    private final class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch (ae.getActionCommand()) {
                case "new-map":
                    fireEvent(new NewMap(MenuBar.this));
                    break;
                case "open-map":
                    fireEvent(new OpenMap(MenuBar.this));
                    break;
                case "save-map":
                    fireEvent(new SaveMap(MenuBar.this));
                    break;
                case "save-map-as":
                    fireEvent(new SaveMapAs(MenuBar.this));
                    break;
                case "exit":
                    fireEvent(new Exit(MenuBar.this));
                    break;
                case "about":
                    fireEvent(new About(MenuBar.this));
                    break;
            }
        }
    }
}