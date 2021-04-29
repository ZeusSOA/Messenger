package bsuir.sidorovich.pigeon.model.server_access.server_api;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import bsuir.sidorovich.pigeon.model.User;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.Chat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.GroupChat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.SingleChat;
import bsuir.sidorovich.pigeon.model.server_access.entities.ChatEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.UserEntity;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.ChatControllerInterface;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.UserControllerInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static bsuir.sidorovich.pigeon.model.server_access.entities.ChatType.GROUP;
import static bsuir.sidorovich.pigeon.model.server_access.entities.ChatType.SINGLE;

public class UserServiceApi {
    public static Long selfUsetId= 2L;

    private static String testResponse = "empty";


    //метод получения пользователя по id
    //взаимодействует с БД и НЕ РАБОТАЕТ (протестировать, когда будет решена проблема с зацикливанием на сервере)
    //+ не работает даже в браузере
    public static String test() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.237.150.75:2345/user/")
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserControllerInterface service = retrofit.create(UserControllerInterface.class);

        Call<UserEntity> call = service.getUserById(1L);
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
//                testResponse =  response.body() == null ? "null object" : "FULL OBJECT !";
//                testResponse += response.isSuccessful() ? "\nSuccessful" : "\nNOT successful";

                if (response.isSuccessful()) {
                    testResponse = "! successful !";
                } else {
                    testResponse = "NOT Successful: " + response.code();
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                testResponse =  "fail!";
            }
        });

        return testResponse;
    }

    ////////////////////////////////////////////////


    //получить от сервера список чатов, в которых состоит пользователь (он есть в UserEntity)
    //цель - отобразить список
    public ArrayList<Chat> getChats() {
        UserControllerInterface service = new Retrofit
                .Builder()
                .baseUrl("http://35.237.150.75:2345/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserControllerInterface.class);

        Call<ArrayList<ChatEntity>> call = service.getUserChat(UserServiceApi.selfUsetId);
        final ArrayList<Response<ArrayList<ChatEntity>>> response = new ArrayList<>();
        Runnable task = () -> {
            try {
                response.add(call.execute());
            }
            catch (Exception ex) {}
            synchronized(this) {
                notifyAll();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        synchronized(this) {
            try {
                wait(3000);
            } catch(InterruptedException ie){}
        }

        ArrayList<Chat> chats = new ArrayList<>();
        for (ChatEntity entity : response.get(0).body()) {
            Chat chat = null;
            if (entity.getChatType() == GROUP) {
                chat = new GroupChat();
                chat.setChatname(entity.getChatName());
                chat.setId("@" + entity.getId());
            } else if (entity.getChatType() == SINGLE) {
                chat = new SingleChat();
//                int secondUserIndex = 0;
//                if (entity.getUsers().get(0).getId() == UserServiceApi.selfUsetId) {
//                    secondUserIndex = 1;
//                }
//                chat.setChatname(entity.getUsers().get(secondUserIndex).getUsername());
//                chat.setId("" + entity.getUsers().get(secondUserIndex).getId());
                chat.setChatname(entity.getChatName());
                chat.setId("@" + entity.getId());
            }
            chats.add(chat);
        }

        return chats;
    }


    public UserEntity getUserEntityById(Long id) {
        UserControllerInterface service = new Retrofit
                .Builder()
                .baseUrl("http://35.237.150.75:2345/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserControllerInterface.class);

        Call<UserEntity> call = service.getUserById(id);
        final ArrayList<Response<UserEntity>> response = new ArrayList<>();
        Runnable task = () -> {
            try {
                response.add(call.execute());
            }
            catch (Exception ex) {}
            synchronized(this) {
                notifyAll();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        synchronized(this) {
            try {
                wait(3000);
            } catch(InterruptedException ie){}
        }

        return response.get(0).body();
    }

    public User getUserById(Long id) {
        UserEntity entity = getUserEntityById(id);
        User user = new User();
        user.setId(new Long(entity.getId()).toString());
        user.setUsername(entity.getUsername());
        return user;
    }

    public ArrayList<User> getUserByName(String name) {
        UserControllerInterface service = new Retrofit
                .Builder()
                .baseUrl("http://35.237.150.75:2345/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserControllerInterface.class);

        Call<ArrayList<UserEntity>> call = service.getUserByName(name);
        final ArrayList<Response<ArrayList<UserEntity>>> response = new ArrayList<>();
        Runnable task = () -> {
            try {
                response.add(call.execute());
            }
            catch (Exception ex) {}
            synchronized(this) {
                notifyAll();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        synchronized(this) {
            try {
                wait(3000);
            } catch(InterruptedException ie){}
        }

        ArrayList<User> users = new ArrayList<>();
        for (UserEntity entity : response.get(0).body()) {
            User user = new User();
            user.setId(new Long(entity.getId()).toString());
            user.setUsername(entity.getUsername());
            users.add(user);
        }
        return users;
    }

    public String test_createUser() {
        UserControllerInterface service = new Retrofit
                .Builder()
                .baseUrl("http://35.237.150.75:2345/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserControllerInterface.class);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("client_testuser");
        userEntity.setEmail("aa@aa.aa");
        userEntity.setPassword("blablabla");
        userEntity.setId(56L);

        Call<UserEntity> call = service.createUser(userEntity);
        final ArrayList<Response<UserEntity>> response = new ArrayList<>();
        Runnable task = () -> {
            try {
                response.add(call.execute());
            }
            catch (Exception ex) {}
            synchronized(this) {
                notifyAll();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        synchronized(this) {
            try {
                wait(3000);
            } catch(InterruptedException ie){}
        }

        String string = "user\n" + response.get(0).body().getId() + "\n" + response.get(0).body().getUsername();
        return string;
    }
}
