package com.p020kl.commonbase.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import androidx.multidex.MultiDex;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.style.IOSStyle;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.p020kl.commonbase.bean.UserInfoEntity;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.BGEntityDao;
import com.p020kl.commonbase.data.p021db.BPEntityDao;
import com.p020kl.commonbase.data.p021db.BTEntityDao;
import com.p020kl.commonbase.data.p021db.DaoMaster;
import com.p020kl.commonbase.data.p021db.DaoSession;
import com.p020kl.commonbase.data.p021db.ECGEntityDao;
import com.p020kl.commonbase.data.p021db.MySQLiteOpenHelper;
import com.p020kl.commonbase.data.p021db.Spo2EntityDao;
import com.p020kl.commonbase.data.p021db.UserInfoEntityDao;
import com.p020kl.commonbase.data.p021db.manager.UserInfoTableManager;
import com.p020kl.commonbase.utils.LoggerUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.soundplay.SoundUtil;
import com.tencent.bugly.crashreport.CrashReport;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.plugins.RxJavaPlugins;
import p035me.yokeyword.fragmentation.Fragmentation;
import p040pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

/* renamed from: com.kl.commonbase.base.BaseApplication */
public class BaseApplication extends Application {
    private static final String DB_NAME = "HealthMonitor";
    public static final boolean IS_DUBUG = true;
    public static boolean isBindService = false;
    public static boolean isTokenExpire = false;
    private static BaseApplication mApplication = null;
    protected static String token = "";
    private DaoSession daoSession;
    private ActivityManager mActivityManager;
    private SoundUtil soundUtil;

    public static synchronized String getToken() {
        String str;
        synchronized (BaseApplication.class) {
            if (TextUtils.isEmpty(token)) {
                token = SpManager.getToken();
            }
            str = token;
        }
        return str;
    }

    public String getUserId() {
        return SpManager.getUserId();
    }

    public String getRothmanIndexUuid() {
        return StringUtils.formatUUID(getUserId());
    }

    public static synchronized void updateToken(String str) {
        synchronized (BaseApplication.class) {
            if (!TextUtils.isEmpty(str)) {
                token = str;
                SpManager.setToken(str);
                UserInfoEntity queryUserInfo = UserInfoTableManager.queryUserInfo(SpManager.getUserId());
                if (queryUserInfo != null) {
                    queryUserInfo.setToken(str);
                    UserInfoTableManager.updateUserInfo(queryUserInfo);
                }
            }
        }
    }

    public static synchronized void clearToken() {
        synchronized (BaseApplication.class) {
            token = "";
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        mApplication = this;
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
        Log.d("cread", "初始化资源");
        initFragmentation();
        initLogger();
        initGreenDao(this);
        initSqliteView();
        setRxJavaErrorHandler();
        initBugly();
        initDialogX();
    }

    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "8fdb3cc20c", true);
    }

    private void initSqliteView() {
        SQLiteStudioService.instance().start(this);
    }

    private void initFragmentation() {
        Fragmentation.builder().stackViewMode(2).debug(false).install();
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().showThreadInfo(true).methodCount(2).methodOffset(1).logStrategy(new LogCatStrategy()).tag("Mintti").build()) {
            public boolean isLoggable(int i, String str) {
                return true;
            }
        });
    }

    /* renamed from: com.kl.commonbase.base.BaseApplication$LogCatStrategy */
    public static class LogCatStrategy implements LogStrategy {
        private int last;

        public void log(int i, String str, String str2) {
            Log.println(i, randomKey() + str, str2);
        }

        private String randomKey() {
            int random = (int) (Math.random() * 10.0d);
            if (random == this.last) {
                random = (random + 1) % 10;
            }
            this.last = random;
            return String.valueOf(random);
        }
    }

    public static BaseApplication getInstance() {
        return mApplication;
    }

    public ActivityManager getActivityManager() {
        if (this.mActivityManager == null) {
            this.mActivityManager = new ActivityManager();
        }
        return this.mActivityManager;
    }

    private void initGreenDao(Context context) {
        this.daoSession = new DaoMaster(new MySQLiteOpenHelper(context, "HealthMonitor", (SQLiteDatabase.CursorFactory) null).getWritableDatabase()).newSession();
    }

    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                LoggerUtil.m112d(th.getMessage());
            }
        });
    }

    public void onTerminate() {
        super.onTerminate();
        Log.d("cread", "释放资源");
    }

    public DaoSession getDaoSession() {
        return this.daoSession;
    }

    public UserInfoEntityDao getUserInfoEntityDao() {
        return this.daoSession.getUserInfoEntityDao();
    }

    public ECGEntityDao getEcgEntityDao() {
        return this.daoSession.getECGEntityDao();
    }

    public BPEntityDao getBpEntityDao() {
        return this.daoSession.getBPEntityDao();
    }

    public BTEntityDao getBtEntityDao() {
        return this.daoSession.getBTEntityDao();
    }

    public Spo2EntityDao getSpo2hEntityDao() {
        return this.daoSession.getSpo2EntityDao();
    }

    public BGEntityDao getBgEntityDao() {
        return this.daoSession.getBGEntityDao();
    }

    private void initDialogX() {
        DialogX.init(this);
        DialogX.DEBUGMODE = true;
        DialogX.globalStyle = new IOSStyle();
        DialogX.globalTheme = DialogX.THEME.AUTO;
        DialogX.onlyOnePopTip = true;
        DialogX.cancelableTipDialog = false;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (configuration.fontScale != 1.0f) {
            getResources();
        }
        super.onConfigurationChanged(configuration);
    }

    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1.0f) {
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }
}
