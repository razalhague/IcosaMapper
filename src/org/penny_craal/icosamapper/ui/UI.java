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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import org.penny_craal.icosamapper.map.GreyscaleLR;
import org.penny_craal.icosamapper.map.InvalidPathException;
import org.penny_craal.icosamapper.map.Layer;
import org.penny_craal.icosamapper.map.Path;

public class UI extends JFrame {

    public UI() {
        initUI();
    }

    /**
     * Builds the UI.
     */
    public final void initUI() {
        //creating the main window
        setTitle("IcosaMapper");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
         //creating menubar and menus
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu layer = new JMenu("Layers");
        JMenu view = new JMenu("View");
        //adding the menubar
        setJMenuBar(menubar);
        
        //creating components for the menus
        JMenuItem newF = new JMenuItem("New");

        JMenuItem open = new JMenuItem("Open");

        JMenuItem save = new JMenuItem("Save");

        JMenuItem saveas = new JMenuItem("Save As");

        JMenuItem quit = new JMenuItem("Exit");
        quit.setToolTipText("Exit application");
        //adding menus into menubar
        menubar.add(file);
        menubar.add(layer);
        menubar.add(view);
        //adding to the file menu components
        file.add(newF);
        file.add(open);
        file.add(save);
        file.add(saveas);
        file.add(quit);
        
        //Status Bar
        JPanel statusbar = new JPanel();
        statusbar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusbar, BorderLayout.SOUTH);
        statusbar.setLayout(new BoxLayout(statusbar, BoxLayout.X_AXIS));
        JLabel statusLabel = new JLabel();
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusbar.add(statusLabel);
        
        int renderDepth = 2;
        LayerPanel lPanel = new LayerPanel(createTestLayer(), renderDepth);
        statusLabel.setText("Render depth: " + renderDepth);
        add(lPanel, BorderLayout.CENTER);
        
        JPanel tools = new JPanel();
        tools.setLayout(new BorderLayout());
        add(tools, BorderLayout.WEST);
        
        JPanel paint = new JPanel();
        paint.setLayout(new BorderLayout());
        tools.add(paint, BorderLayout.NORTH);
        
        JPanel layers = new JPanel();
        layers.setLayout(new BorderLayout());
        tools.add(layers, BorderLayout.CENTER);

        //creating the toolbars
        JToolBar paintbar = new JToolBar();
        paintbar.setOrientation(JToolBar.VERTICAL);
        paint.add(paintbar, BorderLayout.EAST);
        paintbar.setFloatable(false);
        
        //giving every button meaning
        JButton neww = new JButton(new ImageIcon("gfx/new.png"));
        neww.setToolTipText("Adds a new thing");

        JButton delete = new JButton(new ImageIcon("gfx/delete.png"));
        delete.setToolTipText("Deletes a thing");

        JButton draw = new JButton(new ImageIcon("gfx/draw.png"));
        draw.setToolTipText("Draws a thing");

        JButton duplicate = new JButton(new ImageIcon("gfx/duplicate.png"));
        duplicate.setToolTipText("Dublicates a thing");

        JButton fill = new JButton(new ImageIcon("gfx/fill.png"));
        fill.setToolTipText("Fills a thing");

        JButton properties = new JButton(new ImageIcon("gfx/properties.png"));
        properties.setToolTipText("Shows the properties of a thing");

        JButton rename = new JButton(new ImageIcon("gfx/rename.png"));
        rename.setToolTipText("Renames a thing");

        JButton subdivide = new JButton(new ImageIcon("gfx/subdivide.png"));
        subdivide.setToolTipText("Subdivides a thing");

        JButton underlay = new JButton(new ImageIcon("gfx/underlay.png"));
        underlay.setToolTipText("to be added");//TODO

        JButton unite = new JButton(new ImageIcon("gfx/unite.png"));
        unite.setToolTipText("Unites a thing");

        JButton zoomin = new JButton(new ImageIcon("gfx/zoom-in.png"));
        zoomin.setToolTipText("Zooms in");

        JButton zoomout = new JButton(new ImageIcon("gfx/zoom-out.png"));
        zoomout.setToolTipText("zooms out");
        //adding all the buttons to the toolbar
        paintbar.add(draw);
        paintbar.add(fill);
        paintbar.add(subdivide);
        paintbar.add(unite);
        paintbar.add(zoomin);
        paintbar.add(zoomout);

        JToolBar layerbar = new JToolBar();
        layerbar.setOrientation(JToolBar.VERTICAL);
        layers.add(layerbar, BorderLayout.EAST);
        layerbar.setFloatable(false);
        
        JLabel layerLabel = new JLabel("Layers");
        layers.add(layerLabel, BorderLayout.NORTH);
        
        JList layerList = new JList();
        layerList.setBorder(new BevelBorder(BevelBorder.LOWERED));
        layers.add(layerList, BorderLayout.CENTER);
        
        layerbar.add(neww);
        layerbar.add(duplicate);
        layerbar.add(rename);
        layerbar.add(properties);
        layerbar.add(delete);
        layerbar.add(underlay);

        JPanel opSize = new JPanel();
        paint.add(opSize, BorderLayout.SOUTH);
        
        JLabel sizeLabel = new JLabel("Operating size");
        opSize.add(sizeLabel);
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));//(initial value, minimum value, maximum value, step)
        opSize.add(sizeSpinner);
        
        ColourPicker colour = new ColourPicker(new GreyscaleLR());
        paint.add(colour, BorderLayout.CENTER);
    }
    
    private Layer createTestLayer() {
        Layer layer = new Layer("test-layer", new GreyscaleLR(), (byte) 0);
        
        try {
            for (int i = 0; i < 20; i++) {
                byte[] api = {(byte) i};
                layer.divide(new Path(api));
                for (int j = 0; j < 9; j++) {
                    byte[] apj = {(byte) i, (byte) j};
                    layer.setElement(new Path(apj), (byte) (256/20*i + 256/20/9*j));
                }
            }
        } catch (InvalidPathException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return layer;
    }
}