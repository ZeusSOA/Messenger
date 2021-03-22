package bsuir.sidorovich.pigeon.model;

public class User {
    private String id;
    private String username;
    private String imageUrl;
    private String password;

    //характеристика пользователя в контексте чата (возможно создание класса ChatUser extends User с этим полем)
    //если true -   пользователь имеет чат в своём списке чатов (то есть он может писать сообщения)
    //если false -  пользователь удалил чат из своего списка чатов (то есть он не может писать сообщения),
    //              но из самого чата он как участник не удаляется, а переходит в состояние "неактивного",
    //              чтобы в будущем можно было брать информацию о нём
    //суть - если есть хотя бы 1 участник не удалил чат из своего списка чатов, то чат не удаляется из БД на сервере, если 0 - удаляется
    private boolean isCurrentMember;

    public User() {}

    public User(String id, String username, String imageUrl) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
