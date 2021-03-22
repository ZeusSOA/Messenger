package bsuir.sidorovich.pigeon.model;

// класс, содержащий статические методы взаимодействия с сервером
// при необходимости api будет реализовано в другом виде

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.model.chat_hierarchy.Chat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.GroupChat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.SingleChat;

public class ServerApi {
    //временные хранилища, играющие роль sql на сервере
    public static ArrayList<Chat> chats = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static int chatIdCounter = 1;
    public static int userIdCounter = 1001;

    // в будущем этого метода не будет, так как в других методах будет прямое обращение к серверу
    public static void initialize() {
        for (int i = 0; i < 6; i++) {
            users.add(new User(
                    "u_id_" + userIdCounter,
                    "u_name_" + userIdCounter,
                    "")
            );
            userIdCounter++;
        }
        for (int i = 0; i < 9; i++) {
            if (i < 3) {
                Chat chat = new SingleChat();
                chat.setChatname(users.get(i).getUsername());
                chat.setId("c_id_" + chatIdCounter);
                chats.add(chat);
            } else {
                Chat chat = new GroupChat();
                chat.setChatname("g_name_" + chatIdCounter);
                chat.setId("c_id_" + chatIdCounter);
                chats.add(chat);
            }
            chatIdCounter++;
        }
    }

    //получить от сервера список чатов, в которых состоит пользователь
    public static ArrayList<Chat> getChats() {
        //отправить свой userID серверу (userID - клиент)
        //сервер ищет, в каких чатах состоит userID
        //сервер возвращает список чатов с chatID

        //одно из полей БД - тип чата: single or group
        //если group, то  chatname = chatname из БД (возможно стоит сделать append userID, чтобы на него заменить chatID)
        //если single, то chatname = username второго пользователя (chatname в БД пуст)

        //ChatView:
        //название, id чата
        //фото, последнее сообщение + время, уведомление

        return chats;
    }

    //метод возвращает список пользователей с именем или id, которое содержит заданную строку
    public static ArrayList<User> getUsersByText(String text) {
        //логика проверки совпадения переносится на сервер

        ArrayList<User> foundUsers = new ArrayList<>();
        boolean isId = false;
        if (text.charAt(0) == '@') {
            text = text.substring(1);
            isId = true;
        }
        for (User user : users) {
            String string = isId ? user.getId() : user.getUsername();
            if (string.contains(text)) {
                foundUsers.add(user);
            }
        }

        return foundUsers;
    }

    //добавить пользователя в список чатов
    //    то есть либо создать пустой чат с одним активным и одним неактивным пользователем (поле isCurrentMember)
    //            либо в существующем чате с одним активным пользователем активировать второго
    public static String addSingleChatWithUser(String userId) {
        //алгоритм на сервере (работа с двумя пользователями: клиент и userId):
        //получить список чатов, в которых присутствует клиент
        //искать чат с userId
        //      если такой чат найден, проверить isCurrentMember для клиента
        //          если true, то выход
        //          если false, то сделать true
        //      если чат не найден, то создать его, для клиента isCurrentMember=true, для userId isCurrentMember=false
        //полученный id чата вернуть

        //временный код
        Chat chat = new SingleChat();
        for (User user : users) {
            if (userId.equals(user.getId())) {
                chat.setChatname(user.getUsername());
                break;
            }
        }
        chat.setId("c_id_" + chatIdCounter);
        chats.add(chat);
        chatIdCounter++;

        return chat.getId();
    }

    //создать пустую беседу, вступить в неё и добавить в список чатов
    public static String addGroupChatWithChatname(String chatname) {
        //SqliteApi.getId()

        String chatId = "";



        return chatId;
    }

    //удалить чат из списка чатов
    //    то есть либо выйти из беседы
    //            либо стать неактивным в одиночном чате
    public static void deleteChat(String chatId) {
        //алгоритм на сервере (работа с клиентом (userId) и чатом (chatId)):
        //деление в зависимости от типа чата
        //      если одиночный:
        //          если оба пользователя имеют isCurrentMember=true, то у клиента присвоить isCurrentMember=false
        //          иначе чат удаляется с сервера
        //      если групповой:
        //          если кроме клиента у всех isCurrentMember=false, то удалить чат с сервера
        //          иначе у клиента присвоить isCurrentMember=false

        //временный код
        for (int i = 0; i < chats.size(); i++) {
            if (chatId.equals(chats.get(i).getId())) {
                chats.remove(i);
                break;
            }
        }
    }
}