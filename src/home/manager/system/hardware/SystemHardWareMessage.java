package home.manager.system.hardware;

import home.manager.system.common.Message;

import java.util.Base64;

/**
 * Created by kaso on 10/16/2014.
 */
public class SystemHardWareMessage implements Message {

    private String message;
    private int messageType;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = new String(Base64.getDecoder().decode(message));
    }

    @Override
    public int getMessageType() {
        return this.messageType;
    }

    @Override
    public void setMessageType(int type) {
        this.messageType = type;
    }

    @Override
    public String getMessageTypeDescription() {
        return null;
    }


}
