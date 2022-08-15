package recipes.service.implementations;

import org.springframework.security.core.context.SecurityContextHolder;
import recipes.domain.model.AppUserDetails;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

public abstract class BaseApplicationService {
    protected final Validator validator;

    public BaseApplicationService(Validator validator) {
        this.validator = validator;
    }


    /**
     * @param object object to validate
     * @throws ConstraintViolationException if the object is not valid
     */
    protected <T> void ensureValid(T object) {
        var violations = validator.validate(object);
        if(violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
    }

    protected AppUserDetails getCurrentUser() {
        return (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
