package org._11253.vision.pipelines;

import org._11253.vision.filtering.ExampleFilter;
import org._11253.vision.filtering.Filter;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;

/**
 * An example pipeline.
 *
 * <p>
 * Pipelines take a Mat input and return another Mat output. Through this
 * pipeline, we can apply effects or edit the image in other ways. Of course,
 * none of this makes any sense - but oh well.
 * </p>
 *
 * @author Colin Robertson
 */
public class ExamplePipeline extends OpenCvPipeline {
    /**
     * An example filter.
     */
    private final Filter exampleFilter = new ExampleFilter();

    /**
     * Process a frame.
     *
     * <p>
     * Given an input, return an output, both of the type Mat. Inputs are
     * (usually raw) input images (of course) and outputs are edited images.
     * In most cases, only one image pipeline occurs.
     * </p>
     *
     * @param input the input image.
     * @return an (edited) output image.
     */
    @Override
    public Mat processFrame(Mat input) {
        exampleFilter.apply(input);
        return input;
    }
}
