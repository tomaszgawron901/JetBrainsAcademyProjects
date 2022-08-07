package recipes.contract.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {
    private @NotBlank String name;
    private @NotBlank String description;
    private @NotEmpty List<@NotBlank String> ingredients;
    private @NotEmpty List<@NotBlank String> directions;
}
