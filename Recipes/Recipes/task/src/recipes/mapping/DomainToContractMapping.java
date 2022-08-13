package recipes.mapping;

import recipes.contract.response.RecipeResponse;
import recipes.domain.model.Recipe;

public class DomainToContractMapping {
    public static RecipeResponse mapToContract(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        return new RecipeResponse(
                recipe.getName(),
                recipe.getDescription(),
                recipe.getCategory(),
                recipe.getUpdatedAt(),
                recipe.getIngredients().toArray(new String[0]),
                recipe.getDirections().toArray(new String[0])
        );
    }
}
