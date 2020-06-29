package pl.bartek.servicebook.io;

import pl.bartek.servicebook.exception.AddRecordException;
import pl.bartek.servicebook.exception.AddVehicleExcetion;
import pl.bartek.servicebook.exception.CreateVehicleException;
import pl.bartek.servicebook.exception.VehicleNotFoundException;
import pl.bartek.servicebook.model.Garage;
import pl.bartek.servicebook.model.ServiceBook;
import pl.bartek.servicebook.model.ServiceRecord;
import pl.bartek.servicebook.model.Vehicle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class CsvReader {

    public static boolean readVehiclesFromCsv(Garage garage, String fileName){
        try(FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
        ) {
            String line = "";
            while((line = br.readLine()) != null) {
                Vehicle vehicle = createVehicleFromLine(line);
                garage.addVehicle(vehicle);
            }
            return true;

        }catch (IOException  |ArrayIndexOutOfBoundsException | CreateVehicleException | AddVehicleExcetion e) {
            return false;
        }

    }

    public static boolean readRecordsFromCsv(Garage garage, ServiceBook serviceBook, String fileName){
        try(FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
        ) {
            String line = "";
            while((line = br.readLine()) != null) {
                ServiceRecord record = createRecordFromLine( line, garage);
                if(record != null) {
                    serviceBook.addRecord(record);
                }
            }
            return true;

        }catch (IOException | AddRecordException e) {
            return false;
        }
    }

    private static Vehicle createVehicleFromLine(String line)  throws CreateVehicleException{
        String[] lineSplited = line.split(";");
        return new Vehicle(lineSplited[0], lineSplited[1], lineSplited[2], lineSplited[3], Integer.parseInt(lineSplited[4]),
                lineSplited[5]);
    }

    private static ServiceRecord createRecordFromLine(String line, Garage garage) {
        String[] lineSplited = line.split(";");
        String[] datesplited = lineSplited[1].split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(datesplited[0]), Integer.parseInt(datesplited[1]), Integer.parseInt(datesplited[2]));
        Vehicle vehicle = null;
        try{
            vehicle = garage.getVehicleFromLicensePlate(lineSplited[0]);
        } catch (VehicleNotFoundException e) {
            return null;
        }
        return new ServiceRecord(vehicle, date,Integer.parseInt(lineSplited[2]), lineSplited[3].replaceAll("<nowaLinia>", "\n") );
    }

}
