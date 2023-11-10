package com.p020kl.commonbase.net;

import com.p020kl.commonbase.bean.BGEntity;
import com.p020kl.commonbase.bean.BPEntity;
import com.p020kl.commonbase.bean.BTEntity;
import com.p020kl.commonbase.bean.CustomDate;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.bean.MemberEntity;
import com.p020kl.commonbase.bean.Spo2Entity;
import com.p020kl.commonbase.bean.UserInfoEntity;
import com.p020kl.commonbase.bean.VersionInfo;
import com.p020kl.commonbase.bean.rothmanindex.AuthenticatedSession;
import com.p020kl.commonbase.bean.rothmanindex.HealthTrendBean;
import com.p020kl.commonbase.bean.rothmanindex.ObservationsBean;
import com.p020kl.commonbase.bean.rothmanindex.QuiltTrendsBean;
import com.p020kl.commonbase.bean.rothmanindex.TrendBean;
import com.p020kl.commonbase.net.entity.EmailVerifyCodeParam;
import com.p020kl.commonbase.net.entity.MeasureRequest;
import com.p020kl.commonbase.net.entity.PhoneVerifyCodeParam;
import com.p020kl.commonbase.net.entity.QueryResult;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UploadResult;
import com.p020kl.commonbase.net.entity.UserDesc;
import com.p020kl.commonbase.net.entity.UserForSignup;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import p028io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/* renamed from: com.kl.commonbase.net.RestApiService */
public interface RestApiService {
    @POST("subUser/create")
    Observable<ResponseResult<MemberEntity>> addMember(@Query("name") String str);

    @DELETE("measure/query/{id}")
    Observable<ResponseResult<Object>> deleteByDocId(@Path("id") String str, @Query("type") String str2);

    @DELETE("subUser/delete")
    Observable<ResponseResult<Object>> deleteMember(@Query("subUserId") String str);

    @GET
    Observable<ResponseBody> download(@Url String str);

    @GET("subUser/get")
    Observable<ResponseResult<List<MemberEntity>>> getMemberList();

    @FormUrlEncoded
    @POST("iam/sessions/{aClientId}")
    Observable<Response<AuthenticatedSession>> getSessionsToken(@Path("aClientId") String str, @Field("access_token") String str2);

    @GET("user/getUserDesc")
    Observable<ResponseResult<UserDesc>> getUserDesc();

    @GET("user/getUserInfo")
    Observable<ResponseResult<UserInfoEntity>> getUserInfo();

    @POST("email/getVerifyCode")
    Observable<ResponseResult<Object>> getVCodeByEmail(@Body EmailVerifyCodeParam emailVerifyCodeParam);

    @POST("sms/getVerifyCode")
    Observable<ResponseResult<Object>> getVCodeByPhone(@Body PhoneVerifyCodeParam phoneVerifyCodeParam);

    @GET("version/getLatestVersion")
    Observable<ResponseResult<VersionInfo>> getVersion(@Query("os") String str, @Query("variant") String str2);

    @PUT("user/modifyPassword")
    Observable<ResponseResult<Object>> modifyPassword(@Query("rawPwd") String str, @Query("newPwd") String str2);

    @POST("observations")
    Observable<Response<Object>> observations(@Header("Authorization") String str, @Body ObservationsBean observationsBean);

