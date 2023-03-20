package com.vervyle.lw8_oop.drawable.leafs;

import com.vervyle.lw8_oop.drawable.GraphicElement;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CCircle extends GraphicElement {

    private final double radius;
    private final Point2D center;
    private final Color color;
    private final Shape shape;

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
        return (Math.pow((point2D.x - center.x), 2) + Math.pow((point2D.y - center.y), 2) <= Math.pow(radius, 2));
    }

    @Override
    public void select() {
        shape.setStyle("-fx-stroke: #FF0000; -fx-stroke-width: 3px");
    }

    @Override
    public void deselect() {
        shape.setStyle("-fx-stroke: #000000; -fx-stroke-width: 3px");
    }

    public CCircle(Point2D point2D, double radius, AnchorPane pane, Color color) {
        super(pane);
        center = point2D;
        this.color = color;
        this.radius = radius;
        this.shape = new Circle(center.x, center.y, this.radius, this.color);
    }
}
