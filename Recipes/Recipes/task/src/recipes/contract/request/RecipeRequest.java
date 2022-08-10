package recipes.contract.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {
    private String name;
    private String description;
    private String category;
    private List<String> ingredients;
    private List<String> directions;
}
