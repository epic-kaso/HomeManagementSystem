package home.manager.system.common;

/**
 * This defines an Interface for Systems that want to communicate with each other
 * Created by kaso on 10/16/2014.
 */
public interface Communicable {
    /**
     * <p>
     * Add a listener to the Communicable Object.
     * the Listener is notified of new messages from the Communicable
     * </p>
     *
     * @param listener instance of Listener
     * @return boolean true on success, false on failure
     */
    public boolean addListener(Listener listener);

    /**
     * <p>
     * This is the main methos that other objects can use to send messages
     * to the Communicable Object
     * </p>
     *
     * @param message The Message Object
     * @return true on success, false on failure
     */
    public boolean sendMessage(Message message);
}
