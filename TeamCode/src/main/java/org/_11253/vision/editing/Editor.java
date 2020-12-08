package org._11253.vision.editing;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public interface Editor {
    void apply(Mat mat, Point p, int w, int l);
    void apply(Mat mat, Point p, int r);
}
