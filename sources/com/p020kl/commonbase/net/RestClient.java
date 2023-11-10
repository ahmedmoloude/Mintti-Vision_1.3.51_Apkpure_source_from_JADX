package com.p020kl.commonbase.net;

import android.util.Log;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.p020kl.commonbase.bean.BaseMeasureEntity;
import com.p020kl.commonbase.bean.CustomDate;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.bean.FeedbackEntity;
import com.p020kl.commonbase.bean.MemberEntity;
import com.p020kl.commonbase.bean.UserInfoEntity;
import com.p020kl.commonbase.bean.VersionInfo;
import com.p020kl.commonbase.bean.rothmanindex.AuthenticatedSession;
import com.p020kl.commonbase.bean.rothmanindex.HealthTrendBean;
import com.p020kl.commonbase.bean.rothmanindex.ObservationsBean;
import com.p020kl.commonbase.bean.rothmanindex.QuiltTrendsBean;
import com.p020kl.commonbase.bean.rothmanindex.TrendBean;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.constants.DataType;
import com.p020kl.commonbase.net.entity.EmailVerifyCodeParam;
import com.p020kl.commonbase.net.entity.MeasureRequest;
import com.p020kl.commonbase.net.entity.PhoneVerifyCodeParam;
import com.p020kl.commonbase.net.entity.QueryResult;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UploadResult;
import com.p020kl.commonbase.net.entity.UserDesc;
import com.p020kl.commonbase.net.entity.UserForSignup;
import com.p020kl.commonbase.net.interceptor.ProgressResponseListener;
import com.p020kl.commonbase.net.interceptor.UploadRequestBody;
import com.p020kl.commonbase.utils.JsonUtils;
import com.p020kl.commonbase.utils.RxUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import p028io.reactivex.Observable;
import retrofit2.Response;

