package com.vervyle.lw8_oop.drawable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import com.vervyle.lw8_oop.drawable.render.Ray2D;
import com.vervyle.lw8_oop.drawable.render.RayTracer;
import com.vervyle.lw8_oop.drawable.render.Segment2D;
import com.vervyle.lw8_oop.drawable.utils.MyColor;
import com.vervyle.lw8_oop.drawable.utils.OutOfPaneException;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class RegularPolygon extends GraphicElement {

    @JsonIgnore
    protected Shape shape;
    @JsonProperty("center")
    protected Point2D center;
    @JsonProperty("vertices")
    protected double[] vertices;
    @JsonProperty("numberOfVertices")
    protected int numOfVertices;
    @JsonProperty("radius")
    protected double radius;
    @JsonProperty("color")
    protected MyColor myColor;
    @JsonIgnore
    protected Color color;

    private void initMyColor() {
        myColor = new MyColor(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
    }

    protected RegularPolygon(Point2D center, Pane pane, Color color, double radius, int numOfVertices) {
        super(pane);
        this.center = center;
        this.radius = radius;
        this.color = color;
        initMyColor();
        this.numOfVertices = numOfVertices;
    }

    protected RegularPolygon() {

    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }

    public double[] getVertices() {
        return vertices;
    }

    public void setVertices(double[] vertices) {
        this.vertices = vertices;
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    public void setNumOfVertices(int numOfVertices) {
        this.numOfVertices = numOfVertices;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public MyColor getMyColor() {
        return myColor;
    }

    public void setMyColor(MyColor myColor) {
        this.myColor = myColor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
        initMyColor();
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
    public void postInit(Pane pane) {
        color = new Color(
                myColor.red(),
                myColor.green(),
                myColor.blue(),
                myColor.alpha());
        setPane(pane);
        setSelected(false);
        shape = new Polygon(vertices);
        shape.setFill(color);
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
