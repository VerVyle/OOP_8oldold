package com.vervyle.lw8_oop.drawable.leafs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vervyle.lw8_oop.drawable.ElementType;
import com.vervyle.lw8_oop.drawable.RegularPolygon;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.io.FileReader;

public class TTriangle extends RegularPolygon {

    private static final double[] INIT_ANGLES;
    public static final int NUM_OF_VERTICES;

    public TTriangle(Point2D center, double radius, Pane pane, Color color) {
        super(center, pane, color, radius, NUM_OF_VERTICES);
        type = ElementType.TRIANGLE;
        calcVertices(INIT_ANGLES);
        shape = new Polygon(vertices);
        shape.setFill(color);
    }

    @Override
    protected void updateShape() {
        hide();
        calcVertices(INIT_ANGLES);
        shape = new Polygon(vertices);
        shape.setFill(color);
        show();
    }

    @Override
    public void postInit(Pane pane) {
        super.postInit(pane);
        type = ElementType.TRIANGLE;
    }

    public TTriangle() {
    }

    static {
        NUM_OF_VERTICES = 3;
        INIT_ANGLES = new double[NUM_OF_VERTICES];
        for (int i = 0; i < NUM_OF_VERTICES; i++) {
            INIT_ANGLES[i] = 120.0 * i / 180 * Math.PI;
        }
    }
}
