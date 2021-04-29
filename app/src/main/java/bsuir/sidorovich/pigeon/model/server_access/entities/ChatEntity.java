package bsuir.sidorovich.pigeon.model.server_access.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatEntity {
//public class ChatEntity implements Serializable {
    private static final long serialVersionUID = 7696060980312070187L;

    private long id;

    private ChatType chatType;

    private String chatName;

    private List<UserEntity> users = new ArrayList<>();

    private List<MessageEntity> messages = new ArrayList<>();

    @Override
    public String toString() {
        return "\n\nChatEntity{" +
                "id=" + id +
                ", chatType=" + chatType +
                ", chatName='" + chatName + '\'' +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }

    public long getId() {
        return id;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public String getChatName() {
        return chatName;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }
}