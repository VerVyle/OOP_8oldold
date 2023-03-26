package com.vervyle.lw8_oop.factories;

import com.vervyle.lw8_oop.drawable.ElementType;
import com.vervyle.lw8_oop.drawable.GraphicElement;
import com.vervyle.lw8_oop.drawable.utils.OutOfPaneException;
import com.vervyle.lw8_oop.drawable.render.Point2D;

public interface ElementFactory {

    GraphicElement createElement(Point2D center, ElementType type) throws OutOfPaneException;
}
