package com.vervyle.lw8_oop.factories;

import com.vervyle.lw8_oop.containers.MyList;
import com.vervyle.lw8_oop.drawable.*;
import com.vervyle.lw8_oop.drawable.leafs.*;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import com.vervyle.lw8_oop.drawable.render.Segment2D;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ElementFactoryImpl implements ElementFactory {

    private final AnchorPane pane;
    private final ColorPicker tool_color;
    private final TextField tool_value;
    private final MyList<GraphicElement> container;
    private final MyList<GraphicElement> selectedGroup;

    public ElementFactoryImpl(AnchorPane pane, ColorPicker tool_color, TextField tool_value, MyList<GraphicElement> container, MyList<GraphicElement> selectedGroup) {
        this.pane = pane;
        this.tool_color = tool_color;
        this.tool_value = tool_value;
        this.container = container;
        this.selectedGroup = selectedGroup;
    }

    @Override
    public GraphicElement createElement(Point2D center, TYPES type) {
        switch (type) {
            case LINE -> {
                GraphicElement lastCreated = container.getLast();
                if (lastCreated instanceof PPoint) {
                    Point2D start = ((PPoint) lastCreated).getPoint2D();
                    lastCreated.hide();
                    selectedGroup.remove(lastCreated);
                    container.remove(lastCreated);
                    return new LLine(new Segment2D(start, center), pane, tool_color.getValue());
                }
                return new PPoint(pane, center, tool_color.getValue());
            }
            case CIRCLE -> {
                return new CCircle(center, Double.parseDouble(tool_value.getText()), pane, tool_color.getValue());
            }
            case TRIANGLE -> {
                return new TTriangle(center, Double.parseDouble(tool_value.getText()), pane, tool_color.getValue());
            }
            case SQUARE -> {
                return new SSquare(center, Double.parseDouble(tool_value.getText()), pane, tool_color.getValue());
            }
            case PENTAGON -> {
                return new PPentagon(center, Double.parseDouble(tool_value.getText()), pane, tool_color.getValue());
            }
            case POLYGON -> {
                return new PPolygon(center, Double.parseDouble(tool_value.getText()), pane, tool_color.getValue());
            }
            default -> throw new UnsupportedOperationException();
        }
    }
}
