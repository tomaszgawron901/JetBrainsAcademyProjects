package recipes;

import infrastructure.service.InMemoryRecipeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import recipes.common.service.IRecipeService;

@SpringBootApplication
public class RecipesApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipesApplication.class, args);
    }

    @Bean
    public IRecipeService recipeService() {
        return new InMemoryRecipeService();
    }
}
