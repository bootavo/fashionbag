package com.tarjetic.store.interfaces;

import com.tarjetic.store.models.JsonRequest;
import com.tarjetic.store.models.TarjeticCode;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by gtufinof on 3/13/18.
 */

public interface TarjeticCodeInterface {

    @GET("verify_code/{codigo}")
    Call<JsonRequest> verifyCode(
            @Path("codigo") String codigo
    );

    @PUT("retrieve_code/{id_usuario}")
    Call<JsonRequest> retrieveCoins(
            @Path("id_usuario") int id_usuario, @Body TarjeticCode tarjeticCode
    );

}
