package cinema.dto;

public class AvailableSeatsDTO {
    private int total_rows;
    private int total_columns;
    private SeatDTO[] available_seats;

    public AvailableSeatsDTO() {
    }

    public AvailableSeatsDTO(int total_rows, int total_columns, SeatDTO[] seats) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.available_seats = seats;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public SeatDTO[] getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(SeatDTO[] available_seats) {
        this.available_seats = available_seats;
    }
}
