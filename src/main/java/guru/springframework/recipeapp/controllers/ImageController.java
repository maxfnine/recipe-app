package guru.springframework.recipeapp.controllers;

import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.services.ImageService;
import guru.springframework.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }


    @GetMapping("/recipe/{id}/image")
    public String showUploadImageform(@PathVariable Long id, Model model){
        RecipeCommand recipeCommand = recipeService.findCommandById(id);
        model.addAttribute("recipe",recipeCommand);
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{id}/image")
    public String processImageUpload(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(id,file);
        return "redirect:/recipe/"+id+"/show";
    }
}
