package pl.bartek.servicebook.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import pl.bartek.servicebook.model.ServiceRecord;

public class ContentPaneController {

    @FXML
    private TableView<ServiceRecord> contentTable;

    @FXML
    private Button viewButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    public TableView<ServiceRecord> getContentTable() {
        return contentTable;
    }

    public Button getViewButton() {
        return viewButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void initialize() {
    }


}
