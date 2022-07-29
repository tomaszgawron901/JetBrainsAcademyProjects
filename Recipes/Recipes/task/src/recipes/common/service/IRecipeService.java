package recipes.common.service;

import recipes.common.data.Recipe;

import java.util.Optional;

public interface IRecipeService {
    Optional<Recipe> getRecipe(int id);
    Optional<Recipe> createRecipe(Recipe recipe);
}
