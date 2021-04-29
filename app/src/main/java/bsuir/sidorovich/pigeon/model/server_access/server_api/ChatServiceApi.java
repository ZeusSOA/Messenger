package bsuir.sidorovich.pigeon.model.server_access.server_api;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bsuir.sidorovich.pigeon.model.User;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.Chat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.GroupChat;
import bsuir.sidorovich.pigeon.model.chat_hierarchy.SingleChat;
import bsuir.sidorovich.pigeon.model.server_access.entities.ChatEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.MessageEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.TestEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.UserEntity;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.ChatControllerInterface;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.UserControllerInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static bsuir.sidorovich.pigeon.model.server_access.ServerApi.chatIdCounter;
import static bsuir.sidorovich.pigeon.model.server_access.entities.ChatType.GROUP;
import static bsuir.sidorovich.pigeon.model.server_access.entities.ChatType.SINGLE;

public class ChatServiceApi {
    private static String testResponse = "empty";

    //метод передачи и приёма строки
    //не взаимодействует с БД и работает
    public static String test() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.237.150.75:2345/chat/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ChatControllerInterface service = retrofit.create(ChatControllerInterface.class);

        Call<String> call = service.test("pigeon");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                testResponse =  response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                testResponse =  "fail!";
            }
        });

        return testResponse;
    }

    //метод передачи и приёма объекта
    //не взаимодействует с БД и работает
    public static String testObject() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.237.150.75:2345/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ChatControllerInterface service = retrofit.create(ChatControllerInterface.class);

        TestEntity testEntity = new TestEntity();
        testEntity.number = 14L;
        testEntity.str = "request";

        Call<TestEntity> call = service.testObject(testEntity);
        call.enqueue(new Callback<TestEntity>() {
            @Override
            public void onResponse(Call<TestEntity> call, Response<TestEntity> response) {
                if (response.isSuccessful()) {
                    TestEntity responseEntity = response.body();
                    testResponse = responseEntity.str + "\n" + responseEntity.number;
                } else {
                    testResponse = "NOT Successful";
                }
            }

            @Override
            public void onFailure(Call<TestEntity> call, Throwable t) {
                testResponse =  "fail!";
            }
        });

        return testResponse;
    }

    //метод получения чата по id
    //взаимодействует с БД и НЕ РАБОТАЕТ (протестировать, когда будет решена проблема с зацикливанием на сервере)
    public static String test_getChatById() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.237.150.75:2345/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ChatControllerInterface service = retrofit.create(ChatControllerInterface.class);

        Call<ChatEntity> call = service.getChatById(1L);
        call.enqueue(new Callback<ChatEntity>() {
            @Override
            public void onResponse(Call<ChatEntity> call, Response<ChatEntity> response) {
                if (response.isSuccessful()) {

                    testResponse = response.body().toString();
                } else {
                    testResponse = "NOT Successful: " + response.code();
//                    Log.e("tag", response.errorBody());
                }
            }

            @Override
                public void onFailure(Call<ChatEntity> call, Throwable t) {
                testResponse =  "fail!";
                    Log.e("Message", t.getMessage());
            }
        });

        return testResponse;
    }

    public static String test_getChat() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.237.150.75:2345/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ChatControllerInterface service = retrofit.create(ChatControllerInterface.class);

        Call<ArrayList<MessageEntity>> call = service.getChat(1L);
        call.enqueue(new Callback<ArrayList<MessageEntity>>() {
            @Override
            public void onResponse(Call<ArrayList<MessageEntity>> call, Response<ArrayList<MessageEntity>> response) {
                if (response.isSuccessful()) {
//                    testResponse = "Successful: ";

                    ArrayList<MessageEntity> list  = (ArrayList<MessageEntity>) response.body();

                    testResponse = list.toString();
                } else {
                    testResponse = "NOT Successful: " + response.code();
                    try {
                        Log.e("tag", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MessageEntity>> call, Throwable t) {
                testResponse =  "fail!";
                Log.e("Message", t.getMessage());
            }
        });

        return testResponse;
    }

    ///////////////////////////



    public String test_createChat() {
        ChatControllerInterface service = new Retrofit
                .Builder()
                .baseUrl("http://35.237.150.75:2345/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ChatControllerInterface.class);


//        ArrayList<UserEntity> members = new ArrayList<>();
//        members.add(new UserServiceApi().getUserEntityById(UserServiceApi.selfUsetId));

        ChatEntity entity = new ChatEntity();
//        entity.setId(32L);
        entity.setChatName("GroupFromClient");
        entity.setChatType(GROUP);
//        entity.setUsers(members);

        Call<ChatEntity> call = service.createChat(entity);
        final ArrayList<Response<ChatEntity>> response = new ArrayList<>();
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

        String string = response.get(0).body().toString();

        return string;
    }


    public void test_addUserById() {
        ChatControllerInterface service = new Retrofit
                .Builder()
                .baseUrl("http://35.237.150.75:2345/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ChatControllerInterface.class);

        Call<Void> call = service.addUserById(3L, new UserServiceApi().getUserEntityById(1L));
        final ArrayList<Response<Void>> response = new ArrayList<>();
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
    }


    public void test_deleteChat() {
        ChatControllerInterface service = new Retrofit
                .Builder()
                .baseUrl("http://35.237.150.75:2345/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ChatControllerInterface.class);

        Call<Void> call = service.deleteChat(3L);
        final ArrayList<Response<Void>> response = new ArrayList<>();
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
    }




}
