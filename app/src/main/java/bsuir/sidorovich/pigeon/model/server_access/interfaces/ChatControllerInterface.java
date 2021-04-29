package bsuir.sidorovich.pigeon.model.server_access.interfaces;

import java.util.ArrayList;
import java.util.List;

import bsuir.sidorovich.pigeon.model.server_access.entities.ChatEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.MessageEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.TestEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.UserEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatControllerInterface {
    @POST("test")
    Call<String> test(@Body String testRequest);

    @POST("object")
    Call<TestEntity> testObject(@Body TestEntity testEntity);

    @GET("{id}")
    Call<ChatEntity> getChatById(@Path("id") Long id);

    /////////////////////////

//    @GET("user/{userId}")
//    Call<ArrayList<ChatEntity>> getChatByUserId(@Path("userId") Long userId);

    @GET("{chatId}/message")
    Call<ArrayList<MessageEntity>> getChat(@Path("chatId") Long chatId);

    @POST(".")
    Call<ChatEntity> createChat(@Body ChatEntity chatEntity);

    @PATCH("{chatId}/user")
    Call<Void> addUserById(@Path("chatId") Long chatId, @Body UserEntity user);

    @DELETE("{id}")
    Call<Void> deleteChat(@Path("id") Long id);
}
