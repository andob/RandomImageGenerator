package com.stedi.randomimagegenerator;

import android.graphics.Paint;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.stedi.randomimagegenerator.generators.FlatColorGenerator;
import com.stedi.randomimagegenerator.generators.TextOverlayGenerator;
import com.stedi.randomimagegenerator.generators.effects.TextOverlayEffect;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class TextOverlayGeneratorBuilderTest {
    private final TextOverlayGenerator.TextPolicy textPolicy = imageParams -> "text";

    @Test(expected = IllegalStateException.class)
    public void buildExceptionWithoutParams() {
        new TextOverlayGenerator.Builder()
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNullGenerator() {
        new TextOverlayGenerator.Builder()
                .setGenerator(null);
    }

    @Test
    public void buildSimple() {
        TextOverlayEffect generator = new TextOverlayGenerator.Builder()
                .setGenerator(new FlatColorGenerator())
                .build();

        assertNotNull(generator);
    }

    @Test
    public void buildSimpleWithTextPolicy() {
        TextOverlayEffect generator = new TextOverlayGenerator.Builder()
                .setGenerator(new FlatColorGenerator())
                .setTextPolicy(textPolicy)
                .build();

        assertNotNull(generator);
    }

    @Test
    public void buildSimpleWithNullPaint() {
        TextOverlayEffect generator = new TextOverlayGenerator.Builder()
                .setGenerator(new FlatColorGenerator())
                .setTextPolicy(textPolicy)
                .setTextPaint(null)
                .setBackgroundPaint(null)
                .build();

        assertNotNull(generator);
    }

    @Test
    public void buildSimpleWithNonNullPaint() {
        TextOverlayEffect generator = new TextOverlayGenerator.Builder()
                .setGenerator(new FlatColorGenerator())
                .setTextPolicy(textPolicy)
                .setTextPaint(new Paint())
                .setBackgroundPaint(new Paint())
                .build();

        assertNotNull(generator);
    }

    @Test
    public void buildSimpleWithFalseSetters() {
        TextOverlayEffect generator = new TextOverlayGenerator.Builder()
                .setGenerator(new FlatColorGenerator())
                .setTextPolicy(textPolicy)
                .setTextPaint(new Paint())
                .setBackgroundPaint(new Paint())
                .setDrawBackground(false)
                .setAutoresizeText(false)
                .build();

        assertNotNull(generator);
    }
}
