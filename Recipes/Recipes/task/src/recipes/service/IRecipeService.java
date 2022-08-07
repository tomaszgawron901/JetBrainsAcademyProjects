package recipes.service;

import org.springframework.stereotype.Service;
import recipes.domain.model.Recipe;
import java.util.Optional;

@Service
public interface IRecipeService {
    Optional<Recipe> getRecipe(long id);
    Optional<Recipe> createRecipe(Recipe recipe);

    boolean deleteRecipe(long id);
}
