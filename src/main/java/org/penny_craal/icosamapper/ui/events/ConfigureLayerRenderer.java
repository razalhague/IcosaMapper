package org.penny_craal.icosamapper.ui.events;

/**
 * @author Ville Jokela
 */
public class ConfigureLayerRenderer extends IMEvent {
    public final String layerName;

    public ConfigureLayerRenderer(Object source, String layerName) {
        super(source, EventType.configureLayerRenderer);
        this.layerName = layerName;
    }

    @Override
    public String toString() {
        return "ConfigureLayerRenderer: " + layerName;
    }
}
