package com.vervyle.lw8_oop.drawable;

import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class GraphicElement implements Savable {
    protected Pane pane;
    public boolean isSelected;

    protected GraphicElement(Pane pane) {
        this.pane = pane;
        isSelected = false;
    }

    public abstract void show();

    public abstract void hide();

    public abstract boolean anyFit(Point2D point2D);

    public abstract void select();

    public boolean select(Point2D point2D) {
        if (!anyFit(point2D)) return false;
        if (isSelected) return false;
        select();
        return true;
    }

    public abstract void deselect();

    public abstract void resize(double newSize) throws OutOfPaneException;

    public abstract void changeColor(Color newColor);

    public abstract void move(double deltaX, double deltaY) throws OutOfPaneException;

    public abstract boolean isOutOfPane(double deltaX, double deltaY);

    public abstract boolean isOutOfPane();
}
