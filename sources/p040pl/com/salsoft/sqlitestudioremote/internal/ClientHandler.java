package p040pl.com.salsoft.sqlitestudioremote.internal;

import android.content.Context;
import android.util.Log;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: pl.com.salsoft.sqlitestudioremote.internal.ClientHandler */
public class ClientHandler implements Runnable {
    private static final String AUTHORIZE_KEY = "auth";
    private static final String COLUMNS_KEY = "columns";
    private static final String COMMAND_KEY = "cmd";
    private static final String CONFIRM_VALUE = "ok";
    private static final String DATA_KEY = "data";
    private static final String DBLIST_KEY = "list";
    private static final String DBNAME_KEY = "db";
    private static final String ERROR_CODE_KEY = "error_code";
    private static final String ERROR_MESSAGE_KEY = "error_message";
    private static final String FAILURE_VALUE = "error";
    private static final String GENERIC_ERROR_KEY = "generic_error";
    private static final int MAX_SIZE = 10485760;
    private static final String PONG_VALUE = "pong";
    private static final String QUERY_KEY = "query";
    private static final String RESULT_KEY = "result";
    private static final String SIZE_KEY = "size";
    private static final String tokenTpl = "06fn43%d3ig7ws%d53";
    private AuthService authService;
    private boolean authorized = false;
    private SocketChannel channel;
    private Socket clientSocket;
    private Context context;
    private State currentState = State.READING_SIZE;
    private byte[] dataBuffer = new byte[0];
    private DataInputStream dataInputStream;
    private SQLiteStudioDbService dbService;
    private boolean denyAccess = false;
    private InputStream inputStream;
    private ClientJobContainer jobContainer;
    private OutputStream outputStream;
    private boolean running = true;
    private byte[] sizeBuffer = new byte[4];

    /* renamed from: pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$Command */
    private enum Command {
        LIST,
        QUERY,
        DELETE_DB
    }

    /* renamed from: pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$Error */
    private enum Error {
        INVALID_FORMAT,
        NO_COMMAND_SPECIFIED,
        UNKNOWN_COMMAND,
        NO_DATABASE_SPECIFIED,
        ERROR_READING_FROM_CLIENT
    }

    /* renamed from: pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$State */
    private enum State {
        READING_SIZE,
        READING_DATA
    }

    public ClientHandler(Socket socket, Context context2, ClientJobContainer clientJobContainer, AuthService authService2) {
        this.clientSocket = socket;
        this.jobContainer = clientJobContainer;
        this.context = context2;
        this.authService = authService2;
        this.dbService = new SQLiteStudioDbService(context2);
        this.authorized = !authService2.isAuthRequired();
    }

    public synchronized void close() {
        this.running = false;
        try {
            this.clientSocket.close();
        } catch (IOException unused) {
        }
    }

    private synchronized boolean isRunning() {
        return this.running;
    }

    public void run() {
        String hostAddress = this.clientSocket.getInetAddress().getHostAddress();
        String str = Utils.LOG_TAG;
        Log.d(str, "New client from " + hostAddress);
        if (!this.authService.isIpAllowed(hostAddress)) {
            String str2 = Utils.LOG_TAG;
            Log.e(str2, "Client's IP address not allowed: " + hostAddress + ", disconnecting.");
            cleanUp();
        } else if (!init()) {
            Log.e(Utils.LOG_TAG, "Could not initialize handler for the client.");
            cleanUp();
        } else {
            while (isRunning() && !this.denyAccess) {
                readClientChannel();
            }
            cleanUp();
            String str3 = Utils.LOG_TAG;
            Log.d(str3, "Disconnected client " + hostAddress);
        }
    }

