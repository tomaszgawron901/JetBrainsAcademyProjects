package recipes.common.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Recipe {
    private int id;
    private String name;
    private String description;
    private String[] ingredients;
    private String[] directions;
}
