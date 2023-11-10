package com.linktop.utils;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileHelper {

    /* renamed from: com.linktop.utils.FileHelper$1 */
    public class C21491 implements Runnable {

        /* renamed from: a */
        public final /* synthetic */ Context f977a;

        /* renamed from: b */
        public final /* synthetic */ String f978b;

        /* renamed from: c */
        public final /* synthetic */ Object f979c;

        public void run() {
            try {
                File externalFilesDir = this.f977a.getExternalFilesDir("BPData");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(externalFilesDir, this.f978b + ".dat")));
                objectOutputStream.writeObject(this.f979c);
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
