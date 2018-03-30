package info.fashion.bag.interfaces;

import java.util.Map;

import info.fashion.bag.models.JsonPromotion;
import info.fashion.bag.models.JsonSalesOrders;
import info.fashion.bag.models.Promotion;
import info.fashion.bag.models.SalesOrders;
import info.fashion.bag.models.SalesOrdersDatails;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by gtufinof on 3/12/18.
 */

public interface PromotionInterface {

    @GET("promotion/")
    Call<JsonPromotion> getPromotions(
            @Header("Authorization") String authorization);

    @DELETE("promotion/{id}")
    Call<Promotion> deletePromotion(
            @Header("Authorization") String authorization,
            @Path("id") int id);

}