    @GET("measure/query/{page}/{rows}")
    Observable<ResponseResult<QueryResult<BGEntity>>> queryBgAll(@Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @POST("measure/query/date/{page}/{rows}")
    Observable<ResponseResult<QueryResult<BGEntity>>> queryBgByDate(@Body CustomDate customDate, @Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @GET("measure/query/{id}")
    Observable<ResponseResult<BGEntity>> queryBgById(@Path("id") String str, @Query("type") String str2);

    @POST("measure/query/week/{page}/{rows}")
    Observable<ResponseResult<QueryResult<BGEntity>>> queryBgByWeek(@Body List<CustomDate> list, @Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @GET("measure/query/{page}/{rows}")
    Observable<ResponseResult<QueryResult<BPEntity>>> queryBpAll(@Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @POST("measure/query/date/{page}/{rows}")
    Observable<ResponseResult<QueryResult<BPEntity>>> queryBpByDate(@Body CustomDate customDate, @Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @GET("measure/query/{id}")
    Observable<ResponseResult<BPEntity>> queryBpById(@Path("id") String str, @Query("type") String str2);

    @POST("measure/query/week/{page}/{rows}")
    Observable<ResponseResult<QueryResult<BPEntity>>> queryBpByWeek(@Body List<CustomDate> list, @Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @GET("measure/query/{page}/{rows}")
    Observable<ResponseResult<QueryResult<BTEntity>>> queryBtAll(@Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @POST("measure/query/date/{page}/{rows}")
    Observable<ResponseResult<QueryResult<BTEntity>>> queryBtByDate(@Body CustomDate customDate, @Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @GET("measure/query/{id}")
    Observable<ResponseResult<BTEntity>> queryBtById(@Path("id") String str, @Query("type") String str2);

    @POST("measure/query/week/{page}/{rows}")
    Observable<ResponseResult<QueryResult<BTEntity>>> queryBtByWeek(@Body List<CustomDate> list, @Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @GET("measure/query/{page}/{rows}")
    Observable<ResponseResult<QueryResult<ECGEntity>>> queryEcgAll(@Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @POST("measure/query/date/{page}/{rows}")
    Observable<ResponseResult<QueryResult<ECGEntity>>> queryEcgByDate(@Body CustomDate customDate, @Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @GET("measure/query/{id}")
    Observable<ResponseResult<ECGEntity>> queryEcgById(@Path("id") String str, @Query("type") String str2);

    @POST("measure/query/week/{page}/{rows}")
    Observable<ResponseResult<QueryResult<ECGEntity>>> queryEcgByWeek(@Body List<CustomDate> list, @Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @GET("measure/query/{page}/{rows}")
    Observable<ResponseResult<QueryResult<Spo2Entity>>> querySpo2All(@Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @POST("measure/query/date/{page}/{rows}")
    Observable<ResponseResult<QueryResult<Spo2Entity>>> querySpo2ByDate(@Body CustomDate customDate, @Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @GET("measure/query/{id}")
    Observable<ResponseResult<Spo2Entity>> querySpo2ById(@Path("id") String str, @Query("type") String str2);

    @POST("measure/query/week/{page}/{rows}")
    Observable<ResponseResult<QueryResult<Spo2Entity>>> querySpo2ByWeek(@Body List<CustomDate> list, @Path("page") int i, @Path("rows") int i2, @Query("type") String str);

    @PUT("user/resetPassword")
    Observable<ResponseResult<Object>> resetPassword(@Query("account") String str, @Query("vcode") String str2, @Query("password") String str3);

    @FormUrlEncoded
    @POST("user/signIn")
    Observable<ResponseResult<UserInfoEntity>> signin(@Field("account") String str, @Field("password") String str2);

    @FormUrlEncoded
    @POST("user/signIn")
    Observable<ResponseResult<UserInfoEntity>> signinTest(@Field("account") String str, @Field("password") String str2, @Field("createTime") long j);

    @FormUrlEncoded
    @POST("user/signOut")
    Observable<ResponseResult<Object>> signout();

    @POST("user/signUp")
    Observable<ResponseResult<UserInfoEntity>> signup(@Body UserForSignup userForSignup);

    @GET("subUser/switch")
    Observable<ResponseResult<Object>> switchMember(@Query("subUserId") String str);

    @GET("trends")
    Observable<Response<List<QuiltTrendsBean>>> trends(@Header("Authorization") String str);

    @GET("trends/clinical/{clinicalId}")
    Observable<Response<HealthTrendBean>> trendsClinical(@Header("Authorization") String str, @Path("clinicalId") String str2);

    @GET("trends/individual/{individualId}")
    Observable<Response<TrendBean>> trendsIndividual(@Header("Authorization") String str, @Path("individualId") String str2);

    @GET("user/updateUserToken")
    Call<ResponseResult<String>> updateToken(@Header("rawToken") String str);

    @PUT("user/updateUserDesc")
    Observable<ResponseResult<Object>> updateUserDesc(@Body UserInfoEntity userInfoEntity);

    @POST("measure/upload/ecg")
    Observable<ResponseResult<UploadResult>> uploadEcgFile(@Body MultipartBody multipartBody);

    @POST("measure/upload/ecg")
    @Multipart
    Observable<ResponseResult<Object>> uploadEcgFile(@Part("jsonData") RequestBody requestBody, @Part MultipartBody.Part part, @Part RequestBody requestBody2);

    @POST("file/upload/image/face")
    @Multipart
    Observable<ResponseResult<String>> uploadFace(@Part MultipartBody.Part part);

    @POST("feedback/upload")
    @Multipart
    Observable<ResponseResult<Object>> uploadFeedback(@Part("feedbackParamStr") RequestBody requestBody, @Part List<MultipartBody.Part> list);

    @POST("measure/upload")
    Observable<ResponseResult<UploadResult>> uploadMeasureData(@Body MeasureRequest measureRequest);

    @POST("measure/upload")
    Observable<ResponseResult<Object>> uploadMeasureData(@Body MultipartBody multipartBody);

    @PUT("subUser/update/face")
    Observable<ResponseResult<Object>> uploadMemberFace(@Query("faceUrl") String str);

    @PUT("subUser/update/name")
    Observable<ResponseResult<Object>> uploadMemberName(@Query("name") String str);
}
