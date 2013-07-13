package org.penny_craal.icosamapper.ui.events;

/**
 *
 * @author Ville Jokela
 */
public class SaveMapAs extends IMEvent {
    private static final long serialVersionUID = 1L;

    public SaveMapAs(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "SaveMapAs";
    }
}