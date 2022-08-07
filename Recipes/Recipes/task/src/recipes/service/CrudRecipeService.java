package recipes.service;

import org.springframework.stereotype.Service;
import recipes.domain.model.Recipe;
import recipes.mapping.DomainToDtoMapping;
import recipes.mapping.DtoToDomainMapping;
import recipes.repository.IRecipeRepository;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Optional;

@Service
public class CrudRecipeService implements IRecipeService {
    private final IRecipeRepository recipeRepository;
    private final Validator validator;

    public CrudRecipeService(IRecipeRepository recipeRepository, Validator validator) {
        this.recipeRepository = recipeRepository;
        this.validator = validator;
    }

    @Override
    public Optional<Recipe> getRecipe(long id) {
        return recipeRepository.findById(id).map(DtoToDomainMapping::mapToDomain);
    }

    /**
     * @throws ConstraintViolationException if the object is not valid
     */
    @Override
    public Optional<Recipe> createRecipe(Recipe recipe) {
        this.ensureValid(recipe);

        try {
            var recipeDto = DomainToDtoMapping.mapToDto(recipe);
            recipeDto = recipeRepository.save(recipeDto);
            return Optional.of(DtoToDomainMapping.mapToDomain(recipeDto));
        }
        catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteRecipe(long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * @param object object to validate
     * @throws ConstraintViolationException if the object is not valid
     */
    private <T> void ensureValid(T object) {
        var violations = validator.validate(object);
        if(violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
    }
}
