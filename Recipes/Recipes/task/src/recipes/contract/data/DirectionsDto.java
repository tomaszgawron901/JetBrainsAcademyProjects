package recipes.contract.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "directions")
public class DirectionsDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeDto recipe;
}