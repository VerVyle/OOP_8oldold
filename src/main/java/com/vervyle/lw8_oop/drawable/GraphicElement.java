package com.vervyle.lw8_oop.drawable;

import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.Pane;

public abstract class GraphicElement implements Savable {
    protected Pane pane;

    protected GraphicElement(Pane pane) {
        this.pane = pane;
    }

    public abstract void show();

    public abstract void hide();

    public abstract boolean anyFit(Point2D point2D);

    public abstract void select();

    public boolean select(Point2D point2D) {
        if (!anyFit(point2D)) return false;
        select();
        return true;
    }

    public abstract void deselect();
}
