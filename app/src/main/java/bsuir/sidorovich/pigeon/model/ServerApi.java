package bsuir.sidorovich.pigeon.model;

// класс, содержащий статические методы взаимодействия с сервером
// при необходимости api будет реализовано в другом виде

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.model.chat_hierarchy.Chat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.GroupChat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.SingleChat;

public class ServerApi {

    //получить от сервера список чатов, в которых состоит пользователь
    public static ArrayList<Chat> getChats() {
        //отправить свой userID серверу
        //сервер ищет, в каких чатах состоит userID
        //сервер возвращает список чатов с chatID

        //одно из полей БД - тип чата: single or group
        //если group, то  chatname = chatname из БД
        //если single, то chatname = username второго пользователя (chatname в БД пуст)

        //ChatView:
        //название, id чата
        //фото, последнее сообщение + время, уведомление

        //временный код
        ArrayList<Chat> chats = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i < 10) {
                Chat chat = new SingleChat();
                chat.setChatname("username" + (i + 1));
                chat.setId("id" + (i + 1001));
                chats.add(chat);
            } else {
                Chat chat = new GroupChat();
                chat.setChatname("ВМСиС 8505" + (i + 1));
                chat.setId("id" + (i + 1001));
                chats.add(chat);
            }

        }
        return chats;
    }
}
