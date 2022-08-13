package recipes.contract.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RecipeResponse {
    private String name;
    private String description;
    private String category;
    private LocalDateTime date;
    private String[] ingredients;
    private String[] directions;
}
