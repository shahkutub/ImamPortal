package com.imamportal.utils;


import com.imamportal.model.NoticeResponse;
import com.imamportal.model.SeraContentData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface Api {

    //String BASE_URL = "http://192.168.0.119/wasa_inventory/";
    //String BASE_URL = "http://192.168.0.22/wasa_inventory/";

    //Local
    String BASE_URL = "http://192.168.0.119/imamportal/";

    //Live
    //String BASE_URL = "http://sechseba.gov.bd/";


//
//    @FormUrlEncoded
//    @POST("api/assigncomplain")
//    Call<AssigncomplainResponse> assigncomplain(
//            @Field("auth_data") String auth_data,
//            @Field("application_no") String complain_application_id
//    );


    @GET("api/notices")
    public Call<List<NoticeResponse>> notices();

    @GET("api/best-imam")
    public Call<List<SeraContentData>> seraContent();

//
//
//    @GET
//    public Call<RequsitionApproveSinglaDataResponse> getSingeDataReqIssue(@Url String url);
//
//    @GET
//    public Call<StockTransferApproveSinglaDataResponse> getSingeDataStockTransferApprove(@Url String url);
//
//
//     @GET
//    public Call<StockTransferIssueSinglaDataResponse> getSingleDataStockTransferIssue(@Url String url);
//
//



}
