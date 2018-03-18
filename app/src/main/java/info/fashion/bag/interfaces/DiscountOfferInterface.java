package info.fashion.bag.interfaces;

import info.fashion.bag.models.JsonUniqueDiscountOffer;
import info.fashion.bag.models.JsonUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by gtufinof on 3/16/18.
 */

public interface DiscountOfferInterface {

    @GET("unique-discount-offer/?is_active=True/")
    Call<JsonUniqueDiscountOffer> getDiscountOffer();

}
