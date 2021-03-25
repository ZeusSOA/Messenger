package bsuir.sidorovich.pigeon.model;

import java.util.Date;

public class Message {

    public String message;
    public int messageType;
    public Date messageTime;
    public String author;

    public Message(String message, int messageType, String author, Date date) {
        this.message = message;
        this.messageType = messageType;
        this.author=author;
        this.messageTime = date;
    }
}
