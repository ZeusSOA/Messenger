package bsuir.sidorovich.pigeon.model.server_access.server_api;

import android.util.Log;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.activities.ChatActivity;
import bsuir.sidorovich.pigeon.model.Message;
import bsuir.sidorovich.pigeon.model.server_access.entities.ChatEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.MessageEntity;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.ChatControllerInterface;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.MessageControllerInterface;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MessageServiceApi {

    private static String testResponse = "empty";

    //метод приёма сообщений чата
    public static ArrayList<MessageEntity> getMessagesFromChatById(long chatId, ChatActivity activity){

        final ArrayList<MessageEntity> listOfMessages = new ArrayList<>();

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

                    for(MessageEntity messageEntity:response.body().messages){
                        listOfMessages.add(messageEntity);
                    }
                    try {
                        activity.fillHistoryOfMessages(listOfMessages);
                        activity.setTitle(response.body().chatName);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                } else {
                    testResponse = "NOT Successful: " + response.code();
                }
            }

            @Override
            public void onFailure(Call<ChatEntity> call, Throwable t) {
                testResponse =  "fail!";
                Log.e("Message", t.getMessage());
            }
        });

        return listOfMessages;
    }
    //метод отправки сообщения
    public static String sendMessage(MessageEntity messageEntity) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.237.150.75:2345/message/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MessageControllerInterface service = retrofit.create(MessageControllerInterface.class);

        Call<MessageEntity> call = service.createMessage(messageEntity);

        call.enqueue(new Callback<MessageEntity>() {
            @Override
            public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {

                if (response.isSuccessful()) {
                    testResponse = "! successful !";

                } else {
                    testResponse = "NOT Successful: " + response.code();
                    System.out.println("NOT Successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {

                System.out.println("message sent123");

                testResponse =  "fail!";
                Log.e("Message", t.getMessage());
            }
        });
        return testResponse;
    }
}
