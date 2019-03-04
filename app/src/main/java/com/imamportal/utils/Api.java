package com.imamportal.utils;


import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.AllDataResponse;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.model.AudioModel;
import com.imamportal.model.Catagories;
import com.imamportal.model.JobPortalModel;
import com.imamportal.model.NoticeResponse;
import com.imamportal.model.PhotoModel;
import com.imamportal.model.QuestionAnswerModel;
import com.imamportal.model.SeraContentData;
import com.imamportal.model.SignUpResponse;
import com.imamportal.model.VideoModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


public interface Api {

    //String BASE_URL = "http://192.168.0.119/wasa_inventory/";
    //String BASE_URL = "http://192.168.0.22/wasa_inventory/";

    //Local
    String BASE_URL = "http://192.168.0.119/imamportal/";

    //Live
    //String BASE_URL = "http://nanosoftbd.com/imamportal/";


//
//    @FormUrlEncoded
//    @POST("api/assigncomplain")
//    Call<AssigncomplainResponse> assigncomplain(
//            @Field("auth_data") String auth_data,
//            @Field("application_no") String complain_application_id
//    );


    @GET("api/categorys")
    public Call<List<Catagories>> categorys();


    @GET("api/notices")
    public Call<List<NoticeResponse>> notices();



    @GET("api/best-imam")
    public Call<List<SeraContentData>> seraContent();

    @GET("api/al-quran-hadiths")
    public Call<List<AlquranAlhadits>> alquranAllhadits();

    @GET("api/audios")
    public Call<List<AudioModel>> audios();


    @GET("api/videos")
    public Call<List<VideoModel>> videos();


    @GET("api/blog_post")
    public Call<List<AllBlogpostModel>> blog_post();


    @GET("api/skill")
    public Call<List<AllBlogpostModel>> skill();

    @GET("api/job-portals")
    public Call<List<JobPortalModel>> jobportals();

    @GET("api/querylist")
    public Call<List<QuestionAnswerModel>> querylist();



    @GET("api/333")
    public Call<List<QuestionAnswerModel>> querylist333();



    @GET("api/photo-gallery")
    public Call<List<PhotoModel>> photo_gallery();


    @GET("api/get_all_data")
    public Call<AllDataResponse> get_all_data();


    @FormUrlEncoded
    @POST("api/signup/store")
    public Call<SignUpResponse> signup(
            @Field("data") String jsondata
            );


    @FormUrlEncoded
    @POST("api/jiggasa")
    public Call<SignUpResponse> jiggasa(
            @Field("data") String jsondata
            );


    @Multipart
    @POST("uploadAttachment")
    Call<String> uploadAttachment(@Part MultipartBody.Part filePart);

    @Multipart
    @POST("api/jobportal")
    Call <String> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name,@Part("data") String jsondata );

    @Multipart
    @POST("api/jobportal")
    Call<ResponseBody> jobPost(@Part MultipartBody.Part file, @Query("data") String description);

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
