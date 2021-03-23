package bsuir.sidorovich.pigeon.model;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.model.chat_hierarchy.Chat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.GroupChat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.SingleChat;

//моё видение сервера после разработки списка чатов (Полеков)
//
//есть две БД: пользователи и чаты
//
//---------------------------------------------------------------------------------------------
//
//Пользователи
//
//кто входит - понятно
//добавление пользователя - через регистрацию, удаление - по кнопке в профиле
//минимальные характеристики:
//      userId (главная характеристика, уникальная для каждого)
//      username (вместо имени и фамилии единое поле)
//      пароль
//доп характеристики
//      email (можно добавить для разнообразия, но механики не будет)
//      фото или ссылка на него (врядли успеем реализовать, но если это легко, можно попробовать)
//
//      другие поля, с которыми я не работал...
//
//------------------------------------------------------------------------------------------------
//
//Чаты
//
//в широком смысле - место, где хранятся сообщения
//у каждого чата есть тип (см ниже)
//создание и удаление чатов - на странице локального списка чатов клиента (разрабатывал Полеков, )
//
//минимальные характеристики:
//      chatId (главная характеристика, уникальная для каждого)
//          как уже говорили, чтобы отличать от пользователя, можно начинать с "chat_"
//          в простейшем случае chatId можно использовать как инвайт-код в беседу, логика от этого не разрушится
//      тип чата:
//          - Single (1 на 1)
//          - Group (беседа)
//          - Discussion (короткое обсуждение какого-то вопроса, будущая фича)
//      chatname (название) - в зависимости от типа
//          - Single - пустая строка
//                  для сервера оба пользователя в этом чате равноправны
//                  поэтому когда какой-то пользователь запрашивает данные этого чата для записи в локальный список чатов,
//                  на место chatname становится username второго пользователя
//                  можно при отправке к username добавить id этого пользователя (так как поле chatId используется)
//                  так как поле chatId для самого клиента имеет смысл только если это беседа (это инвайт-код)
//                  если же чат одиночный, то пользователю не интересен chatId, а интересен userId второго пользователя
//                  но нельзя при передаче на место chatId поставить userId, так как chatId нужно для адресации
//          - Group - название при создании
//          - Discussion - тема обсуждения
//      полный список пользователей
//          каждый пользователь содержит минимум:
//              - userId существующего пользователя
//              - bool переменную isCurrentMember (описание ниже)
    //isCurrentMember - характеристика пользователя в контексте чата
    //если true -   пользователь имеет чат в своём списке чатов (то есть он может писать сообщения)
    //если false -  пользователь удалил чат из своего списка чатов (то есть он не может писать сообщения),
    //              но из самого чата он как участник не удаляется, а переходит в состояние "неактивного",
    //              чтобы в будущем можно было брать информацию о нём
    //суть - если есть хотя бы 1 участник не удалил чат из своего списка чатов, то чат не удаляется из БД на сервере, если 0 - удаляется
//      количество участников
//          это количество пользователей из полного списка, у которых isCurrentMember=true
//доп характеристики
//      фото чата или ссылка на него
//
//      другие поля, с которыми я не работал (список сообщений и др.)...

// класс, содержащий статические методы взаимодействия с сервером
// при необходимости api будет реализовано в другом виде
public class ServerApi {

    //временные хранилища, играющие роль sql на сервере, пока нет подключения
    //эти хранилища НЕ ПОЛНОСТЬЮ соответствуют тем, что будут на сервере
    public static ArrayList<GroupChat> groupChats = new ArrayList<>();
    public static ArrayList<Chat> chats = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static int chatIdCounter = 1;
    public static int userIdCounter = 1001;

