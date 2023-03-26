package com.vervyle.lw8_oop.drawable.leafs;

import com.vervyle.lw8_oop.drawable.GraphicElement;
import com.vervyle.lw8_oop.drawable.OutOfPaneException;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CCircle extends GraphicElement {

    private double radius;
    private Point2D center;
    private Color color;
    private Shape shape;

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
        isSelected = true;
    }

    @Override
    public void deselect() {
        shape.setStyle("-fx-stroke: #000000; -fx-stroke-width: 3px");
        isSelected = false;
    }

    public CCircle(Point2D point2D, double radius, AnchorPane pane, Color color) {
        super(pane);
        center = point2D;
        this.color = color;
        this.radius = radius;
        this.shape = new Circle(center.x, center.y, this.radius, this.color);
    }

    @Override
    public void resize(double newSize) throws OutOfPaneException {
        double buffer = radius;
        radius = newSize;
        if (isOutOfPane()) {
            radius = buffer;
            throw new OutOfPaneException();
        }
        hide();
        shape = new Circle(center.x, center.y, this.radius, this.color);
        show();
    }

    @Override
    public void changeColor(Color newColor) {
        hide();
        color = newColor;
        shape = new Circle(center.x, center.y, this.radius, this.color);
        show();
    }

    @Override
    public void move(double deltaX, double deltaY) throws OutOfPaneException {
        if (isOutOfPane(deltaX, deltaY)) {
            System.out.println("Cannot move elements because they would be out of pane!");
            return;
        }
        center = new Point2D(center.x + deltaX, center.y + deltaY);
        hide();
        shape = new Circle(center.x, center.y, radius, color);
        show();
    }

    @Override
    public boolean isOutOfPane(double deltaX, double deltaY) {
        if (center.x + deltaX - radius < 0 || center.x + deltaX + radius > pane.getWidth())
            return true;
        return center.y + deltaY - radius < 0 || center.y + deltaY + radius > pane.getHeight();
    }

    @Override
    public boolean isOutOfPane() {
        if (center.x - radius < 0 || center.x + radius > pane.getWidth())
            return true;
        return center.y - radius < 0 || center.y + radius > pane.getHeight();
    }
}
