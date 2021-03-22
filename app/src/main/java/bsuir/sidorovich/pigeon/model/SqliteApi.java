package bsuir.sidorovich.pigeon.model;

public class SqliteApi {
    private static User user;

    //возможно в будущем этого метода не будет, так как в других методах будет прямое обращение к ПЗУ
    public static void initialize() {
        //извлечение информации из ПЗУ
        user = new User();
        user.setId("u_id_0");
        user.setUsername("u_name_0");
        user.setPassword("abcd1234");
    }

    public static String getPassword() {
        return user.getPassword();
    }

    public static String getId() {
        return user.getId();
    }

    public static String getUsername() {
        return user.getUsername();
    }
}
