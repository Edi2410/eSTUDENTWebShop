package hr.estudent.webshop.estudentwebshop.controller.rest;

import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import hr.estudent.webshop.estudentwebshop.models.CartItem;
import hr.estudent.webshop.estudentwebshop.models.Purchase;
import hr.estudent.webshop.estudentwebshop.models.PurchaseItem;
import hr.estudent.webshop.estudentwebshop.service.CartItemService;
import hr.estudent.webshop.estudentwebshop.service.MyUserDetailsService;
import hr.estudent.webshop.estudentwebshop.service.PurchaseService;
import hr.estudent.webshop.estudentwebshop.utils.DateUtils;
import hr.estudent.webshop.estudentwebshop.utils.PurchaseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/paypal")
public class PayPallController {

    @Autowired
    private PayPalHttpClient payPalClient;
    @Autowired
    private CartItemService cartItemService;

    @Value("${paypal.returnUrl}")
    private String returnUrl;

    @Value("${paypal.cancelUrl}")
    private String cancelUrl;

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/buy")
    public ResponseEntity<String> createOrder(Model model) {
        List<CartItem> cartItems = cartItemService.findAllCartItems();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        OrdersCreateRequest request = new OrdersCreateRequest();
        request.requestBody(buildRequestBody());

        try {
            Order order = payPalClient.execute(request).result();

            URI approvalUrl = order.links().stream()
                    .filter(link -> "approve".equals(link.rel()))
                    .findFirst()
                    .map(link -> URI.create(link.href()))
                    .orElseThrow(() -> new RuntimeException("No approve link found in response"));



            Purchase purchase = new Purchase();
            purchase.setPurchaseDate(DateUtils.format(LocalDateTime.now()));
            purchase.setPaymentMethod(PurchaseEnum.PAYPAL);
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

            return ResponseEntity.status(HttpStatus.FOUND).location(approvalUrl).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order: " + e.getMessage());
        }
    }

    private OrderRequest buildRequestBody() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext().brandName("YourBrandName")
                .landingPage("BILLING").cancelUrl(cancelUrl).returnUrl(returnUrl);



        AmountWithBreakdown amount = new AmountWithBreakdown().currencyCode("USD")
                .value(cartItemService.calculateTotalPrice().toString());

        orderRequest.applicationContext(applicationContext);
        orderRequest.purchaseUnits(Collections.singletonList(new PurchaseUnitRequest().amountWithBreakdown(amount)));

        return orderRequest;
    }

}