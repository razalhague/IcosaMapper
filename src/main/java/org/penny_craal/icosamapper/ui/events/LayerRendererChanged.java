package org.penny_craal.icosamapper.ui.events;

/**
 * @author Ville Jokela
 */
public class LayerRendererChanged extends IMEvent {
    public final String layerName;
    public final String lr;

    public LayerRendererChanged(Object source, String layerName, String lr) {
        super(source, EventType.layerRendererChanged);
        this.layerName = layerName;
        this.lr = lr;
    }

    @Override
    public String toString() {
        return "LayerRendererChanged: " + layerName + ", " + lr;
    }
}