    // в будущем этого метода НЕ БУДЕТ, так как в других методах будет прямое обращение к серверу
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
        for (int i = 0; i < 4; i++) {
            GroupChat chat = new GroupChat();
            chat.setChatname("g_name_" + chatIdCounter);
            chat.setId("c_id_" + chatIdCounter);
            chat.setMembersCount(10 + i);
            groupChats.add(chat);
            chatIdCounter++;
        }
    }

    //получить от сервера список чатов, в которых состоит пользователь
    //цель - отобразить список
    public static ArrayList<Chat> getChats() {
        //алгоритм на сервере (работа с userId клиента):
        //сервер ищет, в каких чатах состоит userId и имеет там поле isCurrentMember=true
        //сервер возвращает список чатов
        //
        //каждый чат содержит несколько параметров
        //в реальных мессенджерах такие:
        //chatname, chatId (основной уникальный параметр), фото, последнее сообщение (время + текст), возможно уведомление
        //
        //минимум нужно передать первые 2 параметра, без них невозможно
        //пояснения к chatname:
        //одно из полей БД - тип чата: single or group
        //если group, то  chatname = chatname из БД (то есть это название беседы)
        //если single, то chatname = username второго пользователя (chatname в БД пуст) (возможно при передаче стоит сделать append(userID), чтобы chatID заменить на userID для наглядности)


        return chats;
    }

    //метод возвращает список пользователей с именем или id, которое содержит заданную строку
    public static ArrayList<User> getUsersByText(String text) {
        //алгоритм на сервере - найти среди всех пользователей тех, имя или id которого соответствует требованиям, и вернуть этот список
        //минимальные данные каджого пользователя при возврате:
        //      username
        //      userId

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
        //получить список одиночных чатов, в которых присутствует клиент
        //искать чат с userId
        //      если такой чат найден, проверить isCurrentMember для клиента
        //          если true, то выход
        //          если false, то сделать true
        //      если чат не найден, то создать его, для клиента isCurrentMember=true, для userId isCurrentMember=false
        //полученный id чата вернуть

        //временный код
        //не предусмотрен переход на уже существующий чат
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
    public static String createGroupChatWithChatname(String chatname) {
        //алгоритм на сервере (работа с клиентом (userId)):
        //в общий список чатов добавить чат с параметрами:
        //      - тип чата - group
        //      - chatname
        //      - сгенерированный уникальный id
        //      - в списке участников только клиент с параметром isCurrentMember=true
        //id чата вернуть

        //временный код
        Chat chat = new GroupChat();
        chat.setChatname("g_name_" + chatIdCounter + "_" + chatname);
        chat.setId("c_id_" + chatIdCounter);
        chatIdCounter++;
        chats.add(chat);

        return chat.getId();
    }

    //найти на сервере беседу по chatId
    public static GroupChat getGroupChatWithId(String chatId) {
        //алгоритм на сервере (работа с клиентом (userId) и чатом (chatId)):
        //найти в списке чатов chatId (искать только среди чатов типа group)
        //вернуть информацию о чате:
        //      - chatname
        //      - chatId
        //      - количество участников (у которых параметр isCurrentMember=true)
        //      - (возможно в будущем ещё отправить фото)

        //временный код
        for (GroupChat groupChat : groupChats) {
            if (chatId.equals(groupChat.getId())) {
                return groupChat;
            }
        }
        return null;
    }

    //вступить в беседу и добавить её в список чатов
    public static boolean joinGroupChatWithId(String chatId) {
        //алгоритм на сервере (работа с клиентом (userId) и чатом (chatId)):
        //найти в списке чатов chatId (искать только среди чатов типа group)
        //искать в участниках userId
        //      если найден
        //          если у пользователя с userId параметр isCurrentMember=true, то вернуть false (то есть он уже вступил)
        //          иначе (когда isCurrentMember=false) сделать isCurrentMember=true
        //      если не найден
        //          добавить в список участников и установить isCurrentMember=true
        //вернуть true

        //временный код
        for (Chat chat : chats) {
            if (chatId.equals(chat.getId())) {
                return false;
            }
        }
        chats.add(getGroupChatWithId(chatId));
        return true;
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