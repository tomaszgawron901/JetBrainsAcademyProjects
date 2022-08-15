package recipes.service.implementations;

import org.springframework.stereotype.Service;
import recipes.contract.data.RecipeDto;
import recipes.domain.exception.AuthorizationException;
import recipes.domain.model.Recipe;
import recipes.mapping.DomainToDtoMapping;
import recipes.mapping.DtoToDomainMapping;
import recipes.repository.IRecipeRepository;
import recipes.service.interfaces.IRecipeService;

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
public class CrudRecipeService extends BaseApplicationService implements IRecipeService {
    private final IRecipeRepository recipeRepository;
    public CrudRecipeService(IRecipeRepository recipeRepository, Validator validator) {
        super(validator);
        this.recipeRepository = recipeRepository;
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
        recipe.setUser(getCurrentUser());
        this.ensureValid(recipe);

        try {
            RecipeDto recipeDto = DomainToDtoMapping.mapToDto(recipe);
            recipeDto = recipeRepository.save(recipeDto);
            return Optional.of(DtoToDomainMapping.mapToDomain(recipeDto));
        }
        catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * @throws AuthorizationException if the user is not authorized to update the recipe
     */
    @Override
    public boolean deleteRecipe(long id) {
        var recipeDtoOptional = recipeRepository.findById(id);
        if(recipeDtoOptional.isEmpty()) {
            return false;
        }
        var recipeDto = recipeDtoOptional.get();
        if(!recipeDto.getUser().getEmail().equals(getCurrentUser().getEmail())) {
            throw new AuthorizationException("You are not authorized to delete this recipe");
        }

        recipeRepository.delete(recipeDto);

        return true;
    }

    /**
     * @throws ConstraintViolationException if the object is not valid
     * @throws AuthorizationException if the user is not authorized to update the recipe
     */
    @Override
    public boolean updateRecipe(long id, Recipe recipe) {
        recipe.setUpdatedAt(LocalDateTime.now());
        this.ensureValid(recipe);
        var recipeDtoOptional = recipeRepository.findById(id);
        if(recipeDtoOptional.isEmpty()) {
            return false;
        }
        var recipeDto = recipeDtoOptional.get();
        boolean currentUserIsOwner = recipeDto.getUser().getEmail().equals(getCurrentUser().getEmail());
        if(!currentUserIsOwner) {
            throw new AuthorizationException("You are not authorized to update this recipe");
        }

        var newRecipeDto = DomainToDtoMapping.mapToDto(recipe);
        newRecipeDto.setId(id);
        newRecipeDto.setUser(recipeDto.getUser());
        newRecipeDto.setUpdatedAt(LocalDateTime.now());
        recipeRepository.save(newRecipeDto);
        return true;
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
}
