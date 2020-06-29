package pl.bartek.servicebook.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.bartek.servicebook.exception.AddRecordException;
import pl.bartek.servicebook.exception.AddVehicleExcetion;
import pl.bartek.servicebook.exception.CreateVehicleException;
import pl.bartek.servicebook.io.CsvReader;
import pl.bartek.servicebook.io.CsvWriter;
import pl.bartek.servicebook.model.Garage;
import pl.bartek.servicebook.model.ServiceBook;
import pl.bartek.servicebook.model.ServiceRecord;
import pl.bartek.servicebook.model.Vehicle;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainController {

    private static final String LICENSE_PLATE_COLUMN = "Nr rejestracyjny";
    private static final String DATE_COLUMN = "Data";
    private static final String DESCRIPTION_COLUMN = "Opis";

    private static final String VEHICLES_FILE_NAME = "vehicles.csv";
    private static final String RECORDS_FILE_NAME = "records.csv";

    @FXML
    private AddViewDataPaneController addViewDataPaneController;

    @FXML
    private ContentPaneController contentPaneController;

    @FXML
    private MessagePaneController messagePaneController;

    private static final Vehicle NEW_VEHICLE = new Vehicle("Nowy pojazd");
    private static Garage garage;
    private static ServiceBook serviceBook;

    public void initialize() {
        createGarage();
        createServiceBook();
        configureTable();
        contentPaneController.getContentTable().setItems(serviceBook.getServiceBook());
        addViewDataPaneController.getVehicleComboBox().setItems(garage.getVehiclesList());
        configureVehicleComboBox();
        configureAddRecordButton();
        configureViewButton();
        configureDeleteButton();
        configureEditButton();
    }

    public static void saveGarage() {
        CsvWriter.printVehiclesToCsv(garage, VEHICLES_FILE_NAME);
    }

    public static void seveRecords(){
        CsvWriter.printRecordsToCsv(serviceBook, RECORDS_FILE_NAME);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Platform.exit();
        }
    }

    private void createGarage() {
        garage = new Garage(FXCollections.observableArrayList());
        try{
            garage.addVehicle(NEW_VEHICLE);
        } catch (AddVehicleExcetion e) {
            messagePaneController.getMessageTextArea().setText("Zresetuj aplikację");
        }
        if(CsvReader.readVehiclesFromCsv(garage, VEHICLES_FILE_NAME)) {
            messagePaneController.getMessageTextArea().setText("Pomyślnie wczytano plik pojazdów");
        } else {
            messagePaneController.getMessageTextArea().setText("Nie udało się wczytać pliku pojazdów");
        }
    }

    private void createServiceBook() {
        serviceBook = new ServiceBook(FXCollections.observableArrayList());
        if(CsvReader.readRecordsFromCsv(garage, serviceBook, RECORDS_FILE_NAME)) {
            String message = messagePaneController.getMessageTextArea().getText();
            messagePaneController.getMessageTextArea().setText(message + "\nPomyślnie wczytano plik wpsiów");
        } else {
            String message = messagePaneController.getMessageTextArea().getText();
            messagePaneController.getMessageTextArea().setText(message + "\nNie udało się wczytać pliku wpisów");
        }
    }

    private void addRecord(){
        try{
            boolean newVehicle = false;
            Vehicle vehicle = addViewDataPaneController.getVehicleComboBox().getSelectionModel().getSelectedItem();
            if(vehicle.equals(NEW_VEHICLE)) {
                vehicle = createVehicleFromTextFields();
                newVehicle = true;
            }
            if(newVehicle){
                garage.addVehicle(vehicle);
            }
            LocalDate date = createDateFromString(addViewDataPaneController.getDateTextField().getText());
            int mileage = Integer.parseInt(addViewDataPaneController.getMileageTextField().getText());
            String description = addViewDataPaneController.getServiceRecordTextArea().getText();
            ServiceRecord serviceRecord = new ServiceRecord(vehicle, date, mileage, description);
            serviceBook.addRecord(serviceRecord);
            messagePaneController.getMessageTextArea().setText("Pomyślnie dodano wpis do bazy");
            clearVehicleTextFields();
            clearRecordTextFields();
        } catch (CreateVehicleException e) {
            messagePaneController.getMessageTextArea().setText(e.getMessage());
        } catch (NumberFormatException e) {
            messagePaneController.getMessageTextArea().setText("Wprowadź poprawne wartości liczbowe");
        } catch (AddRecordException e) {
            messagePaneController.getMessageTextArea().setText(e.getMessage());
        } catch (AddVehicleExcetion e) {
            messagePaneController.getMessageTextArea().setText(e.getMessage());
        } catch (DateTimeException  | IllegalArgumentException e) {
            messagePaneController.getMessageTextArea().setText("Wprowadź datę w formacie YYYY-MM-DD");
        }
    }

    private void configureTable(){
        TableColumn<ServiceRecord, String> licensePlateColumn = new TableColumn<>(LICENSE_PLATE_COLUMN);
        licensePlateColumn.setCellValueFactory(new PropertyValueFactory<>("vehicle"));

        TableColumn<ServiceRecord, LocalDate> dateColumn = new TableColumn<>(DATE_COLUMN);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<ServiceRecord, String> descriptionColumn = new TableColumn<>(DESCRIPTION_COLUMN);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("shortDescription"));

        contentPaneController.getContentTable().getColumns().add(licensePlateColumn);
        contentPaneController.getContentTable().getColumns().add(dateColumn);
        contentPaneController.getContentTable().getColumns().add(descriptionColumn);
        contentPaneController.getContentTable().getColumns().get(0).setMaxWidth(100);
        contentPaneController.getContentTable().getColumns().get(0).setMinWidth(80);
        contentPaneController.getContentTable().getColumns().get(1).setMaxWidth(100);
        contentPaneController.getContentTable().getColumns().get(1).setMinWidth(80);
        contentPaneController.getContentTable().getColumns().get(2).setMinWidth(300);
    }

    private void configureVehicleComboBox(){
        addViewDataPaneController.getVehicleComboBox().getSelectionModel().select(0);
        addViewDataPaneController.getVehicleComboBox().setOnAction(actionEvent -> {
            if(!addViewDataPaneController.getVehicleComboBox().getSelectionModel().isEmpty()){
                setEditAbility(addViewDataPaneController.getVehicleComboBox().getSelectionModel().getSelectedItem().equals(NEW_VEHICLE));
            }
            messagePaneController.getRecordViewTextArea().clear();
            clearVehicleTextFields();
            clearRecordTextFields();
            configureAddRecordButton();
        });
    }

    private void configureAddRecordButton(){
        addViewDataPaneController.getAddButton().setOnAction(actionEvent -> addRecord());
    }

    private void configureViewButton() {
        contentPaneController.getViewButton().setOnAction(actionEvent -> {
           int index = contentPaneController.getContentTable().getSelectionModel().getSelectedIndex();
           if(index != -1) {
               ServiceRecord serviceRecord = serviceBook.getServiceBook().get(index);
               Vehicle vehicle = serviceRecord.getVehicleObject();
               messagePaneController.getRecordViewTextArea().setText(vehicle.stringRepresentation() + "\n\n" + serviceRecord.stringRepresentation() );
               clearRecordTextFields();
               clearVehicleTextFields();
           }
        });
    }

    private void configureDeleteButton() {
        contentPaneController.getDeleteButton().setOnAction(actionEvent -> {
            int index = contentPaneController.getContentTable().getSelectionModel().getSelectedIndex();
            if(index != -1) {
                serviceBook.removeRecord(index, garage);
                messagePaneController.getMessageTextArea().setText("Pomyślnie usunięto wpis");
            }
        });
    }

    private void configureEditButton() {
        contentPaneController.getEditButton().setOnAction(actionEvent -> {
            editRecord();
        });
    }

    private void editRecord() {

        int index = contentPaneController.getContentTable().getSelectionModel().getSelectedIndex();
        if(index != -1) {
            ServiceRecord serviceRecord = serviceBook.getServiceBook().get(index);
            Vehicle vehicle = serviceRecord.getVehicleObject();
            setTextFields(vehicle, serviceRecord);
            setEditAbility(true);
            addViewDataPaneController.getAddButton().setText("Zatwierdź");
            addViewDataPaneController.getAddButton().setOnAction(actionEvent1 -> {
                try{
                    setValuesInObjectsFromTextFields(vehicle, serviceRecord);
                    clearVehicleTextFields();
                    clearRecordTextFields();
                    contentPaneController.getContentTable().refresh();
                    addViewDataPaneController.getVehicleComboBox().commitValue();
                    messagePaneController.getMessageTextArea().setText("Pomyślnie zmieniono wpis");
                    addViewDataPaneController.getAddButton().setText("Dodaj");
                } catch (NumberFormatException e) {
                    messagePaneController.getMessageTextArea().setText("Wprowadź poprawne wartości liczbowe");
                } catch (DateTimeException  | IllegalArgumentException e) {
                    messagePaneController.getMessageTextArea().setText("Wprowadź datę w formacie YYYY-MM-DD");
                }

            });
        }
    }

    private void setTextFields(Vehicle vehicle, ServiceRecord serviceRecord) {
        addViewDataPaneController.getLicensePlateTextField().setText(vehicle.getLicensePlate());
        addViewDataPaneController.getBrandTextField().setText(vehicle.getBrand());
        addViewDataPaneController.getModelTextField().setText(vehicle.getModel());
        addViewDataPaneController.getOwnerTextField().setText(vehicle.getOwner());
        addViewDataPaneController.getProductionYearTextField().setText(Integer.toString(vehicle.getProductionYear()));
        addViewDataPaneController.getEngineTextField().setText(vehicle.getEngine());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        addViewDataPaneController.getDateTextField().setText(serviceRecord.getDate().format(dateFormatter));
        addViewDataPaneController.getMileageTextField().setText(Integer.toString(serviceRecord.getMileage()));
        addViewDataPaneController.getServiceRecordTextArea().setText(serviceRecord.getDescritpion());
    }

    private void setValuesInObjectsFromTextFields(Vehicle vehicle, ServiceRecord serviceRecord) throws NumberFormatException, DateTimeException, IllegalArgumentException{
        vehicle.setLicensePlate(addViewDataPaneController.getLicensePlateTextField().getText());
        vehicle.setBrand(addViewDataPaneController.getBrandTextField().getText());
        vehicle.setModel(addViewDataPaneController.getModelTextField().getText());
        vehicle.setOwner(addViewDataPaneController.getOwnerTextField().getText());
        vehicle.setProductionYear(Integer.parseInt(addViewDataPaneController.getProductionYearTextField().getText()));
        vehicle.setEngine(addViewDataPaneController.getEngineTextField().getText());
        serviceRecord.setDate(createDateFromString(addViewDataPaneController.getDateTextField().getText()));
        serviceRecord.setMileage(Integer.parseInt(addViewDataPaneController.getMileageTextField().getText()));
        serviceRecord.setDescritpion(addViewDataPaneController.getServiceRecordTextArea().getText());

    }

    private Vehicle createVehicleFromTextFields() throws CreateVehicleException, NumberFormatException{
        String licensePlate = addViewDataPaneController.getLicensePlateTextField().getText();
        String brand = addViewDataPaneController.getBrandTextField().getText();
        String model = addViewDataPaneController.getModelTextField().getText();
        String owner = addViewDataPaneController.getOwnerTextField().getText();
        int productionYear = Integer.parseInt(addViewDataPaneController.getProductionYearTextField().getText());
        String engine = addViewDataPaneController.getEngineTextField().getText();
        return new Vehicle(licensePlate, brand, model, owner, productionYear, engine);
    }

    private LocalDate createDateFromString(String inputDate) throws IllegalArgumentException {
        String[] splited = inputDate.split("-");
        if (splited.length < 3) {
            throw new IllegalArgumentException();
        }
        int year = Integer.parseInt(splited[0]);
        int month = Integer.parseInt(splited[1]);
        int day = Integer.parseInt(splited[2]);
        LocalDate date = LocalDate.of(year, month, day);
        return date;
    }

    private void setEditAbility(boolean value) {
        addViewDataPaneController.getLicensePlateTextField().setEditable(value);
        addViewDataPaneController.getBrandTextField().setEditable(value);
        addViewDataPaneController.getModelTextField().setEditable(value);
        addViewDataPaneController.getOwnerTextField().setEditable(value);
        addViewDataPaneController.getProductionYearTextField().setEditable(value);
        addViewDataPaneController.getEngineTextField().setEditable(value);
    }

    private void clearVehicleTextFields(){
        addViewDataPaneController.getLicensePlateTextField().clear();
        addViewDataPaneController.getBrandTextField().clear();
        addViewDataPaneController.getModelTextField().clear();
        addViewDataPaneController.getOwnerTextField().clear();
        addViewDataPaneController.getProductionYearTextField().clear();
        addViewDataPaneController.getEngineTextField().clear();
    }

    private void clearRecordTextFields(){
        addViewDataPaneController.getDateTextField().clear();
        addViewDataPaneController.getMileageTextField().clear();
        addViewDataPaneController.getServiceRecordTextArea().clear();

    }

}
