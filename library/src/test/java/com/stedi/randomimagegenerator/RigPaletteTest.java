package com.stedi.randomimagegenerator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RigPaletteTest {
    @Test
    public void testAllColors() {
        RigPalette palette = RigPalette.allColors();
        assertEquals(0, palette.getHueFrom(), 0.0);
        assertEquals(360, palette.getHueTo(), 0.0);
        assertFalse(palette.isBlackAndWhite());
        assertTrue(palette.isUseDarkColors());
        assertTrue(palette.isUseLightColors());
    }

    @Test
    public void testHueRange() {
        RigPalette palette = RigPalette.hueRange(10, 340);
        assertEquals(10, palette.getHueFrom(), 0.0);
        assertEquals(340, palette.getHueTo(), 0.0);
        assertFalse(palette.isBlackAndWhite());
        assertFalse(palette.isUseDarkColors());
        assertFalse(palette.isUseLightColors());
    }

    @Test
    public void testWrongHue() {
        try {
            RigPalette palette = RigPalette.allColors();
            palette.setHueFrom(-10);
            fail();
        } catch (Exception ignored) {
        }

        try {
            RigPalette palette = RigPalette.allColors();
            palette.setHueFrom(400);
            fail();
        } catch (Exception ignored) {
        }

        try {
            RigPalette palette = RigPalette.allColors();
            palette.setHueTo(-10);
            fail();
        } catch (Exception ignored) {
        }

        try {
            RigPalette palette = RigPalette.allColors();
            palette.setHueTo(400);
            fail();
        } catch (Exception ignored) {
        }
    }
}
