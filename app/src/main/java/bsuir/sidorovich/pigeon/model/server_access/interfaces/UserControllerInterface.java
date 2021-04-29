package bsuir.sidorovich.pigeon.model.server_access.interfaces;

import java.util.ArrayList;

import bsuir.sidorovich.pigeon.model.server_access.entities.UserEntity;
import bsuir.sidorovich.pigeon.model.server_access.entities.ChatEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserControllerInterface {
    @GET("{id}")
    Call<UserEntity> getUserById(@Path("id") Long id);

    @GET("name/{name}")
    Call<ArrayList<UserEntity>> getUserByName(@Path("name") String name);

    @GET("{userId}/chat")
    Call<ArrayList<ChatEntity>> getUserChat(@Path("userId") Long userId);

    @POST(".")
    Call<UserEntity> createUser(@Body UserEntity userEntity);
}
