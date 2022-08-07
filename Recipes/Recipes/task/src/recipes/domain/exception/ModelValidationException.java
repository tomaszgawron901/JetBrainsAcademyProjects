package recipes.domain.exception;

public class ModelValidationException extends Exception {
    public ModelValidationException(String message) {
        super(message);
    }
}
