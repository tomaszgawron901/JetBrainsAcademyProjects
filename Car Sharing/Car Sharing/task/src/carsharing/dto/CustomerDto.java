package carsharing.dto;

public class CustomerDto {
    private final int id;
    private final String name;
    private final int rentedCarId;

    public CustomerDto(int id, String name, int rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }
}
