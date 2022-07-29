package recipes.tool;

import recipes.common.data.Recipe;
import recipes.contract.request.RecipeRequest;
import recipes.contract.response.RecipeResponse;

public class DataContractMapper {
    public static RecipeResponse map(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        return new RecipeResponse(recipe.getName(), recipe.getDescription(), recipe.getIngredients(), recipe.getDirections());
    }

    public static Recipe map(RecipeRequest recipe) {
        if (recipe == null) {
            return null;
        }
        return new Recipe(0, recipe.getName(), recipe.getDescription(), recipe.getIngredients(), recipe.getDirections());
    }
}
