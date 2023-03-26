package com.vervyle.lw8_oop.controllers;

import com.vervyle.lw8_oop.drawable.render.Point2D;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PaintController implements Initializable {
    private Workspace workspace;
    @FXML
    private AnchorPane main_pane;
    @FXML
    private ScrollPane scroll_pane;
    @FXML
    private CheckBox tool_ctrl_enable;
    @FXML
    private ListView<String> tool_element_list;
    @FXML
    private CheckBox tool_groups_enable;
    @FXML
    private Label tool_mouse_location;
    @FXML
    private Label tool_pane_size;
    @FXML
    private Button tool_scale_minus;
    @FXML
    private Button tool_scale_plus;
    @FXML
    private ColorPicker tool_color;
    @FXML
    private TextField tool_value;
    @FXML
    private MenuItem menu_save;
    @FXML
    private MenuItem menu_exit;
    @FXML
    private MenuItem menu_deselect_all;

    private void onSave(ActionEvent actionEvent) {

    }

    private void onExit(ActionEvent actionEvent) {

    }

    private void mouseClickedHandler(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            Point2D point2D = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            if (!workspace.anyFit(point2D)) {
                workspace.addElement(point2D);
                return;
            }
            workspace.selectElement(mouseEvent);
        }
    }

    private void keyPressedHandler(KeyEvent keyEvent) {
        double step = Double.parseDouble(tool_value.getText());
        if (keyEvent.getCode() == KeyCode.DELETE)
            workspace.deleteSelectedElements();
        if (keyEvent.getCode() == KeyCode.W)
            workspace.moveElements(0, -1 * step);
        if (keyEvent.getCode() == KeyCode.S)
            workspace.moveElements(0, step);
        if (keyEvent.getCode() == KeyCode.A)
            workspace.moveElements(-1 * step, 0);
        if (keyEvent.getCode() == KeyCode.D)
            workspace.moveElements(step, 0);
    }

    private void initElementsGUI() {
        String[] elements = {
                "Circle",
                "Triangle",
                "Square",
                "Pentagon",
                "Polygon"
        };
        tool_element_list.getItems().addAll(elements);
        menu_deselect_all.setOnAction(actionEvent -> {
            workspace.deselectAll();
        });
    }

    private void workspaceInit() {
        workspace = new Workspace(main_pane, tool_color, tool_value, tool_element_list);
        tool_ctrl_enable.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal && tool_groups_enable.isSelected()) {
                workspace.setState(Workspace.ADD_TO_SELECTION_INTERSECTIONS_STATE);
                return;
            }
            if (newVal && !tool_groups_enable.isSelected()) {
                workspace.setState(Workspace.ADD_TO_SELECTION_SINGLES_STATE);
                return;
            }
            if (oldVal && tool_groups_enable.isSelected()) {
                workspace.setState(Workspace.SELECT_INTERSECTIONS_STATE);
                return;
            }
            workspace.setState(Workspace.DEFAULT_STATE);
        });
        tool_groups_enable.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (newVal && tool_ctrl_enable.isSelected()) {
                workspace.setState(Workspace.ADD_TO_SELECTION_INTERSECTIONS_STATE);
                return;
            }
            if (newVal && !tool_ctrl_enable.isSelected()) {
                workspace.setState(Workspace.SELECT_INTERSECTIONS_STATE);
                return;
            }
            if (oldVal && tool_ctrl_enable.isSelected()) {
                workspace.setState(Workspace.ADD_TO_SELECTION_SINGLES_STATE);
                return;
            }
            workspace.setState(Workspace.DEFAULT_STATE);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initElementsGUI();
        menu_save.setOnAction(this::onSave);
        menu_exit.setOnAction(this::onExit);
        main_pane.setOnMouseClicked(this::mouseClickedHandler);
        scroll_pane.setOnKeyPressed(this::keyPressedHandler);
        workspaceInit();
    }
}

