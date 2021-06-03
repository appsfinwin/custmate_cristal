package com.finwin.cristal.custmate.retrofit;


import com.finwin.cristal.custmate.home.transfer.add_beneficiary.pojo.GetIfscResponse;
import com.finwin.cristal.custmate.pojo.Response;
import com.finwin.cristal.custmate.sign_up.sign_up.pojo.ApiKeyResponse;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("custRequestingTransaction")
    Single<Response> neftTransfer(@Body RequestBody body);

    @POST("cashTransfer")
    Single<Response> accountTransfer(@Body RequestBody body);

    @POST("OTPGenerate")
    Single<Response> resendOtp(@Body RequestBody body);

    @POST("ValidateMPIN")
    Single<Response> validateMpin(@Body RequestBody body);

    @POST("OTPGenerate")
    Single<Response> generateOtp(@Body RequestBody body);

    @POST("getAccountHolder")
    Single<Response> getAccountHolder(@Body RequestBody body);

    @POST("getOperatorList")
    Single<Response> getOperatorList(@Body RequestBody body);

    @POST("getCircleList")
    Single<Response> getCircle(@Body RequestBody body);

    @POST("CustRequstTransStatus")
    Single<Response> getRecentTransactionStatus(@Body RequestBody body);

    @POST("CustSelectTransList")
    Single<Response> getRecentTransactionList(@Body RequestBody body);

    @POST("userRegister")
    Single<Response> userRegister(@Body RequestBody body);

    @POST("custCreateBeneficiary")
    Single<Response> addBeneficiary(@Body RequestBody body);

    @POST("custappUserLogin")
    Single<Response> login(@Body RequestBody body);

    @POST("updateMPIN")
    Single<Response> updateMpin(@Body RequestBody body);

    @GET("APIkey")
    Single<ApiKeyResponse> getApiKey();

    @GET("bank/{ifsc}")
    Single<GetIfscResponse> getIfsc(@Path("ifsc") String ifsc);

    @POST("custSenderRegisteredReceiversList")
    Single<Response> getBeneficiary(@Body RequestBody body);

    @POST("RechargeMobile")
    Single<Response> recharge(@Body RequestBody body);

    @POST("getMasters")
    Single<Response> getMasters(@Body RequestBody body);

    @POST("registerMPIN")
    Single<Response> registerMpin(@Body RequestBody body);

    @POST("balanceEnqury")
    Single<Response> balanceEnqury(@Body RequestBody body);

    @POST("getMiniStatement")
    Single<Response> getMiniStatement(@Body RequestBody body);

}