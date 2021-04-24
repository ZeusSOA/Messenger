package bsuir.sidorovich.pigeon.model.server_access.server_api;

import android.util.Log;

import bsuir.sidorovich.pigeon.model.server_access.entities.MessageEntity;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.ChatControllerInterface;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.MessageControllerInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MessageServiceApi {

    private static String testResponse = "empty";
    private static MessageEntity messageEntityResponse = null;

    //метод приёма сообщений чата
    public static String getMessages() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.237.150.75:2345/chat/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MessageControllerInterface service = retrofit.create(MessageControllerInterface.class);

        Call<Iterable<MessageEntity>> call = service.getChat(123L);

        call.enqueue(new Callback<Iterable<MessageEntity>>() {
            @Override
            public void onResponse(Call<Iterable<MessageEntity>> call, Response<Iterable<MessageEntity>> response) {
                if (response.isSuccessful()) {
                    testResponse = "! successful !";

                    for(MessageEntity entity:response.body()){
                        //
                    }

                } else {
                    testResponse = "NOT Successful: " + response.code();
                }
            }

            @Override
            public void onFailure(Call<Iterable<MessageEntity>> call, Throwable t) {
                testResponse =  "fail!";
                Log.e("Message", t.getMessage());
            }
        });
        return testResponse;
    }
    //метод отправки сообщения
    public static String sendMessage() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://35.237.150.75:2345/message/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MessageControllerInterface service = retrofit.create(MessageControllerInterface.class);

        Call<MessageEntity> call = service.createMessage(new MessageEntity());

        call.enqueue(new Callback<MessageEntity>() {
            @Override
            public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response) {
                if (response.isSuccessful()) {
                    testResponse = "! successful !";
                   messageEntityResponse = response.body();

                } else {
                    testResponse = "NOT Successful: " + response.code();
                }
            }

            @Override
            public void onFailure(Call<MessageEntity> call, Throwable t) {
                testResponse =  "fail!";
                Log.e("Message", t.getMessage());
            }
        });
        return testResponse;
    }

}
