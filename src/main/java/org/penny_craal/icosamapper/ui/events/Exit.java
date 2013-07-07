package org.penny_craal.icosamapper.ui.events;

/**
 *
 * @author Ville
 */
public class Exit extends IMEvent {
    private static final long serialVersionUID = 1L;

    public Exit(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "Exit";
    }
}