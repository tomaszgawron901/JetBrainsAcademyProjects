package recipes.service;

import recipes.domain.model.Recipe;
import java.util.Optional;

public interface IRecipeService {
    Optional<Recipe> getRecipe(long id);
    Optional<Recipe> createRecipe(Recipe recipe);
    boolean deleteRecipe(long id);
    boolean updateRecipe(long id, Recipe mapToDomain);
}
