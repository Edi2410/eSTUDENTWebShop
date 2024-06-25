package hr.estudent.webshop.estudentwebshop.controller.mvc;

import hr.estudent.webshop.estudentwebshop.models.CartItem;
import hr.estudent.webshop.estudentwebshop.models.Purchase;
import hr.estudent.webshop.estudentwebshop.models.PurchaseItem;
import hr.estudent.webshop.estudentwebshop.publisher.CustomSpringEventPublisher;
import hr.estudent.webshop.estudentwebshop.service.CartItemService;
import hr.estudent.webshop.estudentwebshop.service.MyUserDetailsService;
import hr.estudent.webshop.estudentwebshop.service.PurchaseService;
import hr.estudent.webshop.estudentwebshop.utils.DateUtils;
import hr.estudent.webshop.estudentwebshop.utils.PurchaseEnum;
import hr.estudent.webshop.estudentwebshop.utils.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private CustomSpringEventPublisher publisher;
    @Autowired
    private PurchaseService purchaseService;


    @GetMapping
    public String viewCartItems(Model model) {
        String[] roles = userDetailsService.findUsersRoles();
        boolean isAdmin = Arrays.stream(roles).anyMatch(role -> role.equals(RolesEnum.ADMIN.name()));
        boolean isUser = Arrays.stream(roles).anyMatch(role -> role.equals(RolesEnum.USER.name()));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isUser", isUser);
        model.addAttribute("totalValue", cartItemService.calculateTotalPrice());
        model.addAttribute("cartItems", cartItemService.findAllCartItems());
        publisher.publishCustomEvent("CartController :: List cart items screen displayed!");
        return "cart/list";
    }

    @PostMapping("/add/{id}")
    public String addItemToCart(@PathVariable Long id, @RequestParam int quantity) {
        cartItemService.addArticleInCart(id, quantity);
        publisher.publishCustomEvent("CartController :: Add item to cart!");
        return "redirect:/articles";
    }

    @PostMapping("/edit/{id}")
    public String editCartItem(@PathVariable Long id, @RequestParam int quantity) {
        cartItemService.updateArticle(id, quantity);
        publisher.publishCustomEvent("CartController :: Edit item in cart!");
        return "redirect:/cart";
    }

    @PostMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        publisher.publishCustomEvent("CartController :: Delete item from cart!");
        return "redirect:/cart";
    }

    @PostMapping("/delete/all")
    public String deleteCartItems() {
        cartItemService.clearCart();
        publisher.publishCustomEvent("CartController :: Delete all items from cart!");
        return "redirect:/cart";
    }

    @PostMapping("/buy")
    public String buyCart(Model model) {
        List<CartItem> cartItems = cartItemService.findAllCartItems();

        if (cartItems.isEmpty()) {
            model.addAttribute("error",
                    "Nemaš ništa u košarici!");
            return "error";
        }

        Purchase purchase = new Purchase();
        purchase.setPurchaseDate(DateUtils.format(LocalDateTime.now()));
        purchase.setPaymentMethod(PurchaseEnum.GOTOVINA);
        purchase.setUser(userDetailsService.getCurrentUser());
        purchase.setPurchaseItems(cartItems.stream()
                .map(item -> new PurchaseItem(
                        null,
                        item.getArticle(),
                        item.getQuantity(),
                        purchase))
                .toList());

        purchaseService.savePurchase(purchase);
        cartItemService.clearCart();

        return "redirect:/cart";
    }


}
