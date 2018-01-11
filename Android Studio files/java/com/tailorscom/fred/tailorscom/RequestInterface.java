package com.tailorscom.fred.tailorscom;

import com.tailorscom.fred.tailorscom.models.ServerRequest;
import com.tailorscom.fred.tailorscom.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("TailorsCom-login-register/")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
