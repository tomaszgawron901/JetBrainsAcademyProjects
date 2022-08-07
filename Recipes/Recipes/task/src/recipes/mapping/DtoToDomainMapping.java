package recipes.mapping;

import recipes.contract.data.DirectionsDto;
import recipes.contract.data.IngredientDto;
import recipes.contract.data.RecipeDto;
import recipes.domain.model.Recipe;

import javax.validation.Valid;

import static recipes.mapping.CommonMapping.mapList;

public class DtoToDomainMapping {
    public static Recipe mapToDomain(RecipeDto recipeDto) {
        if (recipeDto == null) {
            return null;
        }

        var recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setIngredients(mapList(recipeDto.getIngredients(), IngredientDto::getValue));
        recipe.setDirections(mapList(recipeDto.getDirections(), DirectionsDto::getValue));

        return recipe;
    }
}
