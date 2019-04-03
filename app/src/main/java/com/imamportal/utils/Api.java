package com.imamportal.utils;


import com.imamportal.model.AllBlogpostModel;
import com.imamportal.model.AllDataResponse;
import com.imamportal.model.AlquranAlhadits;
import com.imamportal.model.AmarpataContentResponse;
import com.imamportal.model.AudioModel;
import com.imamportal.model.Catagories;
import com.imamportal.model.JobPortalModel;
import com.imamportal.model.NoticeResponse;
import com.imamportal.model.PhotoModel;
import com.imamportal.model.QuestionAnswerModel;
import com.imamportal.model.CommonPostResponse;
import com.imamportal.model.QuizeQuistionResponse;
import com.imamportal.model.SeraContentData;
import com.imamportal.model.SignUpResponse;
import com.imamportal.model.UploadResponse;
import com.imamportal.model.VideoModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface Api {

    //String BASE_URL = "http://192.168.0.119/wasa_inventory/";
    //String BASE_URL = "http://192.168.0.22/wasa_inventory/";

    //Local
    //String BASE_URL = "http://192.168.0.119/imamportal/";

    //Live
    String BASE_URL = "http://nanosoftbd.com/imamportal/";



    @FormUrlEncoded
    @POST("api/likepost")
    Call<String> likepost(
            @Field("id") String complain_application_id
    );

    @FormUrlEncoded
    @POST("api/singlepost/comment")
    Call<String> commentepost(
            @Field("user_id") String user_id,
            @Field("blog_post_id") String blog_post_id,
            @Field("comment") String comment
    );


    @GET("api/categorys")
    public Call<List<Catagories>> categorys();


    @GET("api/notices")
    public Call<List<NoticeResponse>> notices();



    @GET("api/best-imam")
    public Call<List<SeraContentData>> seraContent();

    @GET("api/al-quran-hadiths")
    public Call<List<AlquranAlhadits>> alquranAllhadits();

    //api/shantirbani
    @GET()
        public Call<CommonPostResponse> shantirbani(@Url String url);

    @GET("api/audios")
    public Call<List<AudioModel>> audios();


    @GET("api/videos")
    public Call<List<VideoModel>> videos();


    @GET("api/blog_post")
    public Call<List<AllBlogpostModel>> blog_post();

    @GET("api/mypage/content")
        public Call<AmarpataContentResponse> mypage_content();

    @GET("api/mypage/audio")
            public Call<AmarpataContentResponse> mypage_Audio();


    @GET("api/mypage/video")
            public Call<AmarpataContentResponse> mypage_video();


    @GET("api/skill")
    public Call<List<AllBlogpostModel>> skill();

    @GET("api/job-portals")
    public Call<CommonPostResponse> jobportals();

    @GET("api/querylist")
    public Call<List<QuestionAnswerModel>> querylist();



    @GET("api/333")
    public Call<List<QuestionAnswerModel>> querylist333();



    @GET("api/photo-gallery")
    public Call<CommonPostResponse> photo_gallery();


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



    @FormUrlEncoded
    @POST("api/login")
    public Call<SignUpResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("api/quiz_start")
    public Call<QuizeQuistionResponse> quiz_start(
            @Header("Authorization") String authHeader,
            @Field("start_quiz") String start_quiz
    );


    @FormUrlEncoded
    @POST("api/quiz_submit")
    public Call<QuizeQuistionResponse> quiz_submit(
            @Header("Authorization") String authHeader,
            @Field("question") String question,
            @Field("question_answer") String question_answer
    );

    @FormUrlEncoded
    @POST("api/post_store")
    public Call<UploadResponse> post_store (
           // @Header("Authorization") String authHeader,
            @Field("data") String jsondata
    );

    @Multipart
    @POST("api/audio_store")
    public Call<UploadResponse> audio_store (
           // @Header("Authorization") String authHeader,
           @Part MultipartBody.Part file,
           @Part("data") RequestBody jsondata
    );

    @Multipart
    @POST("api/video_store")
    public Call<UploadResponse> video_store  (
            // @Header("Authorization") String authHeader,
            @Part MultipartBody.Part file,
            @Part("data") RequestBody jsondata
    );

    @Multipart
    @POST("api/user_photogallery/add")
    public Call<UploadResponse>photogallery (
            // @Header("Authorization") String authHeader,
            @Part MultipartBody.Part file,
           // @Part("image") RequestBody image,
            @Part("title") RequestBody jsondata,
            @Part("user_id") RequestBody user_id
    );


    @Multipart
    @POST("api/jobportal")
    Call<ResponseBody> jobPost(@Part MultipartBody.Part file, @Part("data") RequestBody description);


    @Multipart
    @POST("api/add-technical-training")
    Call<ResponseBody> addtechnicaltraining(@Part MultipartBody.Part image, @Query("data") String description);

    @Multipart
    @POST("api/skill/training/store")
    Call<ResponseBody> imamTraining(@Part MultipartBody.Part image,
                                    @Part MultipartBody.Part educational_certificate,
                                    @Part MultipartBody.Part testimonial,
                                    @Part MultipartBody.Part nid,
                                    @Part MultipartBody.Part imam_prove_certificate,
                                    @Part MultipartBody.Part nomination_certificate,
                                    @Part MultipartBody.Part leave_certificate,
                                    @Part MultipartBody.Part authority_approve_certificate,
                                    @Query("exam_name") String exam_name,
                                    @Query("data") String data);

    //Islam
    @GET
    public Call<CommonPostResponse> familylaw(@Url String url);

     @GET
    public Call<CommonPostResponse> fojdariLaw(@Url String url);


     @GET
    public Call<CommonPostResponse> islamiceconomicbooks(@Url String url);



//    @GET
//    public Call<StockTransferApproveSinglaDataResponse> getSingeDataStockTransferApprove(@Url String url);
//
//
//     @GET
//    public Call<StockTransferIssueSinglaDataResponse> getSingleDataStockTransferIssue(@Url String url);
//
//



}
