package org.penny_craal.icosamapper.ui.events;

/**
 *
 * @author Ville
 */
public class NewMap extends IMEvent {
    private static final long serialVersionUID = 1L;
    public final String layerName;

    public NewMap(Object source, String layerName) {
        super(source);
        this.layerName = layerName;
    }

    @Override
    public String toString() {
        return "NewMap: " + layerName;
    }
}
