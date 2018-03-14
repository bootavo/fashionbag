package info.fashion.bag.interfaces;

import info.fashion.bag.models.JsonUser;
import info.fashion.bag.models.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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

}