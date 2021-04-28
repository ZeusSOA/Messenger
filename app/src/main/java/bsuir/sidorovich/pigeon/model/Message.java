package bsuir.sidorovich.pigeon.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class Message {

    public String message;
    public int messageType;
    public Date messageTime;
    public String author;
    public long id;

    public Message(String message, int messageType, String author, Date date, long id) {
        this.message = message;
        this.messageType = messageType;
        this.author=author;
        this.messageTime = date;
        this.id=id;
    }

    @NonNull
    @Override
    public Message clone() throws CloneNotSupportedException {
        return new Message(this.message, this.messageType, this.author, this.messageTime, this.id);
    }

    public void set(Message message){
        this.message = message.message;
        this.messageType = message.messageType;
        this.author=message.author;
        this.messageTime = message.messageTime;
        this.id = message.id;
    }
}
