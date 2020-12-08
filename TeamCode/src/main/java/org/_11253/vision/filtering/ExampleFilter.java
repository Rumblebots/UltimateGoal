package org._11253.vision.filtering;

import org.opencv.core.Mat;

/**
 * An example of a filter.
 *
 * @author Colin Robertson
 */
public class ExampleFilter implements Filter {
    /**
     * This is where the Mat should be edited.
     *
     * <p>
     * Because this is a void, the Mat should be directly edited here.
     * </p>
     *
     * @param mat the image to be filtered.
     */
    @Override
    public void apply(Mat mat) {
        // Edit the Mat here.
    }
}
