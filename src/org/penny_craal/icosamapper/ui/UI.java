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

/**
 * The user interface for IcosaMapper.
 * @author Ville Jokela
 * @author James Pearce
 */
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import org.penny_craal.icosamapper.map.GreyscaleLR;

public class UI extends JFrame {
    private int renderDepth;

    public UI() {
        renderDepth = 1;
        initUI();
    }

    /**
     * Builds the UI.
     */
    public final void initUI() {
        setTitle("IcosaMapper");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);    // centered on screen
        setLayout(new BorderLayout());
        
        setJMenuBar(createMenuBar());
        add(createStatusBar(), BorderLayout.PAGE_END);
        add(new LayerPanel(LayerPanel.createTestLayer(), renderDepth), BorderLayout.CENTER);
        add(createToolsArea(), BorderLayout.LINE_START);
    }

    private JMenuBar createMenuBar() {
       JMenuBar menuBar = new JMenuBar();
       
       JMenu file = new JMenu("File");
       JMenuItem newM = new JMenuItem("New Map");
       JMenuItem open = new JMenuItem("Open Map...");
       JMenuItem save = new JMenuItem("Save Map");
       JMenuItem saveAs = new JMenuItem("Save Map As...");
       JMenuItem exit = new JMenuItem("Exit");
       exit.setToolTipText("Exit IcosaMapper");
       file.add(newM);
       file.add(open);
       file.add(save);
       file.add(saveAs);
       file.add(exit);
       
       JMenu help = new JMenu("Help");
       JMenuItem about = new JMenuItem("About");
       help.add(about);
       
       menuBar.add(file);
       menuBar.add(help);
       
       return menuBar;
    }
    
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel();
        
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.LINE_AXIS));
        statusBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        JLabel statusLabel = new JLabel("Render depth: " + renderDepth);
        statusBar.add(statusLabel);
        
        return statusBar;
    }

    private JPanel createToolsArea() {
        JPanel tools = new JPanel();
        tools.setLayout(new BorderLayout());
        
        String[][] paintButtons = {
            { "draw",       "draw on the map" },
            { "fill",       "fill an area" },
            { "subdivide",  "divide a triangle" },
            { "unite",      "unite a triangle" },
            { "zoom-in",    "zoom in to a triangle" },
            { "zoom-out",   "zoom out" },
        };
        String[][] layerButtons = {
            {"new",         "create new layer" },
            {"duplicate",   "duplicate layer" },
            {"rename",      "rename layer" },
            {"properties",  "edit layer's properties" },
            {"underlay",    "create an underlay" },
            {"delete",  "delete layer" },
        };

        JPanel paint = new JPanel();
        paint.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Paint"));
        paint.setLayout(new BorderLayout());
        paint.add(createButtonBar(paintButtons), BorderLayout.LINE_END);
        paint.add(new ColourPicker(new GreyscaleLR()), BorderLayout.CENTER);
        JPanel opSize = new JPanel();
        opSize.add(new JLabel("Operating size"));
        opSize.add(new JSpinner(new SpinnerNumberModel(1, 1, 10, 1)));
        paint.add(opSize, BorderLayout.PAGE_END);
        
        JPanel layers = new JPanel();
        layers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Layers"));
        layers.setLayout(new BorderLayout());
        layers.add(createButtonBar(layerButtons), BorderLayout.LINE_END);
        JList layerList = new JList();
        layerList.setBorder(new BevelBorder(BevelBorder.LOWERED));
        layers.add(layerList, BorderLayout.CENTER);
        
        tools.add(paint, BorderLayout.PAGE_START);
        tools.add(layers, BorderLayout.CENTER);
        
        return tools;
    }

    private JToolBar createButtonBar(String[][] paintButtons) {
        JToolBar buttonBar = new JToolBar();
        buttonBar.setOrientation(JToolBar.VERTICAL);
        buttonBar.setFloatable(false);
        
        for (String[] b: paintButtons) {
            JButton button = new JButton(new ImageIcon("gfx/" + b[0] + ".png"));
            button.setToolTipText(b[1]);
            buttonBar.add(button);
        }
        
        return buttonBar;
    }
}