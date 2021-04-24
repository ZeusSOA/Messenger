package bsuir.sidorovich.pigeon.model.server_access.interfaces;

import java.util.Iterator;

import bsuir.sidorovich.pigeon.model.server_access.entities.ChatEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.MessageEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageControllerInterface {

    @GET("/{chatId}/message")
    Call<Iterable<MessageEntity>> getChat(@Path("id") Long chatId);

    @POST
    Call<MessageEntity> createMessage(@Body MessageEntity messageEntity);
}
