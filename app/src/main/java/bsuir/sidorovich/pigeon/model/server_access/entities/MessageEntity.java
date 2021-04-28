package bsuir.sidorovich.pigeon.model.server_access.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class MessageEntity {
//public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 8272313331707744189L;

    public long id;

    public ChatEntity chat;

    public UserEntity user;

    public Date sendTime;

    public String text;
}