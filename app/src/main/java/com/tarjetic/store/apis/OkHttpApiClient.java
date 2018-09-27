package com.tarjetic.store.apis;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by bootavo on 2/09/2017.
 */

public class OkHttpApiClient {

    public static OkHttpClient okHttpClient = null;

    public static OkHttpClient getOkHttpClient(){
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
                                            //.addHeader("Accept", "Application/JSON")
                                            //.addHeader("Content-Type","multipart/form-data")
                                            .addHeader("Content-Type","application/json")
                                            .build();
                                    return chain.proceed(request);
                                }
                            })
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS).build();
        }
        return okHttpClient;
    }

}
