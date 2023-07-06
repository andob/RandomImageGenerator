package com.stedi.randomimagegenerator;


/**
 * Image file name policy. Used to save images with the corresponding names retrieved from it.
 */
public interface FileNamePolicy {
    /**
     * Called before an image will be saved to specified path.
     *
     * @param imageParams Image parameters used to generate an image.
     * @return Image file name.
     */
    String getName(ImageParams imageParams);
}
