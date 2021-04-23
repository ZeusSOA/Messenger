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

//    @Override
//    public String toString() {
//        return "UserEntity{" +
//                "id=" + id +
//                ", email='" + email + '\'' +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", chats=" + chats +
//                '}';
//    }
}