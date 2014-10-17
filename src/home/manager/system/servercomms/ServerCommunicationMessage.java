/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.manager.system.servercomms;

import home.manager.system.common.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kaso
 */
public class ServerCommunicationMessage implements Message {
    public static Map<Integer, String> messageTypesMap;

    static {
        messageTypesMap = new HashMap<>();
        messageTypesMap.put(0, "MeterControl");
        messageTypesMap.put(1, "StatusCheck");
        messageTypesMap.put(2, "SectorControl");
    }

    private int messageType;
    private String message;

    @Override
    public int getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessageTypeDescription() {
        return messageTypesMap.get(this.getMessageType());
    }
}
