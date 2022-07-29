package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import recipes.common.service.IRecipeService;
import recipes.contract.request.RecipeRequest;
import recipes.contract.response.IdResponse;
import recipes.contract.response.RecipeResponse;
import recipes.tool.DataContractMapper;

@RestController
@RequestMapping("api/recipe")
public class RecipeController {

    private final IRecipeService recipeService;

    public RecipeController(@Autowired IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public ResponseEntity<IdResponse> createRecipe(@RequestBody RecipeRequest requestBody) {
        var recipe = recipeService.createRecipe(DataContractMapper.map(requestBody));
        if (recipe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new IdResponse(recipe.get().getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable("id") int id) {
        var recipe = recipeService.getRecipe(id);
        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(DataContractMapper.map(recipe.get()));
    }
}
