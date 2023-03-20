package com.vervyle.lw8_oop.controllers.states;

import com.vervyle.lw8_oop.controllers.PaneController;
import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.scene.input.MouseEvent;

public class AddToSelectionSinglesState implements WorkspaceState {
    @Override
    public boolean selectElement(PaneController paneController, Point2D point2D) {
        return paneController.selectOne(point2D);
    }
}
