package com.vervyle.lw8_oop.drawable.leafs;

import com.vervyle.lw8_oop.drawable.GraphicElement;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import com.vervyle.lw8_oop.drawable.render.Segment2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.Optional;

public class LLine extends GraphicElement {

    private Segment2D segment2D;
    private Shape shape;
    private Color color;

    public LLine(Segment2D segment2D, Pane pane, Color color) {
        super(pane);
        this.color = color;
        this.segment2D = segment2D;
        shape = new Line(segment2D.start.x, segment2D.start.y, segment2D.end.x, segment2D.end.y);
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
        return false;
    }

    @Override
    public void select() {
        shape.setStyle("-fx-stroke: #FF0000; -fx-stroke-width: 3px");
    }

    @Override
    public void deselect() {
        int red = ((int) (color.getRed() * 256)) % 256;
        int green = ((int) (color.getGreen() * 256)) % 256;
        int blue = ((int) (color.getBlue() * 256)) % 256;

        String strRed = Optional.of(red).map(integer -> {
            if (integer == 0)
                return "00";
            String var = Integer.toHexString(integer);
            if (var.length() == 1)
                var = "0" + var;
            return var;
        }).get();

        String strGreen = Optional.of(green).map(integer -> {
            if (integer == 0)
                return "00";
            String var = Integer.toHexString(integer);
            if (var.length() == 1)
                var = "0" + var;
            return var;
        }).get();

        String strBlue = Optional.of(blue).map(integer -> {
            if (integer == 0)
                return "00";
            String var = Integer.toHexString(integer);
            if (var.length() == 1)
                var = "0" + var;
            return var;
        }).get();

        String val = strRed + strGreen + strBlue;

        System.out.println(val);
        shape.setStyle("-fx-stroke: #" + val + "; -fx-stroke-width: 3px");
    }
}
