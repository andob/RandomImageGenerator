package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.stedi.randomimagegenerator.generators.ColoredLinesGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ColoredLinesGeneratorTest {
    private final int[] sizes = new int[]{1, 2, 63, 512};

    @Test
    public void png() {
        test(0, Quality.png());
        test(1, Quality.png());
        test(10, Quality.png());
        test(2000, Quality.png());
    }

    @Test
    public void jpg() {
        test(0, Quality.jpg(80));
        test(1, Quality.jpg(80));
        test(10, Quality.jpg(80));
        test(2000, Quality.jpg(80));
    }

    @Test
    public void webp() {
        test(0, new Quality(Bitmap.CompressFormat.WEBP, 100));
        test(1, new Quality(Bitmap.CompressFormat.WEBP, 100));
        test(10, new Quality(Bitmap.CompressFormat.WEBP, 100));
        test(2000, new Quality(Bitmap.CompressFormat.WEBP, 100));
    }

    private void test(int linesCount, Quality quality) {
        ColoredLinesGenerator generator = new ColoredLinesGenerator(linesCount);
        int count = 0;
        for (int width : sizes) {
            for (int height : sizes) {
                ImageParams params = new ImageParams(++count, width, height, null, quality, RigPalette.allColors(), null);
                try {
                    Bitmap bitmap = generator.generate(params);
                    assertNotNull(bitmap);
                    assertEquals(bitmap.getWidth(), width);
                    assertEquals(bitmap.getHeight(), height);
                    bitmap.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                    fail();
                }
            }
        }
    }
}
