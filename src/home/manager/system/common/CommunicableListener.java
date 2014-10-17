package home.manager.system.common;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This defines a Abstract Communicable Object with Basic Listerner functionality implemented
 * Created by Akachukwu Okafor on 10/16/2014.
 * </p>
 */
abstract public class CommunicableListener implements Communicable {
    private List<Listener> listenerList;


    /**
     * This instantiates the Object, Setting up the system for optimal work
     */
    public CommunicableListener() {
        listenerList = new ArrayList<>();
    }

    /**
     * <p>
     * Add a listener to the Communicable so as to be notified of
     * new messages when a new message becomes available.
     * </p>
     *
     * @param listener Listener
     * @return boolean true on success, false on failure
     */
    @Override
    public boolean addListener(Listener listener) {
        return listenerList != null && this.listenerList.add(listener);
    }

    /**
     * This function notifies the registered listeners of new message
     *
     * @param message the message object passed to the listeners @see Message
     */
    protected void notifyListeners(Message message) {
        if (listenerList != null && listenerList.size() > 0) {
            for (Listener l : listenerList) {
                l.executeMessage(this, message);
            }
        }
    }
}
