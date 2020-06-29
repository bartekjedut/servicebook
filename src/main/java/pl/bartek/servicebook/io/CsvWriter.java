package pl.bartek.servicebook.io;

import javafx.collections.ObservableList;
import pl.bartek.servicebook.model.Garage;
import pl.bartek.servicebook.model.ServiceBook;
import pl.bartek.servicebook.model.ServiceRecord;
import pl.bartek.servicebook.model.Vehicle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    private static final Vehicle NEW_VEHICLE = new Vehicle("Nowy pojazd");

    public static boolean printVehiclesToCsv(Garage garage, String fileName){
        try(FileWriter fr = new FileWriter(fileName);
            BufferedWriter br = new BufferedWriter(fr);)
        {
            ObservableList<Vehicle> vehiclesList = garage.getVehiclesList();
            garage.getVehiclesList().remove(NEW_VEHICLE);
            for(Vehicle vehicle:vehiclesList){
                br.write(vehicle.toCsv());
                br.write("\n");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean printRecordsToCsv(ServiceBook serviceBook, String fileName){
        try(FileWriter fr = new FileWriter(fileName);
            BufferedWriter br = new BufferedWriter(fr);)
        {
            ObservableList<ServiceRecord> recordsList = serviceBook.getServiceBook();
            for(ServiceRecord record:recordsList){
                br.write(record.toCsv());
                br.write("\n");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
