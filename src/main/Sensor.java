package main;

public class Sensor {

    private Address address;
    private int id;
    private Location location;
    private String name;
    private Double pollutionLevel;
    private String vendor;

    public Address getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public Double getPollutionLevel() {
        return pollutionLevel;
    }

    public String getVendor() {
        return vendor;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPollutionLevel(Double pollutionLevel) {
        this.pollutionLevel = pollutionLevel;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
