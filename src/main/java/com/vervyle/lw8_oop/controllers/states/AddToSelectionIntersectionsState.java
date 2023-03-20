package com.vervyle.lw8_oop.controllers.states;

import com.vervyle.lw8_oop.controllers.PaneController;
import com.vervyle.lw8_oop.drawable.render.Point2D;

public class AddToSelectionIntersectionsState implements WorkspaceState {
    @Override
    public boolean selectElement(PaneController paneController, Point2D point2D) {
        boolean res = paneController.selectOne(point2D);
        paneController.selectAll(point2D);
        return res;
    }
}
