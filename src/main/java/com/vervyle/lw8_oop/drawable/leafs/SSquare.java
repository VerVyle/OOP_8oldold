package com.vervyle.lw8_oop.drawable.leafs;

import com.vervyle.lw8_oop.drawable.RegularPolygon;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class SSquare extends RegularPolygon {

    private static final double[] INIT_ANGLES;
    private static final int NUM_OF_VERTICES;

    private double width;

    public SSquare(Point2D center, double radius, AnchorPane pane, Color color) {
        super(center, pane, color, radius, NUM_OF_VERTICES);
        width = Math.sqrt(2) * radius;
        shape = new Rectangle(center.x - width / 2, center.y - width / 2, width, width);
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
    public boolean anyFit(Point2D point2D) {
        if (this.center.x <= point2D.x + width / 2 && point2D.x <= this.center.x + width / 2)
            return this.center.y <= point2D.y + width / 2 && point2D.y <= this.center.y + width / 2;
        return false;
    }

    static {
        NUM_OF_VERTICES = 4;
        INIT_ANGLES = new double[4];
        for (int i = 0; i < 4; i++) {
            INIT_ANGLES[i] = ((315.0 + i * 90) % 360) / 180 * Math.PI;
        }
    }
}
