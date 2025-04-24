package com.logauditor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class TimeMonitorService extends Service {
    private static final String TAG = "TimeMonitorService";
    private long lastTime = System.currentTimeMillis();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(() -> {
            while (true) {
                long currentTime = System.currentTimeMillis();
                long expectedDiff = 5000;
                long actualDiff = currentTime - lastTime;

                if (Math.abs(actualDiff - expectedDiff) > 3000) {
                    String log = "[" + new java.util.Date() + "] ⚠️ Hora alterada! Diferença: " + actualDiff + "ms\n";
                    writeLog(log);
                    Log.d(TAG, log);
                }

                lastTime = currentTime;
                SystemClock.sleep(expectedDiff);
            }
        }).start();

        return START_STICKY;
    }

    private void writeLog(String text) {
        try {
            FileOutputStream fos = openFileOutput("time_log.txt", MODE_APPEND);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.append(text);
            writer.close();
        } catch (Exception e) {
            Log.e(TAG, "Erro ao gravar log: " + e.getMessage());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
