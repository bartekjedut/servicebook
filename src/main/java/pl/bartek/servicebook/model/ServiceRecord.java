package pl.bartek.servicebook.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ServiceRecord {
    private Vehicle vehicle;
    private LocalDate date;
    private int mileage;
    private String description;
    private String shortDescription;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ServiceRecord(Vehicle vehicle, LocalDate date, int mileage, String description) {
        this.vehicle = vehicle;
        this.date = date;
        this.mileage = mileage;
        this.description = description;
        if(description.length() > 60) {
            shortDescription = description.substring(0, 59);
        } else {
            shortDescription = description;
        }
        shortDescription += "...";
    }

    public String getVehicle() {
        return vehicle.getLicensePlate();
    }

    public Vehicle getVehicleObject() {
        return vehicle;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getDescritpion() {
        return description;
    }

    public void setDescritpion(String descritpion) {
        this.description = descritpion;
        if(description.length() > 60) {
            this.shortDescription = description.substring(0, 59);
        } else {
            this.shortDescription = description;
        }
        this.shortDescription += "...";
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRecord that = (ServiceRecord) o;
        return mileage == that.mileage &&
                Objects.equals(vehicle, that.vehicle) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description) &&
                Objects.equals(shortDescription, that.shortDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, date, description, shortDescription);
    }

    public String stringRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("Data wpisu: ");
        sb.append(date.format(dateFormatter));
        sb.append(" , przebieg: ");
        sb.append(mileage);
        sb.append("\n");
        sb.append(description);
        return sb.toString();
    }

    public String toCsv(){
        StringBuilder sb = new StringBuilder();
        sb.append(vehicle.getLicensePlate());
        sb.append(";");
        sb.append(date.format(dateFormatter));
        sb.append(";");
        sb.append(mileage);
        sb.append(";");
        sb.append(description.replaceAll("\n", "<nowaLinia>"));
        return sb.toString();
    }

}
