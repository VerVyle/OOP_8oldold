package com.vervyle.lw8_oop.controllers;

import com.vervyle.lw8_oop.controllers.states.*;
import com.vervyle.lw8_oop.drawable.OutOfPaneException;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Workspace {

    public static final WorkspaceState DEFAULT_STATE;
    public static final WorkspaceState ADD_TO_SELECTION_INTERSECTIONS_STATE;
    public static final WorkspaceState ADD_TO_SELECTION_SINGLES_STATE;
    public static final WorkspaceState SELECT_INTERSECTIONS_STATE;

    private final PaneController paneController;
    private WorkspaceState state;

    public Workspace(AnchorPane anchorPane, ColorPicker tool_color, TextField tool_value, ListView<String> listView) {
        paneController = new PaneController(anchorPane, listView, tool_color, tool_value);
        state = DEFAULT_STATE;
    }

    public boolean anyFit(Point2D point2D) {
        return paneController.anyFit(point2D);
    }

    public void addElement(Point2D point2D) {
        try {
            paneController.addElement(point2D);
        } catch (OutOfPaneException e) {
            System.out.println("Cannot create elements because it's out of pane!");
        }
    }

    public void deselectAll() {
        paneController.deselectAll();
    }

    public boolean selectElement(MouseEvent mouseEvent) {
        if (!mouseEvent.isControlDown())
            paneController.deselectAll();
        return state.selectElement(paneController, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
    }

    public void deleteSelectedElements() {
        paneController.delete();
    }

    public void setState(WorkspaceState state) {
        this.state = state;
    }

    public void moveElements(double deltaX, double deltaY) {
        try {
            paneController.moveSelectedElements(deltaX, deltaY);
        } catch (OutOfPaneException e) {
            System.out.println("Cannot move elements because they would be out of pane!");
        }
    }

    static {
        DEFAULT_STATE = new DefaultState();
        SELECT_INTERSECTIONS_STATE = new SelectIntersectionsState();
        ADD_TO_SELECTION_INTERSECTIONS_STATE = new AddToSelectionIntersectionsState();
        ADD_TO_SELECTION_SINGLES_STATE = new AddToSelectionSinglesState();
    }
}
