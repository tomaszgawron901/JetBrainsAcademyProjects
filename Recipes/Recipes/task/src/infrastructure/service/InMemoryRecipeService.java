package infrastructure.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import recipes.common.data.Recipe;
import recipes.common.service.IRecipeService;

import java.util.HashMap;
import java.util.Optional;

@Service
public class InMemoryRecipeService implements IRecipeService {

    private int nextId = 1;
    private final HashMap<@NonNull Integer, Recipe> recipes = new HashMap<>();

    @Override
    public Optional<Recipe> getRecipe(int id) {
        var recipe = recipes.get(id);
        if (recipe == null) {
            return Optional.empty();
        }

        return Optional.of(recipe);
    }

    @Override
    public Optional<Recipe> createRecipe(@NonNull Recipe recipe) {
        var newRecipe = new Recipe(nextId++, recipe.getName(), recipe.getDescription(), recipe.getIngredients(), recipe.getDirections());
        recipes.put(newRecipe.getId(), newRecipe);
        return Optional.of(newRecipe);
    }
}
