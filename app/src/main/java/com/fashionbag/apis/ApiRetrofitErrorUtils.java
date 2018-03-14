package com.fashionbag.apis;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by bootavo on 26/08/2017.
 */

public class ApiRetrofitErrorUtils {

    public static ApiRetrofitError parseError(Response<?> response){
        Converter<ResponseBody, ApiRetrofitError> converter = ApiRetrofitClient.retrofit.responseBodyConverter(ApiRetrofitError.class, new Annotation[0]);
        ApiRetrofitError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e){
            return new ApiRetrofitError();
        }
        return error;
    }

}
