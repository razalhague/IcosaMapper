/* IcosaMapper - an rpg map editor based on equilateral triangles that form an icosahedron
 * Copyright (C) 2013  Ville Jokela
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

import java.awt.*;

import javax.swing.*;

import org.penny_craal.icosamapper.map.layerrenderers.LayerRenderer;

/**
 * @author Ville Jokela
 */
public class LayerRendererDisplay extends JPanel {
    private LayerRenderer lr;
    private Insets insets;

    private static final int MIN_DRAW_HEIGHT = 32;
    private static final int MIN_DRAW_WIDTH = 256;

    public LayerRendererDisplay(LayerRenderer lr) {
        this.lr = lr;
        this.insets = new Insets(2, 2, 2, 2);
        setPreferredSize(new Dimension(insets.left + MIN_DRAW_WIDTH + insets.right, insets.top + MIN_DRAW_HEIGHT + insets.bottom));
        setMinimumSize(getPreferredSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;
        for (int i = 0; i < 256; i++) {
            g.setColor(new Color(lr.renderByte((byte) i)));
            g.fillRect(
                    insets.left + (int) (width / 256.0 * i),
                    insets.top,
                    insets.left + (int) (width / 256.0 * (i + 1)),
                    insets.top + height);
        }
    }
}
