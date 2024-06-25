package hr.estudent.webshop.estudentwebshop.controller.mvc;


import hr.estudent.webshop.estudentwebshop.exceptions.ColorInUseException;
import hr.estudent.webshop.estudentwebshop.models.Colors;
import hr.estudent.webshop.estudentwebshop.publisher.CustomSpringEventPublisher;
import hr.estudent.webshop.estudentwebshop.service.ColorsService;
import hr.estudent.webshop.estudentwebshop.service.MyUserDetailsService;
import hr.estudent.webshop.estudentwebshop.utils.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/colors")
public class ColorsController {

    @Autowired
    private CustomSpringEventPublisher publisher;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private ColorsService colorsService;

    @RequestMapping
    public String viewColors(Model model) {

        String[] roles = userDetailsService.findUsersRoles();
        boolean isAdmin = Arrays.stream(roles).anyMatch(role -> role.equals(RolesEnum.ADMIN.name()));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("colors", colorsService.findAllColor());
        publisher.publishCustomEvent("ColorsController :: List colors screen displayed!");
        return "colors/list";
    }


    @GetMapping("/admin/add")
    public String showAddColorForm(Model model) {
        model.addAttribute("color", new Colors());
        publisher.publishCustomEvent("ColorsController :: Add color screen displayed!");
        return "colors/add";
    }

    @PostMapping("/admin/add")
    public String addColor(@ModelAttribute() Colors color) {
        colorsService.saveColor(color);
        publisher.publishCustomEvent("ColorsController :: Color added!");
        return "redirect:/colors";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditColorForm(@PathVariable() Long id, Model model) {
        model.addAttribute("color", colorsService.findColorById(id));
        publisher.publishCustomEvent("ColorsController :: Edit color screen displayed!");
        return "colors/edit";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateColor(@PathVariable() Long id, @ModelAttribute() Colors color) {
        colorsService.updateColor(id, color);
        publisher.publishCustomEvent("ColorsController :: Color edited!");
        return "redirect:/colors";
    }

    @PostMapping("admin/delete/{id}")
    public String deleteColor(@PathVariable Long id, Model model) {
        try {
            colorsService.deleteColor(id);
            publisher.publishCustomEvent("ColorsController :: Delete Color done!");
            return "redirect:/colors";
        } catch (ColorInUseException e) {
            model.addAttribute("error", e.getMessage());
            publisher.publishCustomEvent("ColorsController :: Delete Color failed!");
            return "error";
        }
    }
}
