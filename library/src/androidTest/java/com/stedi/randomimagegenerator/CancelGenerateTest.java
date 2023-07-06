package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.callbacks.GenerateCallback;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(AndroidJUnit4.class)
public class CancelGenerateTest {
    private static final int requestCount = 1000;

    private final AtomicInteger generatedCount = new AtomicInteger(0);
    private final AtomicInteger failedCount = new AtomicInteger(0);

    @Test
    public void test() {
        final Object lock = new Object();

        final Rig rig = new Rig.Builder()
                .setGenerator(imageParams -> {
                    sleep(100);
                    return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                })
                .setFixedSize(100, 100)
                .setCount(requestCount)
                .setCallback(new GenerateCallback() {
                    @Override
                    public void onGenerated(ImageParams imageParams, Bitmap bitmap) {
                        generatedCount.incrementAndGet();
                    }

                    @Override
                    public void onFailedToGenerate(ImageParams imageParams, Exception e) {
                        failedCount.incrementAndGet();
                    }
                }).build();

        new Thread(() -> {
            sleep(1000);
            rig.generateAsync();
            synchronized (lock) {
                lock.notify();
            }
        }).start();

        new Thread(() -> {
            sleep(2000);
            rig.cancel();
        }).start();

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertTrue(generatedCount.get() > 0 && generatedCount.get() < requestCount);
        assertEquals(0, failedCount.get());
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
