package com.p020kl.commonbase.net;

import com.p020kl.commonbase.bean.BPEntity;
import com.p020kl.commonbase.bean.BTEntity;
import com.p020kl.commonbase.bean.BaseMeasureEntity;
import com.p020kl.commonbase.bean.CustomDate;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.bean.FeedbackEntity;
import com.p020kl.commonbase.bean.UserInfoEntity;
import com.p020kl.commonbase.constants.DataType;
import com.p020kl.commonbase.net.constants.NetConstants;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UploadResult;
import com.p020kl.commonbase.net.entity.UserDesc;
import com.p020kl.commonbase.net.entity.UserForSignup;
import com.p020kl.commonbase.net.interceptor.ProgressResponseListener;
import com.p020kl.commonbase.net.utils.VCodeParamUtil;
import com.p020kl.commonbase.utils.JsonUtils;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import p028io.reactivex.Observer;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Consumer;

/* renamed from: com.kl.commonbase.net.Test */
public class Test {
    @org.junit.Test
    public void getPhoneVcode() {
        RestClient.getVCodeByPhone(VCodeParamUtil.getPhoneVCodeParam("18256986525", "86", NetConstants.SIGN_TYPE_SIGNUP)).subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                System.out.println(responseResult.toString());
            }
        });
    }

    @org.junit.Test
    public void getEmailVcode() {
        RestClient.getVCodeByEmail(VCodeParamUtil.getEmailVCodeParam("2356778767@qq.com", NetConstants.SIGN_TYPE_SIGNUP)).subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                System.out.println(responseResult.toString());
            }
        });
    }

    @org.junit.Test
    public void signupByPhone() {
        UserForSignup userForSignup = new UserForSignup();
        userForSignup.setAccount("15656203680");
        userForSignup.setAccountType(NetConstants.SIGN_ACCOUNT_TYPE_PHONE);
        userForSignup.setCountryId("86");
        userForSignup.setVcode("128346");
        userForSignup.setPassword("123456");
        RestClient.signup(userForSignup).subscribe(new Consumer<ResponseResult<UserInfoEntity>>() {
            public void accept(ResponseResult<UserInfoEntity> responseResult) throws Exception {
                System.out.println(responseResult.toString());
            }
        });
    }

    @org.junit.Test
    public void signupByEmail() {
        UserForSignup userForSignup = new UserForSignup();
        userForSignup.setAccount("1397617037@qq.com");
        userForSignup.setAccountType("email");
        userForSignup.setVcode("520601");
        userForSignup.setPassword("123456");
        RestClient.signup(userForSignup).subscribe(new Consumer<ResponseResult<UserInfoEntity>>() {
            public void accept(ResponseResult<UserInfoEntity> responseResult) throws Exception {
                System.out.println(responseResult.toString());
            }
        });
    }

    @org.junit.Test
    public void signin() {
        RestClient.signin("18256986525", "1234567").subscribe(new Observer<ResponseResult<UserInfoEntity>>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(ResponseResult<UserInfoEntity> responseResult) {
                System.out.println(responseResult.toString());
                UserInfoEntity data = responseResult.getData();
                responseResult.getStatus();
            }

            public void onError(Throwable th) {
                System.out.println(th.getMessage());
            }
        });
    }

    @org.junit.Test
    public void getUserDesc() {
        RestClient.getUserDesc().subscribe(new Consumer<ResponseResult<UserDesc>>() {
            public void accept(ResponseResult<UserDesc> responseResult) throws Exception {
                System.out.println(responseResult.toString());
            }
        });
    }

    @org.junit.Test
    public void getUserInfo() {
        RestClient.getUserInfo().subscribe(new Consumer<ResponseResult<UserInfoEntity>>() {
            public void accept(ResponseResult<UserInfoEntity> responseResult) throws Exception {
                System.out.println(responseResult);
            }
        });
    }

    @org.junit.Test
    public void modifyPwd() {
        RestClient.modifyPassword("12345678", "123456").subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                System.out.println(responseResult.toString());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                System.out.println(th.getMessage());
            }
        });
    }

    @org.junit.Test
    public void resetPwd() {
        RestClient.resetPassword("1397617037@qq.com", "145831", "aaaaaa").subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                System.out.println(responseResult.toString());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                System.out.println(th.getMessage());
            }
        });
    }

    @org.junit.Test
    public void uploadFace() {
        RestClient.uploadFace(new File("E:\\pic\\123.jpg"), new ProgressResponseListener() {
            public void onResponseProgress(long j, long j2, boolean z) {
                PrintStream printStream = System.out;
                printStream.println("上传进度 = " + ((j * 100) / j2));
            }
        }).subscribe(new Consumer<ResponseResult<String>>() {
            public void accept(ResponseResult<String> responseResult) throws Exception {
                System.out.println(responseResult.toString());
                System.out.println(responseResult.getData());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                System.out.println(th.getMessage());
            }
        });
    }

    @org.junit.Test
    public void uploadFeedback() {
        File file = new File("E:\\pic\\123.jpg");
        File file2 = new File("E:\\pic\\456.jpg");
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setFeedbackText("问题大了去了");
        String jsonString = JsonUtils.toJsonString(feedbackEntity);
        ArrayList arrayList = new ArrayList();
        MultipartBody.Part createFormData = MultipartBody.Part.createFormData("imgFiles", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        MultipartBody.Part createFormData2 = MultipartBody.Part.createFormData("imgFiles", file2.getName(), RequestBody.create(MediaType.parse("image/*"), file2));
        RequestBody.create(MediaType.parse("multipart/form-data"), jsonString);
        arrayList.add(createFormData);
        arrayList.add(createFormData2);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(file);
        arrayList2.add(file2);
        RestClient.uploadFeedback(feedbackEntity, (List<File>) arrayList2).subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                System.out.println(responseResult.toString());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                System.out.println(th.getMessage());
            }
        });
    }

    @org.junit.Test
    public void uploadEcgFile() {
        ECGEntity eCGEntity = new ECGEntity();
        eCGEntity.setFileMd5("72c7044c30e6fa4d18738a3ca836b060");
        eCGEntity.setRriMax(1234);
        eCGEntity.setRriMin(123);
        eCGEntity.setCreateTime(System.currentTimeMillis());
        eCGEntity.setYear(2019);
        eCGEntity.setMonth(12);
        eCGEntity.setDay(13);
        eCGEntity.setDuration(30);
        eCGEntity.setBr(16);
        eCGEntity.setMood(10);
        eCGEntity.setHrv(18);
        eCGEntity.setAvgHr(80);
        eCGEntity.setMacAddress("11:22:33:44:55:66");
        System.out.println(JsonUtils.toJsonString(eCGEntity));
        RestClient.uploadEcgFile(eCGEntity, new File("E:\\test\\2019-12-11 09-27-19.ecg")).subscribe(new Consumer<ResponseResult<UploadResult>>() {
            public void accept(ResponseResult<UploadResult> responseResult) throws Exception {
                System.out.println(responseResult.toString());
                System.out.println(responseResult.getData().getFileUrl());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                System.out.println(th.getMessage());
            }
        });
    }

    @org.junit.Test
    public void uploadMeasureDate() {
        BTEntity bTEntity = new BTEntity();
        bTEntity.setCreateTime(System.currentTimeMillis());
        bTEntity.setDay(13);
        bTEntity.setMonth(12);
        bTEntity.setYear(2019);
        bTEntity.setTemperature(36.0d);
        bTEntity.setMacAddress("11:22:33:44:55:66");
        BPEntity bPEntity = new BPEntity();
        bPEntity.setCreateTime(System.currentTimeMillis());
        bPEntity.setDay(13);
        bPEntity.setMonth(12);
        bPEntity.setYear(2019);
        bPEntity.setHeartRate(76);
        bPEntity.setSystolicPressure(140);
        bPEntity.setDiastolicPressure(90);
        bPEntity.setMacAddress("11:22:33:44:55:66");
        RestClient.uploadMeasureData(bPEntity).subscribe(new Consumer<ResponseResult<UploadResult>>() {
            public void accept(ResponseResult<UploadResult> responseResult) throws Exception {
                System.out.println(responseResult.toString());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                System.out.println(th.getMessage());
            }
        });
    }

    @org.junit.Test
    public void queryByWeek() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CustomDate(2019, 12, 9));
        arrayList.add(new CustomDate(2019, 12, 10));
        arrayList.add(new CustomDate(2019, 12, 11));
        arrayList.add(new CustomDate(2019, 12, 12));
        arrayList.add(new CustomDate(2019, 12, 13));
        arrayList.add(new CustomDate(2019, 12, 14));
        arrayList.add(new CustomDate(2019, 12, 15));
        RestClient.queryByDocId("249ab709da7a41879bbf635e1954d9b7", DataType.BP).subscribe(new Consumer<ResponseResult<BaseMeasureEntity>>() {
            public void accept(ResponseResult<BaseMeasureEntity> responseResult) throws Exception {
                System.out.println(responseResult.toString());
                System.out.println(JsonUtils.toJsonString((BPEntity) responseResult.getData()));
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                System.out.println(th.getMessage());
            }
        });
    }

    @org.junit.Test
    public void delete() {
        RestClient.deleteByDocId("249ab709da7a41879bbf635e1954d9b7", DataType.BP).subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                System.out.println(responseResult.toString());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                System.out.println(th.getMessage());
            }
        });
    }
}
