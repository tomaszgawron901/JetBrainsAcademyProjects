package recipes.mapping;

import recipes.contract.request.RecipeRequest;
import recipes.domain.model.Recipe;

import javax.validation.Valid;

import static recipes.mapping.CommonMapping.mapList;

public class ContractToDomainMapping {
    public static Recipe mapToDomain(RecipeRequest recipeRequest) {
        if (recipeRequest == null) {
            return null;
        }

        var recipe = new Recipe();
        recipe.setName(recipeRequest.getName());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setIngredients(recipeRequest.getIngredients());
        recipe.setDirections(recipeRequest.getDirections());

        return recipe;
    }
}
