package recipes.service;

import org.springframework.stereotype.Service;
import recipes.contract.data.RecipeDto;
import recipes.domain.model.Recipe;
import recipes.mapping.DomainToDtoMapping;
import recipes.mapping.DtoToDomainMapping;
import recipes.repository.IRecipeRepository;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        recipe.setUpdatedAt(LocalDateTime.now());
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
     * @throws ConstraintViolationException if the object is not valid
     */
    @Override
    public boolean updateRecipe(long id, Recipe recipe) {
        recipe.setUpdatedAt(LocalDateTime.now());
        this.ensureValid(recipe);
        if (recipeRepository.existsById(id)) {
            var recipeDto = DomainToDtoMapping.mapToDto(recipe);
            recipeDto.setId(id);
            recipeDto.setUpdatedAt(LocalDateTime.now());
            recipeRepository.save(recipeDto);
            return true;
        }
        return false;
    }

    @Override
    public List<Recipe> searchByName(String name) {
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .filter(recipe -> recipe.getName().toLowerCase().contains(name.toLowerCase()))
                .sorted(Comparator.comparing(RecipeDto::getUpdatedAt).reversed())
                .map(DtoToDomainMapping::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> searchByCategory(String category) {
        if (category == null || category.isEmpty()) {
            return Collections.emptyList();
        }

        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .filter(recipe -> recipe.getCategory().equalsIgnoreCase(category))
                .sorted(Comparator.comparing(RecipeDto::getUpdatedAt).reversed())
                .map(DtoToDomainMapping::mapToDomain)
                .collect(Collectors.toList());
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
