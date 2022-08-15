package recipes.mapping;

import org.springframework.security.core.userdetails.UserDetails;
import recipes.contract.data.DirectionsDto;
import recipes.contract.data.IngredientDto;
import recipes.contract.data.RecipeDto;
import recipes.contract.data.UserDto;
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
        recipeDto.setUser(mapToDto(recipe.getUser()));

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

    public static UserDto mapToDto(UserDetails user) {
        if (user == null) {
            return null;
        }

        var userDto = new UserDto();
        userDto.setEmail(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
