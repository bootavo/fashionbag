package info.fashion.bag.interfaces;

import java.util.Map;

import info.fashion.bag.models.JsonUser;
import info.fashion.bag.models.Token;

import info.fashion.bag.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by gtufinof on 3/12/18.
 */

public interface UserInterface {

    @POST("login/")
    @FormUrlEncoded
    Call<Token> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("users/")
    Call<JsonUser> getUserData(
            @Header("Authorization") String authorization);

    @POST("user/create/")
    Call<Token> createUser(
            @Body Map<String, Object> body
    );

    @Headers("Content-Type: application/json")
    @PATCH("users/{id}/")
    Call<User> updateUser(
            @Header("Authorization") String authorization,
            @Path("id") int id,
            @Body Map<String, Object> body);

}
