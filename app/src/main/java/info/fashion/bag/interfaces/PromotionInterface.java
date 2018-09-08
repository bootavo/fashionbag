package info.fashion.bag.interfaces;

import info.fashion.bag.models.JsonRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gtufinof on 3/12/18.
 */

public interface PromotionInterface {

    //@DELETE("promotion/{id}")
    //Call<Promotion> deletePromotion(
    //        @Path("id") int id);

    @GET("promotion/{id_promotion}")
    Call<JsonRequest> getPromotionById(
            @Path("id_promotion") int id_promotion
    );

    @GET("promotion")
    Call<JsonRequest> getPromotiones();

}
