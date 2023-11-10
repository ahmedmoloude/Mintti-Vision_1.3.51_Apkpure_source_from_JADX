package com.p020kl.commonbase.data.p021db.manager;

import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.bean.UserInfoEntity;

/* renamed from: com.kl.commonbase.data.db.manager.UserInfoTableManager */
public class UserInfoTableManager {
    public static void inserOrReplace(UserInfoEntity userInfoEntity) {
        BaseApplication.getInstance().getUserInfoEntityDao().insertOrReplace(userInfoEntity);
    }

    public static void deleteUserInfo(UserInfoEntity userInfoEntity) {
        BaseApplication.getInstance().getUserInfoEntityDao().delete(userInfoEntity);
    }

    public static void updateUserInfo(UserInfoEntity userInfoEntity) {
        BaseApplication.getInstance().getUserInfoEntityDao().update(userInfoEntity);
    }

    public static UserInfoEntity queryUserInfo(String str) {
        return (UserInfoEntity) BaseApplication.getInstance().getUserInfoEntityDao().load(str);
    }
}
