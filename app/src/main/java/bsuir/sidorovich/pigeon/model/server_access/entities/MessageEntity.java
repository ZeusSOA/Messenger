package bsuir.sidorovich.pigeon.model.server_access.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageEntity {
//public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 8272313331707744189L;

    private long id;

    private ChatEntity chat;

    private UserEntity user;

//    private LocalDateTime sendTime;

    private String text;

//    public MessageEntity() {}

    @Override
    public String toString() {
        return "\n\nMessageEntity{" +
                "id=" + id +
                ", chat=" + chat +
                ", user=" + user +
                ", text='" + text + '\'' +
                '}';
    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public ChatEntity getChat() {
//        return chat;
//    }
//
//    public void setChat(ChatEntity chat) {
//        this.chat = chat;
//    }
//
//    public UserEntity getUser() {
//        return user;
//    }
//
//    public void setUser(UserEntity user) {
//        this.user = user;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
}