package guru.springframework.recipeapp.controllers;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}/show")
    public String getRecipe(Model model, @PathVariable("id") Long id){
        model.addAttribute("recipe",recipeService.findById(id));
        return "recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping("/")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/"+savedRecipeCommand.getId()+"/show";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id,Model model){
        model.addAttribute("recipe",recipeService.findCommandById(id));
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id){
        recipeService.deletById(id);
        return "redirect:/";
    }

}
