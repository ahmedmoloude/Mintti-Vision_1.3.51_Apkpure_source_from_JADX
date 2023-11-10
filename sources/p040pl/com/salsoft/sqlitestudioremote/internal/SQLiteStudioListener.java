package p040pl.com.salsoft.sqlitestudioremote.internal;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import p040pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

/* renamed from: pl.com.salsoft.sqlitestudioremote.internal.SQLiteStudioListener */
public class SQLiteStudioListener implements Runnable, ClientJobContainer {
    private static final int interval = 1000;
    private AuthService authService;
    private List<ClientHandler> clientJobs;
    private Context context;
    private List<String> ipBlackList;
    private List<String> ipWhiteList;
    private BlockingDeque<Runnable> jobsQueue;
    private String password;
    private int port = SQLiteStudioService.DEFAULT_PORT;
    private boolean running = true;
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPool;

    public SQLiteStudioListener(Context context2) {
        this.context = context2;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public synchronized void close() {
        this.running = false;
        ServerSocket serverSocket2 = this.serverSocket;
        if (serverSocket2 != null) {
            try {
                serverSocket2.close();
            } catch (IOException unused) {
            }
            this.serverSocket = null;
        }
        ThreadPoolExecutor threadPoolExecutor = this.threadPool;
        if (!(threadPoolExecutor == null || this.clientJobs == null)) {
            threadPoolExecutor.shutdown();
            for (ClientHandler close : this.clientJobs) {
                close.close();
            }
            try {
                this.threadPool.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException unused2) {
            }
        }
    }

    private synchronized boolean isRunning() {
        return this.running;
    }

    public void run() {
        if (init()) {
            Log.d(Utils.LOG_TAG, "Listening for clients...");
            while (isRunning()) {
                try {
                    ClientHandler clientHandler = new ClientHandler(this.serverSocket.accept(), this.context, this, this.authService);
                    this.clientJobs.add(clientHandler);
                    this.threadPool.execute(clientHandler);
                } catch (IOException unused) {
                }
            }
            Log.d(Utils.LOG_TAG, "Listener thread finished.");
        }
    }

    private boolean init() {
        try {
            ServerSocket serverSocket2 = new ServerSocket(this.port, 5);
            this.serverSocket = serverSocket2;
            serverSocket2.setSoTimeout(1000);
            this.jobsQueue = new LinkedBlockingDeque(1);
            this.clientJobs = new CopyOnWriteArrayList();
            this.threadPool = new ThreadPoolExecutor(20, 20, 10, TimeUnit.SECONDS, this.jobsQueue);
            this.authService = new AuthServiceImpl(this.password, this.ipBlackList, this.ipWhiteList);
            return true;
        } catch (IOException e) {
            String str = Utils.LOG_TAG;
            Log.e(str, "Error while opening listening socket: " + e.getMessage(), e);
            return false;
        }
    }

    public void removeJob(ClientHandler clientHandler) {
        this.clientJobs.remove(clientHandler);
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public void setIpWhiteList(List<String> list) {
        this.ipWhiteList = list;
    }

    public void setIpBlackList(List<String> list) {
        this.ipBlackList = list;
    }
}
