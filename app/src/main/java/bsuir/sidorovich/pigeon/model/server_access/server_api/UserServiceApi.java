package bsuir.sidorovich.pigeon.model.server_access.server_api;

import bsuir.sidorovich.pigeon.model.server_access.entities.UserEntity;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.ChatControllerInterface;
import bsuir.sidorovich.pigeon.model.server_access.interfaces.UserControllerInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserServiceApi {
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
}
