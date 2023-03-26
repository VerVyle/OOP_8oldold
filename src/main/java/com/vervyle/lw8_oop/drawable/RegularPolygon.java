package com.vervyle.lw8_oop.drawable;

import com.vervyle.lw8_oop.drawable.render.Point2D;
import com.vervyle.lw8_oop.drawable.render.Ray2D;
import com.vervyle.lw8_oop.drawable.render.RayTracer;
import com.vervyle.lw8_oop.drawable.render.Segment2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class RegularPolygon extends GraphicElement {

    protected Shape shape;
    protected Point2D center;
    protected double[] vertices;
    protected int numOfVertices;
    protected double radius;
    protected Color color;

    protected RegularPolygon(Point2D center, Pane pane, Color color, double radius, int numOfVertices) {
        super(pane);
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.numOfVertices = numOfVertices;
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

    @Override
    public void show() {
        pane.getChildren().add(shape);
    }

    public void hide() {
        pane.getChildren().remove(shape);
    }

    /**
     * @param point2D
     * @return
     * @// TODO: 26.03.2023 поправить реализацию потому что эта полный хлам
     */
    @Override
    public boolean anyFit(Point2D point2D) {
        Point2D start = new Point2D(vertices[2 * numOfVertices - 2], vertices[2 * numOfVertices - 1]);
        Point2D end;
        Segment2D edge;
        Ray2D ray = new Ray2D(point2D, new Point2D(0, 0));
        int i = 0;
        int counter = 0;
        do {
            end = new Point2D(vertices[2 * i], vertices[2 * i + 1]);
            edge = new Segment2D(start, end);
            if (RayTracer.doesCrossV2(ray, edge))
                counter++;
            start = end;
            i++;
        } while (i < numOfVertices);
        return counter % 2 == 1;
    }

    protected void calcVertices(double[] angles) {
        vertices = new double[numOfVertices * 2];
        for (int i = 0; i < numOfVertices; i++) {
            vertices[2 * i] = center.x + Math.sin(angles[i]) * radius;
            vertices[2 * i + 1] = center.y + Math.cos(angles[i]) * radius;
        }
    }

    protected abstract void updateShape();

    @Override
    public void resize(double newSize) throws OutOfPaneException {
        double buffer = radius;
        radius = newSize;
        if (isOutOfPane()) {
            radius = buffer;
            throw new OutOfPaneException();
        }
        updateShape();
    }

    @Override
    public void changeColor(Color newColor) {
        color = newColor;
        updateShape();
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

    @Override
    public void move(double deltaX, double deltaY) throws OutOfPaneException {
        if (isOutOfPane(deltaX, deltaY)) {
            System.out.println("Cannot move elements because they would be out of pane!");
            return;
        }
        center = new Point2D(center.x + deltaX, center.y + deltaY);
        updateShape();
    }
}
