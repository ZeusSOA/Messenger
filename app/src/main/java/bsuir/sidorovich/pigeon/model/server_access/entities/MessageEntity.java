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
}