package p040pl.com.salsoft.sqlitestudioremote;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import p040pl.com.salsoft.sqlitestudioremote.internal.SQLiteStudioListener;
import p040pl.com.salsoft.sqlitestudioremote.internal.Utils;

/* renamed from: pl.com.salsoft.sqlitestudioremote.SQLiteStudioService */
public class SQLiteStudioService extends Service {
    public static final int DEFAULT_PORT = 12121;
    private static SQLiteStudioService staticInstance;
    private List<String> ipBlackList = new ArrayList();
    private List<String> ipWhiteList = new ArrayList();
    private SQLiteStudioListener listener;
    private Thread listenerThread;
    private String password;
    private int port = DEFAULT_PORT;
    private boolean running = false;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void start(Context context) {
        if (!this.running) {
            SQLiteStudioListener sQLiteStudioListener = new SQLiteStudioListener(context);
            this.listener = sQLiteStudioListener;
            sQLiteStudioListener.setPort(this.port);
            this.listener.setPassword(this.password);
            this.listener.setIpBlackList(this.ipBlackList);
            this.listener.setIpWhiteList(this.ipWhiteList);
            Thread thread = new Thread(this.listener);
            this.listenerThread = thread;
            thread.start();
            this.running = true;
            String str = Utils.LOG_TAG;
            Log.d(str, "Started instance on port " + this.port);
        }
    }

    public void stop() {
        if (this.running) {
            Log.d(Utils.LOG_TAG, "Shutting down SQLiteStudioService instance.");
            this.listener.close();
            try {
                this.listenerThread.join();
            } catch (InterruptedException unused) {
            }
            this.running = false;
        }
    }

    public static SQLiteStudioService instance() {
        if (staticInstance == null) {
            staticInstance = new SQLiteStudioService();
        }
        return staticInstance;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public void addIpToBlackList(String str) {
        this.ipBlackList.add(str);
    }

    public void addIpToWhiteList(String str) {
        this.ipWhiteList.add(str);
    }

    public void setIpBlackList(String... strArr) {
        this.ipBlackList.clear();
        for (String add : strArr) {
            this.ipBlackList.add(add);
        }
    }

    public void setIpWhiteList(String... strArr) {
        this.ipBlackList.clear();
        for (String add : strArr) {
            this.ipWhiteList.add(add);
        }
    }

    public void setPort(int i) {
        this.port = i;
    }

    public boolean isRunning() {
        return this.running;
    }
}
