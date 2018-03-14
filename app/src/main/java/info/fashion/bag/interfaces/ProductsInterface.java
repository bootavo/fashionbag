package info.fashion.bag.interfaces;

import info.fashion.bag.models.JsonProducts;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gtufinof on 3/13/18.
 */

public interface ProductsInterface {

    @GET("catalog/?product__is_offer=True&ordering=-created")
    Call<JsonProducts> getGeneralProducts();

    @GET("catalog/?product__is_offer=True&ordering=-created")
    Call<JsonProducts> getOffersBags();

    @GET("catalog/?product__is_offer=True&ordering=-created")
    Call<JsonProducts> getOfferJewels();

}
