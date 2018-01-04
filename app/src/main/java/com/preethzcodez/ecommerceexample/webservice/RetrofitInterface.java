package com.preethzcodez.ecommerceexample.webservice;

import com.preethzcodez.ecommerceexample.pojo.ResponseJSON;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Preeth on 1/4/18
 */

public interface RetrofitInterface {

    // Method To Fetch Data From URL
    @GET("json")
    Call<ResponseJSON> fetchData();
}
