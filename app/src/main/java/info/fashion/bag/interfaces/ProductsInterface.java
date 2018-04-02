package info.fashion.bag.interfaces;

import info.fashion.bag.models.JsonProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gtufinof on 3/13/18.
 */

public interface ProductsInterface {

    @GET("catalog/?product__is_offer=True&ordering=-created")
    Call<JsonProducts> getGeneralProducts();

    @GET("catalog/?product__is_offer=True&product__product_type__category=1")
    Call<JsonProducts> getOffersBags();

    @GET("catalog/?product__is_offer=True&product__product_type__category=2")
    Call<JsonProducts> getOfferJewels();

    @GET("catalog/")
    Call<JsonProducts> getSearchResult(
            @Query("search") String search
    );

}
