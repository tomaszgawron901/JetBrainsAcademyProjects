package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.mapping.ContractToDomainMapping;
import recipes.mapping.DomainToContractMapping;
import recipes.service.IRecipeService;
import recipes.contract.request.RecipeRequest;
import recipes.contract.response.IdResponse;
import recipes.contract.response.RecipeResponse;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("api/recipe")
public class RecipeController {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleBadRequestException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private final IRecipeService recipeService;

    public RecipeController(@Autowired IRecipeService recipeService) {
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
}
