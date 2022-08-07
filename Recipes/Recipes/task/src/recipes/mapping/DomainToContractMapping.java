package recipes.mapping;

import recipes.contract.response.RecipeResponse;
import recipes.domain.model.Recipe;

import static recipes.mapping.CommonMapping.mapList;

public class DomainToContractMapping {
    public static RecipeResponse mapToContract(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        return new RecipeResponse(
                recipe.getName(),
                recipe.getDescription(),
                recipe.getIngredients().toArray(new String[0]),
                recipe.getDirections().toArray(new String[0])
        );
    }
}
