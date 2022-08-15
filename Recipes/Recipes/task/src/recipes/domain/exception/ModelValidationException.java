package recipes.domain.exception;

public class ModelValidationException extends RuntimeException {
    public ModelValidationException(String message) {
        super(message);
    }
}
