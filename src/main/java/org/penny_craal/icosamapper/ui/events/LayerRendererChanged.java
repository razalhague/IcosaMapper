package org.penny_craal.icosamapper.ui.events;

import org.penny_craal.icosamapper.map.layerrenderers.LayerRenderer;

/**
 * @author Ville Jokela
 */
public class LayerRendererChanged extends IMEvent {
    public final String layerName;
    public final LayerRenderer lr;

    public LayerRendererChanged(Object source, String layerName, LayerRenderer lr) {
        super(source, EventType.layerRendererChanged);
        this.layerName = layerName;
        this.lr = lr;
    }

    @Override
    public String toString() {
        return "LayerRendererChanged: " + layerName + ", " + lr.getType();
    }
}
