package bsuir.sidorovich.pigeon.model;

import java.util.Date;

public class Message {

    public String message;
    public int messageType;
    public Date messageTime;

    public Message(String message, int messageType, Date date) {
        this.message = message;
        this.messageType = messageType;
        this.messageTime = date;
    }
}
