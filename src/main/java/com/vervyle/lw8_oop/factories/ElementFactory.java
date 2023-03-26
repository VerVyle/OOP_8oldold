package com.vervyle.lw8_oop.factories;

import com.vervyle.lw8_oop.drawable.GraphicElement;
import com.vervyle.lw8_oop.drawable.OutOfPaneException;
import com.vervyle.lw8_oop.drawable.render.Point2D;

public interface ElementFactory {

    enum TYPES {
        CIRCLE,
        TRIANGLE,
        SQUARE,
        PENTAGON,
        POLYGON
    }

    GraphicElement createElement(Point2D center, TYPES type) throws OutOfPaneException;
}
