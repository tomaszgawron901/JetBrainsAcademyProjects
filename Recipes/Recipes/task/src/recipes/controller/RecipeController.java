package recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.mapping.CommonMapping;
import recipes.mapping.ContractToDomainMapping;
import recipes.mapping.DomainToContractMapping;
import recipes.service.interfaces.IRecipeService;
import recipes.contract.request.RecipeRequest;
import recipes.contract.response.IdResponse;
import recipes.contract.response.RecipeResponse;

import java.util.Collection;

@RestController
@RequestMapping("api/recipe")
public class RecipeController extends BaseController {
    private final IRecipeService recipeService;

    public RecipeController(IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public ResponseEntity<IdResponse> createRecipe(@RequestBody RecipeRequest requestBody) {
        var recipe = recipeService.createRecipe(ContractToDomainMapping.mapToDomain(requestBody));
        if (recipe.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new IdResponse(recipe.get().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdResponse> updateRecipe(@PathVariable long id, @RequestBody RecipeRequest requestBody) {
        boolean updated = recipeService.updateRecipe(id, ContractToDomainMapping.mapToDomain(requestBody));
        if (updated) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
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

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<Collection<RecipeResponse>> searchByName(
            @RequestParam(name = "name", required = false)String name,
            @RequestParam(name = "category", required = false) String category
    ) {
        if (name != null && category == null) {
            return ResponseEntity.ok(CommonMapping.mapList(recipeService.searchByName(name), DomainToContractMapping::mapToContract));
        }
        else if (category != null && name == null) {
            return ResponseEntity.ok(CommonMapping.mapList(recipeService.searchByCategory(category), DomainToContractMapping::mapToContract));
        }

        return ResponseEntity.badRequest().build();
    }
}
