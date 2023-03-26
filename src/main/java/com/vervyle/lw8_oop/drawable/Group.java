package com.vervyle.lw8_oop.drawable;

import com.vervyle.lw8_oop.containers.MyLinkedList;
import com.vervyle.lw8_oop.containers.MyList;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class Group extends GraphicElement {
    private final MyList<GraphicElement> children;

    public Group(Pane pane) {
        super(pane);
        children = new MyLinkedList<>();
    }

    public void addElement(GraphicElement graphicElement) {
        children.add(graphicElement);
    }

    public void removeElement(GraphicElement graphicElement) {
        children.remove(graphicElement);
    }

    private Iterator<GraphicElement> iterator() {
        return children.iterator();
    }

    @Override
    public void show() {
        iterator().forEachRemaining(graphicElement -> graphicElement.show());
    }

    @Override
    public void hide() {
        iterator().forEachRemaining(graphicElement -> graphicElement.show());
    }

    @Override
    public boolean anyFit(Point2D point2D) {
        Iterator<GraphicElement> iterator = iterator();
        while (iterator.hasNext()) {
            if (iterator.next().anyFit(point2D))
                return true;
        }
        return false;
    }

    public boolean allFit(Point2D point2D) {
        Iterator<GraphicElement> iterator = iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().anyFit(point2D))
                return false;
        }
        return true;
    }

    @Override
    public void select() {
        iterator().forEachRemaining(GraphicElement::select);
    }

    @Override
    public void deselect() {
        iterator().forEachRemaining(GraphicElement::deselect);
    }

    @Override
    public void resize(double newSize) throws OutOfPaneException{
        Iterator<GraphicElement> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next().resize(newSize);
        }
    }

    @Override
    public void changeColor(Color newColor) {
        Iterator<GraphicElement> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next().changeColor(newColor);
        }
    }

    @Override
    public void move(double deltaX, double deltaY) throws OutOfPaneException {
        if (isOutOfPane(deltaX, deltaY))
            throw new OutOfPaneException();
        Iterator<GraphicElement> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next().move(deltaX, deltaY);
        }
    }

    @Override
    public boolean isOutOfPane(double deltaX, double deltaY) {
        Iterator<GraphicElement> iterator = iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isOutOfPane(deltaX, deltaY))
                return true;
        }
        return false;
    }

    @Override
    public boolean isOutOfPane() {
        Iterator<GraphicElement> iterator = iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isOutOfPane())
                return true;
        }
        return false;
    }
}
