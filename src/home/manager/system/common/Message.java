package home.manager.system.common;

/**
 * Created by kaso on 10/13/2014.
 */
public interface Message {
    public String getMessage();

    public void setMessage(String message);

    public int getMessageType();

    public void setMessageType(int type);

    public String getMessageTypeDescription();
}
