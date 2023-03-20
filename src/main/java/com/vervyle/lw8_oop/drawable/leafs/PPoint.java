package com.vervyle.lw8_oop.drawable.leafs;

import com.vervyle.lw8_oop.drawable.GraphicElement;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class PPoint extends GraphicElement {

    private static final double RADIUS = 2;

    private final Point2D point2D;
    private final Shape shape;
    private Color color;

    public PPoint(Pane pane, Point2D point2D, Color color) {
        super(pane);
        this.point2D = point2D;
        this.color = color;
        shape = new Circle(point2D.x, point2D.y, RADIUS, color);
    }

    public Point2D getPoint2D() {
        return point2D;
    }

    @Override
    public void show() {
        pane.getChildren().add(shape);
    }

    @Override
    public void hide() {
        pane.getChildren().remove(shape);
    }

    @Override
    public boolean anyFit(Point2D point2D) {
        return (Math.pow((point2D.x - this.point2D.x), 2) + Math.pow((point2D.y - this.point2D.y), 2) <= Math.pow(RADIUS, 2));
    }

    @Override
    public void select() {
        shape.setStyle("-fx-stroke: #FF0000; -fx-stroke-width: 3px");
    }

    @Override
    public void deselect() {
        shape.setStyle("-fx-stroke: #000000; -fx-stroke-width: 3px");
    }

}
