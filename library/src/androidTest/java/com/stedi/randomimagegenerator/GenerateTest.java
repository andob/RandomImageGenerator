package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.stedi.randomimagegenerator.callbacks.GenerateCallback;
import com.stedi.randomimagegenerator.generators.FlatColorGenerator;
import com.stedi.randomimagegenerator.generators.Generator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class GenerateTest {
    private final List<Bitmap> bitmaps = new ArrayList<>();
    private final List<Exception> exceptions = new ArrayList<>();

    @Before
    public void before() {
        Rig.enableDebugLogging(true);
        bitmaps.clear();
        exceptions.clear();
    }

    @Test
    public void generateOneFixedSize() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(100, 100)
                .setCallback(new GenerateCallback() {
                    @Override
                    public void onGenerated(ImageParams imageParams, Bitmap bitmap) {
                        bitmaps.add(bitmap);
                    }

                    @Override
                    public void onFailedToGenerate(ImageParams imageParams, Exception e) {
                        e.printStackTrace();
                        fail();
                    }
                }).build().generateAsync();

        assertEquals(1, bitmaps.size());

        Bitmap result = bitmaps.get(0);

        assertNotNull(result);
        assertEquals(result.getWidth(), 100);
        assertEquals(result.getHeight(), 100);
    }

    @Test
    public void generateOneFailed() throws Exception {
        Generator generator = mock(Generator.class);
        when(generator.generate(any(ImageParams.class))).thenReturn(null);

        new Rig.Builder()
                .setGenerator(generator)
                .setFixedSize(100, 100)
                .setCallback(new GenerateCallback() {
                    @Override
                    public void onGenerated(ImageParams imageParams, Bitmap bitmap) {
                        bitmaps.add(bitmap);
                    }

                    @Override
                    public void onFailedToGenerate(ImageParams imageParams, Exception e) {
                        exceptions.add(e);
                    }
                }).build().generateAsync();

        assertTrue(bitmaps.isEmpty());
        assertEquals(1, exceptions.size());
        assertNotNull(exceptions.get(0));
    }

    @Test
    public void generate10() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedSize(100, 100)
                .setCount(10)
                .setCallback(new GenerateCallback() {
                    @Override
                    public void onGenerated(ImageParams imageParams, Bitmap bitmap) {
                        bitmaps.add(bitmap);
                    }

                    @Override
                    public void onFailedToGenerate(ImageParams imageParams, Exception e) {
                        e.printStackTrace();
                        fail();
                    }
                }).build().generateAsync();

        assertEquals(10, bitmaps.size());

        for (Bitmap bitmap : bitmaps) {
            assertNotNull(bitmap);
            assertEquals(bitmap.getWidth(), 100);
            assertEquals(bitmap.getHeight(), 100);
        }
    }

    @Test
    public void generateHeightRange() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setFixedWidth(100)
                .setHeightRange(10, 100, 10)
                .setCallback(new GenerateCallback() {
                    @Override
                    public void onGenerated(ImageParams imageParams, Bitmap bitmap) {
                        bitmaps.add(bitmap);
                    }

                    @Override
                    public void onFailedToGenerate(ImageParams imageParams, Exception e) {
                        e.printStackTrace();
                        fail();
                    }
                }).build().generateAsync();

        assertEquals(10, bitmaps.size());

        // width - 100
        // height - 10, 20, 30, 40, 50, 60, 70, 80, 90, 100
        for (int height = 10, i = 0; height <= 100; height += 10, i++) {
            Bitmap result = bitmaps.get(i);
            assertNotNull(result);
            assertEquals(result.getWidth(), 100);
            assertEquals(result.getHeight(), height);
        }
    }

    @Test
    public void generateWidthHeightRange() {
        new Rig.Builder()
                .setGenerator(new FlatColorGenerator())
                .setWidthRange(100, 60, 10)
                .setHeightRange(10, 50, 10)
                .setCallback(new GenerateCallback() {
                    @Override
                    public void onGenerated(ImageParams imageParams, Bitmap bitmap) {
                        bitmaps.add(bitmap);
                    }

                    @Override
                    public void onFailedToGenerate(ImageParams imageParams, Exception e) {
                        e.printStackTrace();
                        fail();
                    }
                }).build().generateAsync();

        assertEquals(25, bitmaps.size());

        // width - 100, 90, 80, 70, 60
        // height - 10, 20, 30, 40, 50
        for (int width = 100, i = 0; width >= 60; width -= 10) {
            for (int height = 10; height <= 50; height += 10, i++) {
                Bitmap result = bitmaps.get(i);
                assertNotNull(result);
                assertEquals(result.getWidth(), width);
                assertEquals(result.getHeight(), height);
            }
        }
    }

    @Test
    public void generate2FailedFrom10() throws Exception {
        Generator generator = mock(FlatColorGenerator.class);
        when(generator.generate(any(ImageParams.class))).thenAnswer((Answer<Bitmap>) invocationOnMock -> {
            ImageParams params = (ImageParams) invocationOnMock.getArguments()[0];
            if (params.getHeight() == 10 || params.getHeight() == 50) {
                return null;
            } else {
                return (Bitmap) invocationOnMock.callRealMethod();
            }
        });

        new Rig.Builder()
                .setGenerator(generator)
                .setFixedWidth(10)
                .setHeightRange(10, 100, 10)
                .setCallback(new GenerateCallback() {
                    @Override
                    public void onGenerated(ImageParams imageParams, Bitmap bitmap) {
                        bitmaps.add(bitmap);
                    }

                    @Override
                    public void onFailedToGenerate(ImageParams imageParams, Exception e) {
                        exceptions.add(e);
                    }
                }).build().generateAsync();

        assertEquals(8, bitmaps.size());
        assertEquals(2, exceptions.size());

        // width - 10
        // height - 20, 30, 40, 60, 70, 80, 90, 100
        for (int height = 10, i = 0; height <= 100; height += 10) {
            if (height == 10 || height == 50) {
                continue;
            }
            Bitmap result = bitmaps.get(i);
            assertNotNull(result);
            assertEquals(result.getWidth(), 10);
            assertEquals(result.getHeight(), height);
            i++;
        }
    }
}
