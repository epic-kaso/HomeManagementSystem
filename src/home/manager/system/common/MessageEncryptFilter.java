package home.manager.system.common;

import java.util.Base64;

public class MessageEncryptFilter extends Filter {
    private static MessageEncryptFilter messageEncryptFilter;

    static {
        messageEncryptFilter = new MessageEncryptFilter();
    }

    private MessageEncryptFilter() {
    }

    public static MessageEncryptFilter getInstance() {
        if (messageEncryptFilter == null)
            messageEncryptFilter = new MessageEncryptFilter();
        return messageEncryptFilter;
    }

    public String encodeMessage(String s) {
        return new String(Base64.getEncoder().encode(s.getBytes()));
    }

    public String decodeMessage(String message) {
        return new String(Base64.getDecoder().decode(message));
    }

    @Override
    public <T> String executeBeforeFilter(T arg) {
        return this.encodeMessage(arg.toString());
    }

    @Override
    public <T> String executeAfterFilter(T arg) {
        return this.decodeMessage(arg.toString());
    }
}