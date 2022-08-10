package recipes.mapping;

import recipes.contract.data.DirectionsDto;
import recipes.contract.data.IngredientDto;
import recipes.contract.data.RecipeDto;
import recipes.domain.model.Recipe;

import java.util.stream.Collectors;

public class DomainToDtoMapping {

    public static RecipeDto mapToDto(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        var recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        recipeDto.setDescription(recipe.getDescription());
        recipeDto.setCategory(recipe.getCategory());
        recipeDto.setUpdatedAt(recipe.getUpdatedAt());

        var ingredientDtoList = recipe.getIngredients().stream()
                .map(ingredient -> {
                    var ingredientDto = new IngredientDto();
                    ingredientDto.setValue(ingredient);
                    ingredientDto.setRecipe(recipeDto);
                    return ingredientDto;
                })
                .collect(Collectors.toList());

        var directionsDtoList = recipe.getDirections().stream()
                .map(directions -> {
                    var directionsDto = new DirectionsDto();
                    directionsDto.setValue(directions);
                    directionsDto.setRecipe(recipeDto);
                    return directionsDto;
                })
                .collect(Collectors.toList());

        recipeDto.setIngredients(ingredientDtoList);
        recipeDto.setDirections(directionsDtoList);

        return recipeDto;
    }
}
