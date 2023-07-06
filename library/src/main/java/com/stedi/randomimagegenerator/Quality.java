package com.stedi.randomimagegenerator;

import android.graphics.Bitmap;

/**
 * A specified quality to use for bitmaps generation and compression.
 */
public class Quality {
    private Bitmap.CompressFormat format;
    private int value;

    /**
     * Static creation of the default PNG quality (value 100).
     *
     * @return The default PNG quality.
     */
    public static Quality png() {
        return new Quality(Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * Static creation of specified JPEG quality.
     *
     * @param quality Hint to the compressor, 0-100. 0 meaning compress for
     *                small size, 100 meaning compress for max quality.
     * @return JPEG quality instance.
     */
    public static Quality jpg(int quality) {
        return new Quality(Bitmap.CompressFormat.JPEG, quality);
    }

    /**
     * Constructs a new quality object with specified format and quality value.
     *
     * @param format Specifies the known formats a bitmap can be compressed into.
     * @param value  Hint to the compressor, 0-100. 0 meaning compress for
     *               small size, 100 meaning compress for max quality.
     *               PNG will ignore the quality setting.
     */
    public Quality(Bitmap.CompressFormat format, int value) {
        setFormat(format);
        setQualityValue(value);
    }

    /**
     * @return Quality compress format.
     */
    public Bitmap.CompressFormat getFormat() {
        return format;
    }

    /**
     * To set quality compress format.
     * <p> If set {@link Bitmap.CompressFormat#PNG}, the quality value will be 100. </p>
     */
    public void setFormat(Bitmap.CompressFormat format) {
        if (format == null) {
            throw new IllegalArgumentException("format cannot not be null");
        }
        this.format = format;
        if (format == Bitmap.CompressFormat.PNG) {
            setQualityValue(100);
        }
    }

    /**
     * @return Quality compress value (0-100).
     */
    public int getQualityValue() {
        return value;
    }

    /**
     * To set quality value (0-100).
     * <p> If current format is {@link Bitmap.CompressFormat#PNG}, the quality value is always 100. </p>
     */
    public void setQualityValue(int value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("quality value must be 0..100");
        }
        if (this.format == Bitmap.CompressFormat.PNG && value != 100) {
            return;
        }
        this.value = value;
    }

    /**
     * @return File extension based on the quality format.
     */
    public String getFileExtension() {
        switch (getFormat()) {
            case JPEG:
                return "jpg";
            case PNG:
                return "png";
            case WEBP:
                return "webp";
        }
        throw new IllegalStateException("not supported");
    }

    @Override
    public String toString() {
        return "Quality{" +
                "format=" + format +
                ", value=" + value +
                '}';
    }
}
