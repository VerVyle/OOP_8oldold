package com.vervyle.lw8_oop.drawable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import com.vervyle.lw8_oop.drawable.utils.OutOfPaneException;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.FileWriter;

public abstract class GraphicElement {
    @JsonIgnore
    public static final String PATH = "C:\\temp\\LW8\\obj.ser";

    @JsonIgnore
    protected Pane pane;
    @JsonIgnore
    public boolean isSelected;
    @JsonIgnore
    public ElementType type;

    protected GraphicElement(Pane pane) {
        this.pane = pane;
        isSelected = false;
    }

    protected GraphicElement() {}

    public Pane getPane() {
        return pane;
    }

    @JsonIgnore
    public boolean isSelected() {
        return isSelected;
    }

    public ElementType getType() {
        return type;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public abstract void show();

    public abstract void hide();

    public abstract boolean anyFit(Point2D point2D);

    @JsonIgnore
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

    @JsonIgnore
    public abstract boolean isOutOfPane();

    public void save(ObjectMapper mapper) {
        try {
            FileWriter fileWriter = new FileWriter(GraphicElement.PATH, true);
            mapper.writeValue(fileWriter, this);

            String jsonString = mapper.writeValueAsString(this);
            System.out.println(jsonString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //public abstract void load(ObjectMapper objectMapper, String value);

    public abstract void postInit(Pane pane);
}
