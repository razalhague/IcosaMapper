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

package org.penny_craal.icosamapper.ui.events;

import java.util.EventObject;

/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class IMEvent extends EventObject {
    public final Type type;
    
    public IMEvent(Object source, Type type) {
        super(source);
        this.type = type;
    }
    
    public IMEvent(Object source, IMEvent ime) {
        this(source, ime.type);
    }
    
    public static enum Type {
        // LayerList
        LAYER_SELECTED  ("layer-selected"),
        
        // PaintPanel
        TOOL_SELECTED   ("tool-selected"),
        COLOUR_SELECTED ("colour-selected"),
        OPSIZE_SELECTED ("operating-size-selected"),
        
        // LayerManagementBar
        NEW             ("new"),
        DUPLICATE       ("duplicate"),
        RENAME          ("rename"),
        PROPERTIES      ("properties"),
        UNDERLAY        ("underlay"),
        DELETE          ("delete"),
        ;
        public final String command;
        
        private Type(String command) {
            this.command = command;
        }
        
        public static Type fromString(String command) {
            for (Type t: Type.values())
                if (t.command.equals(command))
                    return t;
            
            return null;
        }
    }
}