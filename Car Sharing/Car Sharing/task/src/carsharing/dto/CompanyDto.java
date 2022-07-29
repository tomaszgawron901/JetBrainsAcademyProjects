package carsharing.dto;

public class CompanyDto {
    private final int id;
    private final String name;

    public CompanyDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
