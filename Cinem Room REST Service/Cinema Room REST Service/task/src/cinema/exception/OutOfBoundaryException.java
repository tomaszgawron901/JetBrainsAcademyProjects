package cinema.exception;

public class OutOfBoundaryException extends RuntimeException {
    public OutOfBoundaryException() {
        super("The number of a row or a column is out of bounds!");
    }
}
