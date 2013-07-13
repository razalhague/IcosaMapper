package org.penny_craal.icosamapper.ui;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.penny_craal.icosamapper.ui.events.IMEvent;
import org.penny_craal.icosamapper.ui.events.IMEventHelper;
import org.penny_craal.icosamapper.ui.events.IMEventListener;
import org.penny_craal.icosamapper.ui.events.IMEventSource;
import org.penny_craal.icosamapper.ui.events.LayerSelected;

/**
 *
 * @author Ville Jokela
 */
@SuppressWarnings("serial")
public class LayerList extends JList<String> implements IMEventSource {
    LayerList(LayerListModel layerListModel) {
        super(layerListModel);
        Listener listener = new Listener();
        addMouseListener(listener);
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    @Override
    public void addIMEventListener(IMEventListener imel) {
        IMEventHelper.addListener(listenerList, imel);
    }
    
    @Override
    public void removeIMEventListener(IMEventListener imel) {
        IMEventHelper.removeListener(listenerList, imel);
    }
    
    protected void fireEvent(IMEvent ime) {
        IMEventHelper.fireEvent(listenerList, ime);
    }
    
    private class Listener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent me) {
            if (me.getClickCount() <= 1)
                return;
            Rectangle r = getCellBounds(0, getLastVisibleIndex());
            if (r == null)
                throw new RuntimeException("getCellBounds() returned null");
            if (!r.contains(me.getPoint()))
                return;
            
            fireEvent(new LayerSelected(LayerList.this, LayerList.this.getSelectedValue()));
        }
    }
}