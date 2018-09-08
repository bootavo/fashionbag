package info.fashion.bag.interfaces;

import info.fashion.bag.models.JsonRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gtufinof on 3/13/18.
 */

public interface ProductsInterface {

    @GET("catalog/?product__is_offer=True&product__product_type__category=1")
    Call<JsonRequest> getOffersBags();

    @GET("catalog/?product__is_offer=True&product__product_type__category=2")
    Call<JsonRequest> getOfferJewels();



    @GET("product_filter/{filter}")
    Call<JsonRequest> getProductByFilter(
            @Path("filter") String filter
    );

    @GET("product/{id_producto}")
    Call<JsonRequest> getProductById(
            @Path("id_producto") int id_producto
    );

    @GET("product")
    Call<JsonRequest> getProducts();

}
