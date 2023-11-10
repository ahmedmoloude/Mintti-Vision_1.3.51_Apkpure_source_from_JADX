package com.itextpdf.text.pdf.parser;

import com.itextpdf.awt.geom.Point2D;
import java.util.List;

public interface Shape {
    List<Point2D> getBasePoints();
}
