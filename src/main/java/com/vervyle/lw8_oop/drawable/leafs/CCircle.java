package com.vervyle.lw8_oop.drawable.leafs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vervyle.lw8_oop.drawable.ElementType;
import com.vervyle.lw8_oop.drawable.GraphicElement;
import com.vervyle.lw8_oop.drawable.utils.MyColor;
import com.vervyle.lw8_oop.drawable.utils.OutOfPaneException;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CCircle extends GraphicElement {

    @JsonProperty("radius")
    private double radius;
    @JsonProperty("center")
    private Point2D center;
    @JsonProperty("color")
    private MyColor myColor;
    @JsonIgnore
    private Color color;
    @JsonIgnore
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
        type = ElementType.CIRCLE;
        center = point2D;
        this.color = color;
        initMyColor();
        this.radius = radius;
        this.shape = new Circle(center.x, center.y, this.radius, color);
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

    private void initMyColor() {
        myColor = new MyColor(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
    }

    @Override
    public void changeColor(Color newColor) {
        hide();
        color = newColor;
        initMyColor();
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

    @Override
    public void postInit(Pane pane) {
        color = new Color(
                myColor.red(),
                myColor.green(),
                myColor.blue(),
                myColor.alpha());
        setPane(pane);
        setSelected(false);
        setType(ElementType.CIRCLE);
        shape = new Circle(center.x, center.y, this.radius, color);
    }

    public CCircle() {
    }
}
