package recipes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class Recipe {
    @Min(0)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotEmpty
    private List<@NotBlank String> ingredients;

    @NotEmpty
    private  List<@NotBlank String> directions;
}
