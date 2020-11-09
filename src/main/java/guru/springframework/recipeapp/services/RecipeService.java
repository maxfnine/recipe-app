package guru.springframework.recipeapp.services;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(long l);
    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deletById(Long idToDelete);
}
