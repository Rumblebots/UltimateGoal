package org._11253.vision.filtering;

import org.opencv.core.Mat;

/**
 * The lowest-level filter.
 *
 * @author Colin Robertson
 */
public interface Filter {
    /**
     * Apply a filter. The mat itself should be edited.
     *
     * @param mat the image to be filtered.
     */
    void apply(Mat mat);
}
