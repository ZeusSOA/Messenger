package bsuir.sidorovich.pigeon.model;

import java.util.ArrayList;

public class Chat {
    private ArrayList<User> members = new ArrayList<>();



    private String id;
    private String chatname;
    private String imageUrl;

    public Chat() {}
    public Chat(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getChatname() {
        return chatname;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }
}
