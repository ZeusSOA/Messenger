package bsuir.sidorovich.pigeon.model.server_access.interfaces;

import bsuir.sidorovich.pigeon.model.server_access.entities.UserEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserControllerInterface {
    @GET("id/{id}")
    Call<UserEntity> getUserById(@Path("id") Long id);
}
