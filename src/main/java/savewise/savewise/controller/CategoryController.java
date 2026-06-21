package savewise.savewise.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import savewise.savewise.entity.Category;
import savewise.savewise.entity.User;
import savewise.savewise.repository.CategoryRepository;
import savewise.savewise.repository.UserRepository;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryController(
            CategoryRepository categoryRepository,
            UserRepository userRepository) {

        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listCategories(
            Model model,
            Principal principal) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        model.addAttribute(
                "categories",
                categoryRepository.findByUser(user));

        return "categories/list";
    }

    @GetMapping("/new")
    public String newCategory(Model model) {

        model.addAttribute(
                "category",
                new Category());

        return "categories/form";
    }

    @PostMapping("/save")
    public String saveCategory(
            @ModelAttribute Category category,
            Principal principal) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        category.setUser(user);

        categoryRepository.save(category);

        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(
            @PathVariable Long id) {

        categoryRepository.deleteById(id);

        return "redirect:/categories";
    }
}