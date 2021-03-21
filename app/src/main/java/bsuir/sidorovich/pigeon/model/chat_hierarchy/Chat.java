package bsuir.sidorovich.pigeon.model.chat_hierarchy;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.model.User;

public class Chat {
    private ArrayList<User> members = new ArrayList<>();

    private String id;
    private String chatname;
    //private String imageUrl;

    public Chat() {}

    public String getId() {
        return id;
    }

    public String getChatname() {
        return chatname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }
}
