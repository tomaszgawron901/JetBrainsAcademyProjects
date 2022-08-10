package recipes.mapping;

import recipes.contract.request.RecipeRequest;
import recipes.domain.model.Recipe;

public class ContractToDomainMapping {
    public static Recipe mapToDomain(RecipeRequest recipeRequest) {
        if (recipeRequest == null) {
            return null;
        }

        var recipe = new Recipe();
        recipe.setName(recipeRequest.getName());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setCategory(recipeRequest.getCategory());
        recipe.setIngredients(recipeRequest.getIngredients());
        recipe.setDirections(recipeRequest.getDirections());

        return recipe;
    }
}
