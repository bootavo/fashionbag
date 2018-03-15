package info.fashion.bag.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by bootavo on 26/08/2017.
 */

public class ApiRetrofitClient {

    public static final String BASE_URL = "https://www.fashionbagperu.com/api/";
    //public static final String BASE_URL = "http://192.168.0.5/api/";
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(OkHttpApiClient.getOkHttpClient())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
