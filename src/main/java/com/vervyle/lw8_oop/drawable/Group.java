package com.vervyle.lw8_oop.drawable;

import com.vervyle.lw8_oop.containers.MyLinkedList;
import com.vervyle.lw8_oop.containers.MyList;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.layout.Pane;

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
}
