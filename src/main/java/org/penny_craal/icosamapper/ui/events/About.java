package org.penny_craal.icosamapper.ui.events;

/**
 *
 * @author Ville
 */
public class About extends IMEvent {
    private static final long serialVersionUID = 1L;

    public About(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "About";
    }
}