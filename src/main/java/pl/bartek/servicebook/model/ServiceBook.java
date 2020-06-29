package pl.bartek.servicebook.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.bartek.servicebook.exception.AddRecordException;

import java.util.List;

public class ServiceBook {
    private ObservableList<ServiceRecord> serviceBook;

    public ServiceBook(List<ServiceRecord> serviceBook) {
        this.serviceBook = FXCollections.observableList(serviceBook);
    }

    public ObservableList<ServiceRecord> getServiceBook() {
        return serviceBook;
    }

    public void addRecord(ServiceRecord serviceRecord) throws AddRecordException {
        if(serviceBook.contains(serviceRecord)) {
            throw new AddRecordException("Taki wpis już istnieje w bazie");
        } else {
            serviceBook.add(serviceRecord);
        }
    }

    public void removeRecord(int index, Garage garage) {
        Vehicle vehicle = serviceBook.get(index).getVehicleObject();
        serviceBook.remove(index);
        int numberOfRecordsForDeletedVehicle = 0;
        for(ServiceRecord serviceRecord:serviceBook) {
            if(serviceRecord.getVehicleObject().equals(vehicle)){
                numberOfRecordsForDeletedVehicle ++;
            }
        }
        if(numberOfRecordsForDeletedVehicle == 0) {
            garage.getVehiclesList().remove(vehicle);
        }
    }

}
