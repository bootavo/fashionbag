package com.tarjetic.store.interfaces;

import com.tarjetic.store.models.JsonRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gtufinof on 3/13/18.
 */

public interface ProductsInterface {

    @GET("product_filter/{filter}")
    Call<JsonRequest> getProductByFilter(
            @Path("filter") String filter
    );

    @GET("product_filter_category/{id_categoria}")
    Call<JsonRequest> getProductByCategory(
            @Path("id_categoria") int id_categoria
    );

    @GET("product/{id_producto}")
    Call<JsonRequest> getProductById(
            @Path("id_producto") int id_producto
    );

    @GET("product")
    Call<JsonRequest> getProducts();


}
