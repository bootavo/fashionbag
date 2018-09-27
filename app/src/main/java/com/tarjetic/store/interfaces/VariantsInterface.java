package com.tarjetic.store.interfaces;

import com.tarjetic.store.models.Variant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gtufinof on 3/21/18.
 */

public interface VariantsInterface {

    @GET("variants/{id}")
    Call<Variant> getVariant(
        @Path("id") int id
    );

}
