package pl.bartek.servicebook.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.bartek.servicebook.exception.AddVehicleExcetion;
import pl.bartek.servicebook.exception.VehicleNotFoundException;
import java.util.List;

public class Garage {

    private ObservableList<Vehicle> vehiclesList;

    public Garage(List<Vehicle> vehiclesList) {
        this.vehiclesList = FXCollections.observableList(vehiclesList);
    }


    public ObservableList<Vehicle> getVehiclesList() {
        return vehiclesList;
    }

    public void addVehicle(Vehicle vehicle) throws AddVehicleExcetion {
        if (vehiclesList.contains(vehicle)) {
            throw new AddVehicleExcetion("Pojazd już istnieje w bazie");
        } else {
            vehiclesList.add(vehicle);
        }
    }

    public Vehicle getVehicleFromLicensePlate(String licensePlate) throws VehicleNotFoundException {
        Vehicle vehicle = null;
        for(Vehicle vehicleToReturn:vehiclesList) {
            if(vehicleToReturn.getLicensePlate().equals(licensePlate)) {
                vehicle = vehicleToReturn;
            }
        }
        if (vehicle == null) {
            throw new VehicleNotFoundException("Nie znaleziono pojazdu w bazie");
        }
        return vehicle;
    }
}
