package carsharing.dao;

public class NotAbleToConnectException extends Exception {
    public NotAbleToConnectException(Throwable e) {
        super(e);
    }
}
