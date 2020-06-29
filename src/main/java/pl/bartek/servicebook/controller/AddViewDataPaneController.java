package pl.bartek.servicebook.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.bartek.servicebook.model.Vehicle;

public class AddViewDataPaneController {

    @FXML
    private ComboBox<Vehicle> vehicleComboBox;

    @FXML
    private TextField licensePlateTextField;

    @FXML
    private TextField brandTextField;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField ownerTextField;

    @FXML
    private TextField productionYearTextField;

    @FXML
    private TextField engineTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private TextField mileageTextField;

    @FXML
    private TextArea serviceRecordTextArea;

    @FXML
    private Button addButton;

    public ComboBox<Vehicle> getVehicleComboBox() {
        return vehicleComboBox;
    }

    public TextField getLicensePlateTextField() {
        return licensePlateTextField;
    }

    public TextField getBrandTextField() {
        return brandTextField;
    }

    public TextField getModelTextField() {
        return modelTextField;
    }

    public TextField getOwnerTextField() {
        return ownerTextField;
    }

    public TextField getProductionYearTextField() {
        return productionYearTextField;
    }

    public TextField getEngineTextField() {
        return engineTextField;
    }

    public TextField getDateTextField() {
        return dateTextField;
    }

    public TextField getMileageTextField() {
        return mileageTextField;
    }

    public TextArea getServiceRecordTextArea() {
        return serviceRecordTextArea;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void initialize(){

    }



}
