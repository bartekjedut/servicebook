package pl.bartek.servicebook.model;

import pl.bartek.servicebook.exception.CreateVehicleException;

import java.util.Objects;

public class Vehicle {
    private String licensePlate;
    private String brand;
    private String model;
    private String owner;
    private int productionYear;
    private String engine;


    public Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Vehicle(String licensePlate, String brand, String model, String owner, int productionYear, String engine) throws CreateVehicleException {
        if(licensePlate.isEmpty()) {
            throw new CreateVehicleException("Numer rejestracyjny nie może być pusty");
        }
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.owner = owner;
        this.productionYear = productionYear;
        this.engine = engine;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return productionYear == vehicle.productionYear &&
                Objects.equals(licensePlate, vehicle.licensePlate) &&
                Objects.equals(brand, vehicle.brand) &&
                Objects.equals(model, vehicle.model) &&
                Objects.equals(owner, vehicle.owner) &&
                Objects.equals(engine, vehicle.engine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, brand, model, owner, productionYear, engine);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!licensePlate.equals("Nowy pojazd")){
            sb.append("Nr rejestracyjny: ");
            sb.append(licensePlate);
            sb.append(", marka: ");
            sb.append(brand);
            sb.append(", model: ");
            sb.append(model);
        } else {
            sb.append("Nowy pojazd");
        }
        return sb.toString();
    }

    public String stringRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nr rejestracyjny: ");
        sb.append(licensePlate);
        sb.append(", marka: ");
        sb.append(brand);
        sb.append(", model: ");
        sb.append(model);
        sb.append(", rok produkcji: ");
        sb.append(productionYear);
        sb.append(", właściciel: ");
        sb.append(owner);
        sb.append(", silnik: ");
        sb.append(engine);
        return sb.toString();
    }

    public String toCsv(){
        StringBuilder sb = new StringBuilder();
        sb.append(licensePlate);
        sb.append(";");
        sb.append(brand);
        sb.append(";");
        sb.append(model);
        sb.append(";");
        sb.append(owner);
        sb.append(";");
        sb.append(productionYear);
        sb.append(";");
        sb.append(engine);
        sb.append(";");
        return sb.toString();
    }

}
