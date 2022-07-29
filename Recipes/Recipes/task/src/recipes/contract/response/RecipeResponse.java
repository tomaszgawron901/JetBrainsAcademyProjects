package recipes.contract.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeResponse {
    private String name;
    private String description;
    private String[] ingredients;
    private String[] directions;
}
