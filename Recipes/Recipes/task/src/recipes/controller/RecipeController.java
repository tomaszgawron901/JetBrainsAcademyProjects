package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.mapping.ContractToDomainMapping;
import recipes.mapping.DomainToContractMapping;
import recipes.service.IRecipeService;
import recipes.contract.request.RecipeRequest;
import recipes.contract.response.IdResponse;
import recipes.contract.response.RecipeResponse;
@RestController
@RequestMapping("api/recipe")
public class RecipeController {

    private final IRecipeService recipeService;

    public RecipeController(@Autowired IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public ResponseEntity<IdResponse> createRecipe(@RequestBody RecipeRequest requestBody) {
        try {
            var recipe = recipeService.createRecipe(ContractToDomainMapping.mapToDomain(requestBody));
            if (recipe.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(new IdResponse(recipe.get().getId()));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable("id") int id) {
        var recipe = recipeService.getRecipe(id);
        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(DomainToContractMapping.mapToContract(recipe.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") int id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
