package info.fashion.bag.interfaces;

import java.util.Map;

import info.fashion.bag.models.JsonBusinessRules;
import info.fashion.bag.models.JsonSalesOrders;
import info.fashion.bag.models.SalesOrders;
import info.fashion.bag.models.SalesOrdersDatails;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by gtufinof on 3/12/18.
 */

public interface BusinessRulesInterface {

    @GET("business-rules/")
    Call<JsonBusinessRules> getBusinessRules(
            @Header("Authorization") String authorization);

}