/* renamed from: com.kl.commonbase.net.RestClient */
public class RestClient {
    public static Observable<ResponseResult<Object>> getVCodeByPhone(PhoneVerifyCodeParam phoneVerifyCodeParam) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getRestApiService().getVCodeByPhone(phoneVerifyCodeParam));
    }

    public static Observable<ResponseResult<Object>> getVCodeByEmail(EmailVerifyCodeParam emailVerifyCodeParam) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getRestApiService().getVCodeByEmail(emailVerifyCodeParam));
    }

    public static Observable<ResponseResult<UserInfoEntity>> signup(UserForSignup userForSignup) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getRestApiService().signup(userForSignup));
    }

    public static Observable<ResponseResult<UserInfoEntity>> signin(String str, String str2) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getRestApiService().signin(str, str2));
    }

    public static Observable<ResponseResult<Object>> modifyPassword(String str, String str2) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().modifyPassword(str, str2));
    }

    public static Observable<ResponseResult<Object>> resetPassword(String str, String str2, String str3) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getRestApiService().resetPassword(str, str2, str3));
    }

    public static Observable<ResponseResult<UserDesc>> getUserDesc() {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().getUserDesc());
    }

    public static Observable<ResponseResult<UserInfoEntity>> getUserInfo() {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().getUserInfo());
    }

    public static Observable<ResponseResult<Object>> updateUserInfo(UserInfoEntity userInfoEntity) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().updateUserDesc(userInfoEntity));
    }

    public static Observable<ResponseResult<String>> uploadFace(File file, ProgressResponseListener progressResponseListener) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().uploadFace(MultipartBody.Part.createFormData("uploadFile", file.getName(), new UploadRequestBody(RequestBody.create(MediaType.parse("image/*"), file), progressResponseListener))));
    }

    public static Observable<ResponseResult<Object>> uploadFeedback(RequestBody requestBody, List<MultipartBody.Part> list) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().uploadFeedback(requestBody, list));
    }

    public static Observable<ResponseResult<Object>> uploadFeedback(FeedbackEntity feedbackEntity, List<File> list) {
        RequestBody create = RequestBody.create(MediaType.parse("multipart/form-data"), JsonUtils.toJsonString(feedbackEntity));
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            File file = list.get(i);
            Log.e("uploadFeedback", "uploadFeedback: " + file.getName());
            arrayList.add(MultipartBody.Part.createFormData("imgFiles", file.getName(), RequestBody.create(MediaType.parse("image/*"), file)));
        }
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().uploadFeedback(create, arrayList));
    }

    public static Observable<ResponseResult<UploadResult>> uploadEcgFile(ECGEntity eCGEntity, File file) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().uploadEcgFile(new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("jsonData", JsonUtils.toJsonString(eCGEntity)).addFormDataPart("ecgFile", file.getName(), RequestBody.create(MediaType.parse("audio/*"), file)).addFormDataPart(DublinCoreProperties.TYPE, eCGEntity.getType()).build()));
    }

    public static Observable<ResponseResult<UploadResult>> uploadMeasureData(BaseMeasureEntity baseMeasureEntity) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().uploadMeasureData(new MeasureRequest(baseMeasureEntity, baseMeasureEntity.getType())));
    }

    /* renamed from: com.kl.commonbase.net.RestClient$1 */
    static /* synthetic */ class C15701 {
        static final /* synthetic */ int[] $SwitchMap$com$kl$commonbase$constants$DataType;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.kl.commonbase.constants.DataType[] r0 = com.p020kl.commonbase.constants.DataType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$kl$commonbase$constants$DataType = r0
                com.kl.commonbase.constants.DataType r1 = com.p020kl.commonbase.constants.DataType.ECG     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$kl$commonbase$constants$DataType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.kl.commonbase.constants.DataType r1 = com.p020kl.commonbase.constants.DataType.BP     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$kl$commonbase$constants$DataType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.kl.commonbase.constants.DataType r1 = com.p020kl.commonbase.constants.DataType.SPO2     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$kl$commonbase$constants$DataType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.kl.commonbase.constants.DataType r1 = com.p020kl.commonbase.constants.DataType.TEMPERATURE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$kl$commonbase$constants$DataType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.kl.commonbase.constants.DataType r1 = com.p020kl.commonbase.constants.DataType.BG     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.p020kl.commonbase.net.RestClient.C15701.<clinit>():void");
        }
    }

    public static Observable<ResponseResult<QueryResult>> queryByWeek(List<CustomDate> list, int i, int i2, DataType dataType) {
        int i3 = C15701.$SwitchMap$com$kl$commonbase$constants$DataType[dataType.ordinal()];
        if (i3 == 1) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryEcgByWeek(list, i, i2, dataType.name()));
        }
        if (i3 == 2) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBpByWeek(list, i, i2, dataType.name()));
        }
        if (i3 == 3) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().querySpo2ByWeek(list, i, i2, dataType.name()));
        }
        if (i3 == 4) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBtByWeek(list, i, i2, dataType.name()));
        }
        if (i3 != 5) {
            return null;
        }
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBgByWeek(list, i, i2, dataType.name()));
    }

    public static Observable<ResponseResult<QueryResult>> queryByDate(CustomDate customDate, int i, int i2, DataType dataType) {
        int i3 = C15701.$SwitchMap$com$kl$commonbase$constants$DataType[dataType.ordinal()];
        if (i3 == 1) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryEcgByDate(customDate, i, i2, dataType.name()));
        }
        if (i3 == 2) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBpByDate(customDate, i, i2, dataType.name()));
        }
        if (i3 == 3) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().querySpo2ByDate(customDate, i, i2, dataType.name()));
        }
        if (i3 == 4) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBtByDate(customDate, i, i2, dataType.name()));
        }
        if (i3 != 5) {
            return null;
        }
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBgByDate(customDate, i, i2, dataType.name()));
    }

    public static Observable<ResponseResult<QueryResult>> queryAllRecord(int i, int i2, DataType dataType) {
        int i3 = C15701.$SwitchMap$com$kl$commonbase$constants$DataType[dataType.ordinal()];
        if (i3 == 1) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryEcgAll(i, i2, dataType.name()));
        }
        if (i3 == 2) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBpAll(i, i2, dataType.name()));
        }
        if (i3 == 3) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().querySpo2All(i, i2, dataType.name()));
        }
        if (i3 == 4) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBtAll(i, i2, dataType.name()));
        }
        if (i3 != 5) {
            return null;
        }
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBgAll(i, i2, dataType.name()));
    }

    public static Observable<ResponseResult<BaseMeasureEntity>> queryByDocId(String str, DataType dataType) {
        int i = C15701.$SwitchMap$com$kl$commonbase$constants$DataType[dataType.ordinal()];
        if (i == 1) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryEcgById(str, dataType.name()));
        }
        if (i == 2) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBpById(str, dataType.name()));
        }
        if (i == 3) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().querySpo2ById(str, dataType.name()));
        }
        if (i == 4) {
            return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBtById(str, dataType.name()));
        }
        if (i != 5) {
            return null;
        }
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().queryBgById(str, dataType.name()));
    }

    public static Observable<ResponseResult<Object>> deleteByDocId(String str, DataType dataType) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().deleteByDocId(str, dataType.name()));
    }

    public static Observable<ResponseBody> download(String str, ProgressResponseListener progressResponseListener) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getDownloadApiService(progressResponseListener).download(str));
    }

    public static Observable<Response<AuthenticatedSession>> getSessionsToken(String str, String str2) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getSessionsApiService().getSessionsToken(str, str2));
    }

    public static Observable<Response<Object>> observations(String str, ObservationsBean observationsBean) {
        Observable checkNetworkConnection = RxUtils.checkNetworkConnection();
        RestApiService rothmanService = RestServiceCreator.getRothmanService();
        return Observable.concat(checkNetworkConnection, (Observable) rothmanService.observations("Bearer " + str, observationsBean));
    }

    public static Observable<Response<TrendBean>> trendsIndividual(String str, String str2) {
        Observable checkNetworkConnection = RxUtils.checkNetworkConnection();
        RestApiService rothmanService = RestServiceCreator.getRothmanService();
        return Observable.concat(checkNetworkConnection, (Observable) rothmanService.trendsIndividual("Bearer " + str, str2));
    }

    public static Observable<Response<HealthTrendBean>> trendsClinical(String str, String str2) {
        Observable checkNetworkConnection = RxUtils.checkNetworkConnection();
        RestApiService rothmanService = RestServiceCreator.getRothmanService();
        return Observable.concat(checkNetworkConnection, (Observable) rothmanService.trendsClinical("Bearer " + str, str2));
    }

    public static Observable<Response<List<QuiltTrendsBean>>> trends(String str) {
        Observable checkNetworkConnection = RxUtils.checkNetworkConnection();
        RestApiService rothmanService = RestServiceCreator.getRothmanService();
        return Observable.concat(checkNetworkConnection, (Observable) rothmanService.trends("Bearer " + str));
    }

    public static Observable<ResponseResult<List<MemberEntity>>> getMember() {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().getMemberList());
    }

    public static Observable<ResponseResult<Object>> deleteMember(String str) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().deleteMember(str));
    }

    public static Observable<ResponseResult<List<MemberEntity>>> addMember(String str) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().addMember(str));
    }

    public static Observable<ResponseResult<Object>> switchMember(String str) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().switchMember(str));
    }

    public static Observable<ResponseResult<Object>> uploadMemberFace(String str) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().uploadMemberFace(str));
    }

    public static Observable<ResponseResult<Object>> uploadMemberName(String str) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().uploadMemberName(str));
    }

    public static Observable<ResponseResult<VersionInfo>> getSoftwareVersion(String str) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().getVersion(Constants.f840OS, str));
    }

    public static Observable<ResponseResult<VersionInfo>> getFirmwareVersion(String str, String str2) {
        return Observable.concat(RxUtils.checkNetworkConnection(), (Observable) RestServiceCreator.getTokenApiService().getVersion(str2, str));
    }
}
