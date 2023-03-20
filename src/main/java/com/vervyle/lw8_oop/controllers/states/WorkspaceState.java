package com.vervyle.lw8_oop.controllers.states;

import com.vervyle.lw8_oop.controllers.PaneController;
import com.vervyle.lw8_oop.drawable.render.Point2D;

public interface WorkspaceState {
    boolean selectElement(PaneController paneController, Point2D point2D);
}
