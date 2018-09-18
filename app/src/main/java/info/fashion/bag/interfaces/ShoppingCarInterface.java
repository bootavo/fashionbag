package info.fashion.bag.interfaces;

import info.fashion.bag.models.JsonRequest;
import info.fashion.bag.models.ShoppingCar;
import info.fashion.bag.models.ShoppingCarItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShoppingCarInterface {


    @POST("shopping_car/")
    Call<JsonRequest> createShoppingCar(
            @Body ShoppingCar shoppingCar);

    @GET("shopping_car/{id_usuario}")
    Call<JsonRequest> getShoppingCarByUser(
            @Path("id_usuario") int id_usuario);

    @GET("shopping_car_items/{id_usuario}/{id_carrito_compra}")
    Call<JsonRequest> getShoppingItems(
            @Path("id_usuario") int id_usuario,
            @Path("id_carrito_compra") int id_carrito_compra);

    @POST("shopping_car_items/{product_type}")
    Call<JsonRequest> addItemsToShoppingCar(
            @Path("product_type") String product_type, @Body ShoppingCarItem shoppingCarItem);


    @DELETE("shopping_car_items/{id_carrito_compra_producto}")
    Call<JsonRequest> deleteProduct(
            @Path("id_carrito_compra_producto") Integer id_carrito_compra_producto
    );

}
