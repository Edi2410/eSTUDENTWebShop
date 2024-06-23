package hr.estudent.webshop.estudentwebshop.controller.mvc;

import hr.estudent.webshop.estudentwebshop.publisher.CustomSpringEventPublisher;
import hr.estudent.webshop.estudentwebshop.service.MyUserDetailsService;
import hr.estudent.webshop.estudentwebshop.service.PurchaseItemService;
import hr.estudent.webshop.estudentwebshop.service.PurchaseService;
import hr.estudent.webshop.estudentwebshop.utils.PurchaseEnum;
import hr.estudent.webshop.estudentwebshop.utils.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private CustomSpringEventPublisher publisher;

    @GetMapping("/admin")
    public String viewPurchaseAdmin(Model model) {
        model.addAttribute("isAdmin", true);
        model.addAttribute("purchases", purchaseService.getAllPurchases());
        model.addAttribute("users", userDetailsService.getAllUsers());
        publisher.publishCustomEvent("PurchaseController :: List purchases screen displayed!");
        return "purchase/list";
    }

    @PostMapping("/admin")
    public String viewPurchaseAdminSearch(
                                         @RequestParam(required = false) Long userId,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                         @RequestParam(required = false) String paymentMethod,
                                         Model model) {

        model.addAttribute("userId", userId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("paymentMethod", paymentMethod);

        LocalDate start = startDate != null ? startDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate end = endDate != null ? endDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : null;



        model.addAttribute("purchases", purchaseService.filterPurchases(userId, start, end, paymentMethod));
        model.addAttribute("users", userDetailsService.getAllUsers());
        model.addAttribute("isAdmin", true);

        publisher.publishCustomEvent("PurchaseController :: Filter purchases done!");
        return "purchase/list";
    }


    @GetMapping("/user")
    public String viewPurchaseUser(Model model) {
        model.addAttribute("isAdmin", false);
        model.addAttribute("purchases", purchaseService.getAllPurchases());
        model.addAttribute("users", userDetailsService.getAllUsers());
        publisher.publishCustomEvent("PurchaseController :: List purchases screen displayed!");
        return "purchase/list";
    }


    @PostMapping("/user")
    public String viewPurchaseUserSearch(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                         @RequestParam(required = false) String paymentMethod,
                                         Model model) {

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("paymentMethod", paymentMethod);

        LocalDate start =startDate != null ? startDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate end = endDate != null ? endDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : null;

        model.addAttribute("purchases", purchaseService.filterPurchases(null, start, end, paymentMethod));


        model.addAttribute("users", userDetailsService.getAllUsers());
        model.addAttribute("isAdmin", false);

        publisher.publishCustomEvent("PurchaseController :: Filter purchases done!");
        return "purchase/list";
    }

}
