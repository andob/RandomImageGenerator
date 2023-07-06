package com.stedi.randomimagegenerator.generators;

import android.graphics.Bitmap;

import com.stedi.randomimagegenerator.ImageParams;
import com.stedi.randomimagegenerator.Rig;

/**
 * Interface definition for a generator, that is used to generate {@link Bitmap} images.
 */
public interface Generator {
    /**
     * Called from {@link Rig} when a new image should be generated.
     *
     * @param imageParams Image parameters that are used to generate an image.
     * @return Bitmap object, or null.
     * @throws Exception If any.
     */
    Bitmap generate(ImageParams imageParams) throws Exception;
}
