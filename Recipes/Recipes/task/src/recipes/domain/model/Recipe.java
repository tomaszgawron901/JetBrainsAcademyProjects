package recipes.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    @NotBlank
    private String category;

    @NotNull
    private LocalDateTime updatedAt;

    private UserDetails user;

    @NotEmpty
    private List<@NotBlank String> ingredients;

    @NotEmpty
    private  List<@NotBlank String> directions;
}
