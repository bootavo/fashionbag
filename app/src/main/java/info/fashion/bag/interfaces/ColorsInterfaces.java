package info.fashion.bag.interfaces;

import info.fashion.bag.models.Color;
import info.fashion.bag.models.Variant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gtufinof on 3/21/18.
 */

public interface ColorsInterfaces {

    @GET("colors/{id}")
    Call<Color> getVariant(
            @Path("id") int id
    );

}
