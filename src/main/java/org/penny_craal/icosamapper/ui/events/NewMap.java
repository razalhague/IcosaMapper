package org.penny_craal.icosamapper.ui.events;

/**
 *
 * @author Ville Jokela
 */
public class NewMap extends IMEvent {
    private static final long serialVersionUID = 1L;

    public NewMap(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "NewMap";
    }
}
