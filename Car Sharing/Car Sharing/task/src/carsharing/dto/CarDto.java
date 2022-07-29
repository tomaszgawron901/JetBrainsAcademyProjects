package carsharing.dto;

public class CarDto {
    private final int id;
    private final String name;
    private final int companyId;

    public CarDto(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }
}
