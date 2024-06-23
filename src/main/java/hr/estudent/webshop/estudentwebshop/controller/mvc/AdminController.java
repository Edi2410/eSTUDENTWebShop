package hr.estudent.webshop.estudentwebshop.controller.mvc;

import hr.estudent.webshop.estudentwebshop.service.MyUserDetailsService;
import hr.estudent.webshop.estudentwebshop.service.UserLogService;
import hr.estudent.webshop.estudentwebshop.utils.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserLogService userLogService;

    @GetMapping
    public String viewAdmin(Model model) {
        model.addAttribute("userLogs", userLogService.findAllUserLogs());
        return "admin/userLogsList";
    }
}
