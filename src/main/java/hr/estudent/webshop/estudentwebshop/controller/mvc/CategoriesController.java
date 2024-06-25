package hr.estudent.webshop.estudentwebshop.controller.mvc;

import hr.estudent.webshop.estudentwebshop.exceptions.CategoryInUseException;
import hr.estudent.webshop.estudentwebshop.models.Categories;
import hr.estudent.webshop.estudentwebshop.service.CategoriesService;
import hr.estudent.webshop.estudentwebshop.service.MyUserDetailsService;
import hr.estudent.webshop.estudentwebshop.utils.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import hr.estudent.webshop.estudentwebshop.publisher.CustomSpringEventPublisher;

import java.util.Arrays;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CustomSpringEventPublisher publisher;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @RequestMapping
    public String viewCategories(Model model) {

        String[] roles = userDetailsService.findUsersRoles();
        boolean isAdmin = Arrays.stream(roles).anyMatch(role -> role.equals(RolesEnum.ADMIN.name()));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("categories", categoriesService.findAllCategories());
        publisher.publishCustomEvent("CategoryController :: List categories screen displayed!");
        return "category/list";
    }

    @GetMapping("/admin/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Categories());
        publisher.publishCustomEvent("CategoryController :: Add category screen displayed!");
        return "category/add";
    }

    @PostMapping("/admin/add")
    public String addCategory(@ModelAttribute() Categories category) {
        categoriesService.saveCategory(category);
        publisher.publishCustomEvent("CategoryController :: Category added!");
        return "redirect:/categories";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditCategoryForm(@PathVariable() Long id, Model model) {
        model.addAttribute("category", categoriesService.findCategoryById(id));
        publisher.publishCustomEvent("CategoryController :: Edit category screen displayed!");
        return "category/edit";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateCategory(@PathVariable() Long id, @ModelAttribute() Categories category) {
        categoriesService.updateCategory(id, category);
        publisher.publishCustomEvent("CategoryController :: Category edited!");
        return "redirect:/categories";
    }

    @PostMapping("admin/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        try {
            categoriesService.deleteCategory(id);
            publisher.publishCustomEvent("CategoryController :: Delete category done!");
            return "redirect:/categories";
        } catch (CategoryInUseException e) {
            model.addAttribute("error", e.getMessage());
            publisher.publishCustomEvent("CategoryController :: Delete category failed!");
            return "error";
        }
    }
}
