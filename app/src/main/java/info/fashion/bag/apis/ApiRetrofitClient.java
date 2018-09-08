package info.fashion.bag.apis;

import info.fashion.bag.utilities.Constant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by bootavo on 26/08/2017.
 */

public class ApiRetrofitClient {

    public static final String BASE_URL = Constant.BASE_URL;
    //public static final String BASE_URL = "https://gustavo-tarjetic-1-0-0.wso2apps.com/tarjetic/";
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
