package bsuir.sidorovich.pigeon.model.server_access.server_api;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bsuir.sidorovich.pigeon.R;
import bsuir.sidorovich.pigeon.activities.ChatActivity;
import bsuir.sidorovich.pigeon.model.Message;
import bsuir.sidorovich.pigeon.model.server_access.entities.ChatEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.MessageEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.TestEntity;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.ChatControllerInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
            private ChatEntity chatEntity;

            @Override
            public void onResponse(Call<ChatEntity> call, Response<ChatEntity> response) {
 this.chatEntity = response.body();
                testResponse = "! successful !";
System.out.println("alfkds;lf;sdfmsdfklsjkdfl");
                if (response.isSuccessful()) {
                    testResponse = "! successful !";
//                    TestEntity responseEntity = response.body();
//                    testResponse = responseEntity.str + "\n" + responseEntity.number;
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

    public static ChatEntity getMessagesFromChatById(long chatId, long userId)  {

         ArrayList<ChatEntity> listOfMessages = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.237.150.75:2345/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ChatControllerInterface service = retrofit.create(ChatControllerInterface.class);

        Call<ChatEntity> call = service.getChatById(1L);

call.enqueue(new Callback<ChatEntity>() {
    @Override
    public void onResponse(Call<ChatEntity> call, Response<ChatEntity> response) {
        System.out.println("alfkds;lf;sdfmsdfklsjkdfl"+response.body());
      //  for (MessageEntity messageEntity:response.body().messages) {
            listOfMessages.add(response.body());
        //}
    }

    @Override
    public void onFailure(Call<ChatEntity> call, Throwable t) {

    }
});

return listOfMessages.get(0);

    }


}
