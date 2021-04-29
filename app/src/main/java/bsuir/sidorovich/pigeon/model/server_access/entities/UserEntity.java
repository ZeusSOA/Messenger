package bsuir.sidorovich.pigeon.model.server_access.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserEntity {
//public class UserEntity implements Serializable {

    private static final long serialVersionUID = -2402998216354216363L;

    private long id;

    private String email;

    private String username;

    private String password;

    private List<ChatEntity> chats = new ArrayList<>();

//    public String getEmail() {
//        return email;
//    }

    @Override
    public String toString() {
        return "\n\nUserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", chats=" + chats +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<ChatEntity> getChats() {
        return chats;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setChats(List<ChatEntity> chats) {
        this.chats = chats;
    }
}