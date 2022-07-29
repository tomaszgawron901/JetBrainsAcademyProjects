package cinema.repository;

import cinema.models.Seat;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

@Component
public class SeatsRepository {
    private final int rows = 9;
    private final int columns = 9;
    private final Seat[] seats = new Seat[rows * columns];

    public SeatsRepository() {
        fillSeats();
    }

    private void fillSeats() {
        for (int row = 1; row <= rows; row++) {
            int rowPrice = row <= 4 ? 10 : 8;
            for (int column = 1; column <= columns; column++) {
                this.setSeat(row, column, new Seat(row, column, rowPrice));
            }
        }
    }

    private int getSeatIndex(int row, int column) {
        return (row - 1) * columns + (column - 1);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Seat getSeat(int row, int column) {
        return seats[getSeatIndex(row, column)];
    }

    public void setSeat(int row, int column, Seat seat) {
        seats[getSeatIndex(row, column)] = seat;
    }

    public Collection<Seat> getSeats() {
        return Collections.unmodifiableCollection(Arrays.asList(seats));
    }

    public Seat getSeatByTokenOrDefault(String token) {
        return getSeats().stream()
                .filter(s -> s.getReservationToken() != null && s.getReservationToken().equals(token))
                .findFirst()
                .orElse(null);
    }
}
