package com.vervyle.lw8_oop.controllers;

import com.vervyle.lw8_oop.containers.MyLinkedList;
import com.vervyle.lw8_oop.containers.MyList;
import com.vervyle.lw8_oop.drawable.GraphicElement;
import com.vervyle.lw8_oop.drawable.OutOfPaneException;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import com.vervyle.lw8_oop.factories.ElementFactory;
import com.vervyle.lw8_oop.factories.ElementFactoryImpl;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class PaneController {
    private final ListView<String> tool_elements_list;

    private final MyList<GraphicElement> container;
    private MyList<GraphicElement> selectedGroup;
    private final ElementFactory elementFactory;
    private final ColorPicker tool_color;

    private void selectElement(GraphicElement graphicElement) {
        graphicElement.select();
        selectedGroup.add(graphicElement);
    }

    private void deselectElement(GraphicElement graphicElement) {
        graphicElement.deselect();
        selectedGroup.remove(graphicElement);
    }

    public PaneController(AnchorPane pane, ListView<String> tool_elements_list, ColorPicker tool_color, TextField tool_value) {
        this.tool_elements_list = tool_elements_list;
        container = new MyLinkedList<>();
        this.tool_color = tool_color;
        selectedGroup = new MyLinkedList<>();
        elementFactory = new ElementFactoryImpl(pane, tool_color, tool_value, container, selectedGroup);

        tool_color.setOnAction(actionEvent -> {
            changeColorOnSelectedElements(tool_color.getValue());
        });
        tool_value.setOnAction(actionEvent -> {
            double radius = 0;
            try {
                radius = Double.parseDouble(tool_value.getText());
                resizeSelectedElements(radius);
            } catch (OutOfPaneException e) {
                tool_value.setText(Double.toString(radius));
            } catch (NumberFormatException e) {
                System.out.println("incorrect number");
            }
        });
    }

    public boolean anyFit(Point2D point2D) {
        Iterator<GraphicElement> iterator = container.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().anyFit(point2D))
                return true;
        }
        return false;
    }

    public void selectAll(Point2D point2D) {
        Iterator<GraphicElement> iterator = container.iterator();
        iterator.forEachRemaining(element -> {
            if (element.select(point2D))
                selectedGroup.add(element);
        });
    }

    public boolean selectOne(Point2D point2D) {
        MyLinkedList<GraphicElement> list = (MyLinkedList<GraphicElement>) container;
        Iterator<GraphicElement> iterator = list.descendingIterator();
        GraphicElement regularPolygon;
        while (iterator.hasNext()) {
            regularPolygon = iterator.next();
            if (regularPolygon.select(point2D)) {
                selectedGroup.add(regularPolygon);
                return true;
            }
        }
        return false;
    }

    public void selectLastCreated() {
        GraphicElement graphicElement = container.getLast();
        if (graphicElement != null)
            selectElement(graphicElement);
    }

    public void deselectAll() {
        selectedGroup = new MyLinkedList<>();
        Iterator<GraphicElement> iterator = container.iterator();
        iterator.forEachRemaining(this::deselectElement);
    }

    private void postInitElement(GraphicElement graphicElement) {
        container.add(graphicElement);
        selectElement(graphicElement);
        graphicElement.show();
    }

    public void addElement(Point2D point2D) throws OutOfPaneException {
        deselectAll();
        GraphicElement graphicElement = null;
        if (tool_elements_list.getSelectionModel().getSelectedItem().equals("Circle")) {
            graphicElement = elementFactory.createElement(point2D, ElementFactory.TYPES.CIRCLE);
        }
        if (tool_elements_list.getSelectionModel().getSelectedItem().equals("Square")) {
            graphicElement = elementFactory.createElement(point2D, ElementFactory.TYPES.SQUARE);
        }
        if (tool_elements_list.getSelectionModel().getSelectedItem().equals("Pentagon")) {
            graphicElement = elementFactory.createElement(point2D, ElementFactory.TYPES.PENTAGON);
        }
        if (tool_elements_list.getSelectionModel().getSelectedItem().equals("Polygon")) {
            graphicElement = elementFactory.createElement(point2D, ElementFactory.TYPES.POLYGON);
        }
        if (tool_elements_list.getSelectionModel().getSelectedItem().equals("Triangle")) {
            graphicElement = elementFactory.createElement(point2D, ElementFactory.TYPES.TRIANGLE);
        }
        postInitElement(graphicElement);
    }

    private void deleteElement(GraphicElement graphicElement) {
        graphicElement.hide();
        selectedGroup.remove(graphicElement);
        container.remove(graphicElement);
    }

    public void delete() {
        container.remove(selectedGroup);
        Iterator<GraphicElement> iterator = selectedGroup.iterator();
        iterator.forEachRemaining(GraphicElement::hide);
        selectedGroup = new MyLinkedList<>();
        selectLastCreated();
    }

    public void resizeSelectedElements(double size) throws OutOfPaneException {
        Iterator<GraphicElement> iterator = selectedGroup.iterator();
        GraphicElement graphicElement;
        while (iterator.hasNext()) {
            graphicElement = iterator.next();
            graphicElement.resize(size);
            graphicElement.select();
        }
    }

    public void moveSelectedElements(double deltaX, double deltaY) throws OutOfPaneException {
        Iterator<GraphicElement> iterator = selectedGroup.iterator();
        GraphicElement graphicElement;
        while (iterator.hasNext()) {
            graphicElement = iterator.next();
            graphicElement.move(deltaX, deltaY);
            graphicElement.select();
        }
    }

    public void changeColorOnSelectedElements(Color color) {
        Iterator<GraphicElement> iterator = selectedGroup.iterator();
        GraphicElement graphicElement;
        while (iterator.hasNext()) {
            graphicElement = iterator.next();
            graphicElement.changeColor(color);
            graphicElement.select();
        }
    }
}
