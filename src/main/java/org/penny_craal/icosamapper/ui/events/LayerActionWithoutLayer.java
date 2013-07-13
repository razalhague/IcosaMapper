package org.penny_craal.icosamapper.ui.events;

/**
 *
 * @author Ville
 */
public class LayerActionWithoutLayer extends IMEvent {
    private static final long serialVersionUID = 1L;
    public LayerActionWithoutLayer(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "LayerActionWithoutLayer";
    }
}
