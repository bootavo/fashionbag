package info.fashion.bag.interfaces;

import java.util.Map;

import info.fashion.bag.models.JsonSalesOrders;
import info.fashion.bag.models.JsonUser;
import info.fashion.bag.models.SalesOrders;
import info.fashion.bag.models.SalesOrdersDatails;
import info.fashion.bag.models.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by gtufinof on 3/12/18.
 */

public interface SalesOrdersInterface {

    @GET("sales-orders/")
    Call<JsonSalesOrders> getSalesOrders(
            @Header("Authorization") String authorization);

    @POST("sales-orders/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<SalesOrders> createSalesOrders(
            @Header("Authorization") String authorization,
            @Body Map<String, Object> body
    );

    @POST("sales-order-details/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<SalesOrdersDatails> createSalesOrdersDetails(
            @Header("Authorization") String authorization,
            @Body Map<String, Object> body
    );

}