    private void readClientChannel() {
        if (!this.clientSocket.isConnected()) {
            close();
            return;
        }
        try {
            int i = C26771.f2149xc1829fa[this.currentState.ordinal()];
            if (i == 1) {
                this.dataInputStream.readFully(this.sizeBuffer);
            } else if (i == 2) {
                this.dataInputStream.readFully(this.dataBuffer);
            }
            int i2 = C26771.f2149xc1829fa[this.currentState.ordinal()];
            if (i2 == 1) {
                int i3 = ByteBuffer.wrap(this.sizeBuffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
                if (i3 > MAX_SIZE) {
                    String str = Utils.LOG_TAG;
                    Log.e(str, "Error while reading input from client: maximum size exceeded: " + i3);
                    sendError(Error.ERROR_READING_FROM_CLIENT);
                    return;
                }
                this.currentState = State.READING_DATA;
                this.dataBuffer = new byte[i3];
            } else if (i2 == 2) {
                try {
                    handleRequest(new String(this.dataBuffer, "UTF-8"));
                    this.currentState = State.READING_SIZE;
                } catch (UnsupportedEncodingException e) {
                    String str2 = Utils.LOG_TAG;
                    Log.e(str2, "Error while reading data from client: " + e.getMessage(), e);
                    sendError(Error.ERROR_READING_FROM_CLIENT);
                }
            }
        } catch (EOFException unused) {
            close();
        } catch (IOException e2) {
            String str3 = Utils.LOG_TAG;
            Log.e(str3, "Error while reading input from client: " + e2.getMessage(), e2);
            sendError(Error.ERROR_READING_FROM_CLIENT);
        }
    }

    private boolean init() {
        try {
            this.inputStream = this.clientSocket.getInputStream();
            this.outputStream = this.clientSocket.getOutputStream();
            this.dataInputStream = new DataInputStream(this.inputStream);
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    private void handleRequest(String str) {
        try {
            HashMap hashMap = (HashMap) JsonConverter.fromJsonValue(new JSONObject(str));
            if (!this.authorized) {
                authorize(hashMap);
            } else if (!hashMap.containsKey(COMMAND_KEY)) {
                sendError(Error.NO_COMMAND_SPECIFIED);
            } else {
                try {
                    int i = C26771.f2148x106f8e74[Command.valueOf("" + hashMap.get(COMMAND_KEY)).ordinal()];
                    if (i == 1) {
                        send(DBLIST_KEY, this.dbService.getDbList());
                    } else if (i == 2) {
                        Object obj = hashMap.get(DBNAME_KEY);
                        execAndRespond(obj, "" + hashMap.get(QUERY_KEY));
                    } else if (i == 3) {
                        deleteDbAndRespond(hashMap.get(DBNAME_KEY));
                    }
                } catch (IllegalArgumentException unused) {
                    sendError(Error.UNKNOWN_COMMAND);
                }
            }
        } catch (JSONException unused2) {
            sendError(Error.INVALID_FORMAT);
        }
    }

    /* renamed from: pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$1 */
    static /* synthetic */ class C26771 {

        /* renamed from: $SwitchMap$pl$com$salsoft$sqlitestudioremote$internal$ClientHandler$Command */
        static final /* synthetic */ int[] f2148x106f8e74;

        /* renamed from: $SwitchMap$pl$com$salsoft$sqlitestudioremote$internal$ClientHandler$State */
        static final /* synthetic */ int[] f2149xc1829fa;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0039 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x001d */
        static {
            /*
                pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$Command[] r0 = p040pl.com.salsoft.sqlitestudioremote.internal.ClientHandler.Command.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2148x106f8e74 = r0
                r1 = 1
                pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$Command r2 = p040pl.com.salsoft.sqlitestudioremote.internal.ClientHandler.Command.LIST     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                r0 = 2
                int[] r2 = f2148x106f8e74     // Catch:{ NoSuchFieldError -> 0x001d }
                pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$Command r3 = p040pl.com.salsoft.sqlitestudioremote.internal.ClientHandler.Command.QUERY     // Catch:{ NoSuchFieldError -> 0x001d }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r2 = f2148x106f8e74     // Catch:{ NoSuchFieldError -> 0x0028 }
                pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$Command r3 = p040pl.com.salsoft.sqlitestudioremote.internal.ClientHandler.Command.DELETE_DB     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r4 = 3
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$State[] r2 = p040pl.com.salsoft.sqlitestudioremote.internal.ClientHandler.State.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f2149xc1829fa = r2
                pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$State r3 = p040pl.com.salsoft.sqlitestudioremote.internal.ClientHandler.State.READING_SIZE     // Catch:{ NoSuchFieldError -> 0x0039 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0039 }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x0039 }
            L_0x0039:
                int[] r1 = f2149xc1829fa     // Catch:{ NoSuchFieldError -> 0x0043 }
                pl.com.salsoft.sqlitestudioremote.internal.ClientHandler$State r2 = p040pl.com.salsoft.sqlitestudioremote.internal.ClientHandler.State.READING_DATA     // Catch:{ NoSuchFieldError -> 0x0043 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0043 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0043 }
            L_0x0043:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p040pl.com.salsoft.sqlitestudioremote.internal.ClientHandler.C26771.<clinit>():void");
        }
    }

    private void authorize(HashMap<String, Object> hashMap) {
        if (!hashMap.containsKey(AUTHORIZE_KEY)) {
            Log.w(Utils.LOG_TAG, "Client authorization failed - no 'auth' key in first request.");
            this.denyAccess = true;
            return;
        }
        String str = "" + hashMap.get(AUTHORIZE_KEY);
        if (!this.authService.authorize(str)) {
            Log.w(Utils.LOG_TAG, "Client authorization failed - invalid password: " + str);
            this.denyAccess = true;
            return;
        }
        this.authorized = true;
        Log.w(Utils.LOG_TAG, "Client authorization successful.");
        sendResult(CONFIRM_VALUE);
    }

    private void deleteDbAndRespond(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            sendError(Error.NO_DATABASE_SPECIFIED);
        } else {
            sendResult(this.dbService.deleteDb(obj.toString()) ? CONFIRM_VALUE : FAILURE_VALUE);
        }
    }

    private void execAndRespond(Object obj, String str) {
        if (obj == null || obj.toString().isEmpty()) {
            sendError(Error.NO_DATABASE_SPECIFIED);
            return;
        }
        String obj2 = obj.toString();
        HashMap hashMap = new HashMap();
        QueryResults exec = this.dbService.exec(obj2, str);
        if (exec.isError()) {
            hashMap.put(ERROR_CODE_KEY, exec.getErrorCode());
            hashMap.put(ERROR_MESSAGE_KEY, exec.getErrorMessage());
        } else {
            hashMap.put(COLUMNS_KEY, exec.getColumnNames());
            hashMap.put(DATA_KEY, exec.getData());
        }
        send((HashMap<String, Object>) hashMap);
    }

    private void sendError(Error error) {
        send(GENERIC_ERROR_KEY, Integer.valueOf(error.ordinal()));
    }

    private void sendResult(String str) {
        send(RESULT_KEY, str);
    }

    private void send(String str, Object obj) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, obj);
        send((HashMap<String, Object>) hashMap);
    }

    private void send(HashMap<String, Object> hashMap) {
        send(JsonConverter.toJsonValue(hashMap).toString());
    }

    private void send(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            ByteBuffer order = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
            order.putInt(bytes.length);
            this.outputStream.write(order.array());
            this.outputStream.write(bytes);
        } catch (UnsupportedEncodingException e) {
            String str2 = Utils.LOG_TAG;
            Log.e(str2, "Could not convert response to UTF-8: " + e.getMessage(), e);
        } catch (IOException e2) {
            String str3 = Utils.LOG_TAG;
            Log.e(str3, "Could not send response to client: " + e2.getMessage(), e2);
        }
    }

    private void cleanUp() {
        SQLiteStudioDbService sQLiteStudioDbService = this.dbService;
        if (sQLiteStudioDbService != null) {
            sQLiteStudioDbService.releaseAll();
        }
        InputStream inputStream2 = this.inputStream;
        if (inputStream2 != null) {
            try {
                inputStream2.close();
            } catch (IOException unused) {
            }
        }
        OutputStream outputStream2 = this.outputStream;
        if (outputStream2 != null) {
            try {
                outputStream2.close();
            } catch (IOException unused2) {
            }
        }
        DataInputStream dataInputStream2 = this.dataInputStream;
        if (dataInputStream2 != null) {
            try {
                dataInputStream2.close();
            } catch (IOException unused3) {
            }
        }
        Socket socket = this.clientSocket;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException unused4) {
            }
        }
        this.jobContainer.removeJob(this);
    }
}
