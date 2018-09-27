package com.tarjetic.store.interfaces;

import com.tarjetic.store.models.JsonRequest;
import com.tarjetic.store.models.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by gtufinof on 3/12/18.
 */

public interface OrderInterface {

    @GET("order_user/{id_usuario}")
    Call<JsonRequest> getOrdersByUsuario(
            @Path("id_usuario") Integer id_usuario);

    @POST("order")
    Call<JsonRequest> registerOrder(
            @Body Order order);

    /*
    @GET("sales-orders/")
    Call<JsonSalesOrders> getSalesOrders();

    @POST("sales-orders/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<SalesOrders> createSalesOrders(
            @Body Map<String, Object> body
    );

    @POST("sales-order-details/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<SalesOrdersDatails> createSalesOrdersDetails(
            @Body Map<String, Object> body
    );

    @GET("sales-order-details/")
    Call<JsonSalesOrdersDetails> getSalesOrdersDetails(
            @Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @PATCH("sales-order-details/{id}/")
    Call<SalesOrdersDatails> updateSalesOrderDetail(
            @Path("id") int id,
            @Body Map<String, Object> body);

    @GET("sales-order-details/{id}")
    Call<JsonSalesOrders> getSaleOrderReserve(
            @Query("sales-orders") int sales_orders);

    @GET("sales-order-details/")
    Call<JsonSalesOrdersDetails> getReserve(
            @Query("sale_order") int sale_order);
            */

}
