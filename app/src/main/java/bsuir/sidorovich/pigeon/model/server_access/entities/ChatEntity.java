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
}