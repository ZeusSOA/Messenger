package bsuir.sidorovich.pigeon.model.server_access.interfaces;

import bsuir.sidorovich.pigeon.model.server_access.entities.ChatEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.TestEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatControllerInterface {
    @POST("test")
    Call<String> test(@Body String testRequest);

    @POST("object")
    Call<TestEntity> testObject(@Body TestEntity testEntity);

    @GET("{id}")
    Call<ChatEntity> getChatById(@Path("id") Long id);
}
