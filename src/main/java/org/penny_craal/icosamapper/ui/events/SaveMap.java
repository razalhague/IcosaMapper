package org.penny_craal.icosamapper.ui.events;

/**
 *
 * @author Ville
 */
public class SaveMap extends IMEvent {
    private static final long serialVersionUID = 1L;

    public SaveMap(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "SaveMap";
    }
}