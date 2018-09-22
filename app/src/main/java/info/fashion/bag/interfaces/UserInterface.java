package info.fashion.bag.interfaces;

import android.support.annotation.Nullable;

import java.util.Map;

import info.fashion.bag.models.JsonRequest;
import info.fashion.bag.models.Token;

import info.fashion.bag.models.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by gtufinof on 3/12/18.
 */

public interface UserInterface {

    @POST("login")
    Call<JsonRequest> login(
            @Body User user
    );

    @Multipart
    @POST("user/")
    Call<JsonRequest> createUser2(
            @Part("nombres") RequestBody nombres,
            @Part("apellidos") RequestBody apellidos,
            @Part("usuario") RequestBody usuario,
            @Part("clave") RequestBody clave,
            @Part("direccion") RequestBody direccion,
            @Part("dni") RequestBody dni,
            @Part("telefono_contacto") RequestBody telefono_contacto,
            @Part("correo") RequestBody correo,
            @Part("id_rol") RequestBody id_rol,
            @Part("tipo_registro") RequestBody tipo_registro,
            @Part("codigo_app") RequestBody codigo_app);

    @Multipart
    @POST("user/")
    Call<JsonRequest> createUser(
            @Part MultipartBody.Part imagen,
            @Part("nombres") RequestBody nombres,
            @Part("apellidos") RequestBody apellidos,
            @Part("usuario") RequestBody usuario,
            @Part("clave") RequestBody clave,
            @Part("direccion") RequestBody direccion,
            @Part("dni") RequestBody dni,
            @Part("telefono_contacto") RequestBody telefono_contacto,
            @Part("correo") RequestBody correo,
            @Part("id_rol") RequestBody id_rol,
            @Part("tipo_registro") RequestBody tipo_registro,
            @Part("codigo_app") RequestBody codigo_app
    );

    @Headers("Content-Type: application/json")
    @PATCH("users/{id}/")
    Call<JsonRequest> updateUser(
            @Header("Authorization") String authorization,
            @Path("id") int id,
            @Body Map<String, Object> body);

    @PUT("change_password/{id_usuario}")
    Call<JsonRequest> changePassword(
            @Path("id_usuario") int id_usuario,
            @Body User user
    );

    @GET("user_by_code/{codigo}")
    Call<JsonRequest> getUserbyPromoCode(
            @Path("codigo") String codigo
    );

    @GET("verify_user/{usuario}")
    Call<JsonRequest> verifyUser(
            @Path("usuario") String usuario
    );

}
