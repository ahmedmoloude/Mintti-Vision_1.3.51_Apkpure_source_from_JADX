package com.linktop.whealthService.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class ReadThread extends Thread {

    /* renamed from: a */
    private final OxTask f1167a;

    /* renamed from: b */
    private final String f1168b;

    public ReadThread(OxTask oxTask, String str) {
        this.f1167a = oxTask;
        this.f1168b = str;
    }

    /* renamed from: a */
    public abstract void mo27553a();

    public void interrupt() {
        super.interrupt();
    }

    public void run() {
        super.run();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.f1168b)), StandardCharsets.UTF_8));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                String[] split = readLine.split(",");
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                StringBuilder sb = new StringBuilder();
                sb.append("red:");
                sb.append(parseInt);
                sb.append(", ir:");
                sb.append(parseInt2);
                this.f1167a.mo27546a(parseInt, parseInt2);
                Thread.sleep(7, 812500);
            }
            bufferedReader.close();
        } catch (Exception unused) {
        }
        mo27553a();
    }

    public synchronized void start() {
        super.start();
    }
}
