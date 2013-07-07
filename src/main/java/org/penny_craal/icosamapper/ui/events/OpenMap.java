package org.penny_craal.icosamapper.ui.events;

/**
 *
 * @author Ville
 */
public class OpenMap extends IMEvent {
    private static final long serialVersionUID = 1L;

    public OpenMap(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "OpenMap";
    }
}
