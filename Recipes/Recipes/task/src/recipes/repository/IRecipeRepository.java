package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.contract.data.RecipeDto;

@Repository
public interface IRecipeRepository extends CrudRepository<RecipeDto, Long> {
}
