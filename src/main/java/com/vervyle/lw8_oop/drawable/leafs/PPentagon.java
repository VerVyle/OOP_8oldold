package com.vervyle.lw8_oop.drawable.leafs;

import com.vervyle.lw8_oop.drawable.RegularPolygon;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PPentagon extends RegularPolygon {

    private static final double[] INIT_ANGLES;
    private static final int NUM_OF_VERTICES;

    public PPentagon(Point2D center, double radius, Pane pane, Color color) {
        super(center, pane, color, radius, NUM_OF_VERTICES);
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
    public void changeColor(Color newColor) {
        hide();
        this.color = newColor;
        shape = new Polygon(vertices);
        shape.setFill(color);
        show();
    }

    static {
        NUM_OF_VERTICES = 5;
        INIT_ANGLES = new double[NUM_OF_VERTICES];
        for (int i = 0; i < NUM_OF_VERTICES; i++) {
            INIT_ANGLES[i] = 72.0 * i / 180 * Math.PI;
        }
    }
}
