package com.vervyle.lw8_oop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vervyle.lw8_oop.containers.MyLinkedList;
import com.vervyle.lw8_oop.containers.MyList;
import com.vervyle.lw8_oop.drawable.ElementType;
import com.vervyle.lw8_oop.drawable.GraphicElement;
import com.vervyle.lw8_oop.drawable.utils.OutOfPaneException;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import com.vervyle.lw8_oop.factories.ElementFactory;
import com.vervyle.lw8_oop.factories.ElementFactoryImpl;
import com.vervyle.lw8_oop.factories.ElementLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.Iterator;

public class PaneController {
    private final ListView<String> tool_elements_list;

    private final MyList<GraphicElement> container;
    private MyList<GraphicElement> selectedGroup;
    private final ElementFactory elementFactory;
    private final ColorPicker tool_color;
    private AnchorPane pane;

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
        this.pane = pane;
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
            graphicElement = elementFactory.createElement(point2D, ElementType.CIRCLE);
        }
        if (tool_elements_list.getSelectionModel().getSelectedItem().equals("Square")) {
            graphicElement = elementFactory.createElement(point2D, ElementType.SQUARE);
        }
        if (tool_elements_list.getSelectionModel().getSelectedItem().equals("Pentagon")) {
            graphicElement = elementFactory.createElement(point2D, ElementType.PENTAGON);
        }
        if (tool_elements_list.getSelectionModel().getSelectedItem().equals("Polygon")) {
            graphicElement = elementFactory.createElement(point2D, ElementType.POLYGON);
        }
        if (tool_elements_list.getSelectionModel().getSelectedItem().equals("Triangle")) {
            graphicElement = elementFactory.createElement(point2D, ElementType.TRIANGLE);
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

    public void save() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(GraphicElement.PATH, false);
            fileWriter.write(container.size() + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator<GraphicElement> iterator = container.iterator();
        GraphicElement graphicElement;
        ObjectMapper objectMapper = new ObjectMapper();
        while (iterator.hasNext()) {
            graphicElement = iterator.next();
            try {
                fileWriter = new FileWriter(GraphicElement.PATH, true);
                objectMapper.writeValue(fileWriter, graphicElement.type);
                fileWriter = new FileWriter(GraphicElement.PATH, true);
                fileWriter.write("\n");
                fileWriter.flush();
                graphicElement.save(objectMapper);
                fileWriter = new FileWriter(GraphicElement.PATH, true);
                fileWriter.write("\n");
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearPane() {
        Iterator<GraphicElement> iterator = container.iterator();
        while (iterator.hasNext()) {
            deleteElement(iterator.next());
        }
    }

    private void addElementsToPane(MyList<GraphicElement> list) {
        list.iterator().forEachRemaining(graphicElement -> {
            graphicElement.postInit(pane);
            container.add(graphicElement);
            graphicElement.deselect();
            graphicElement.show();
        });
    }

    public void load() {
        File file = new File(GraphicElement.PATH);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String[] strings = null;
        int size = 0;
        try {
            fileReader = new FileReader(file);
            size = fileReader.read() - '0';
            bufferedReader = new BufferedReader(fileReader);
            strings = new String[size * 2];
            bufferedReader.readLine();
            for (int i = 0; i < size * 2; i++) {
                strings[i] = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        GraphicElement graphicElement;
        MyList<GraphicElement> list = new MyLinkedList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < size; i++) {
            graphicElement = ElementLoader.loadElement(objectMapper, strings[2 * i], strings[2 * i + 1]);
            list.add(graphicElement);
        }

        clearPane();
        addElementsToPane(list);
    }
}
