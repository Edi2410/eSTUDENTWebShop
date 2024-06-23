package hr.estudent.webshop.estudentwebshop.service;

import hr.estudent.webshop.estudentwebshop.models.Article;
import hr.estudent.webshop.estudentwebshop.models.CartItem;
import hr.estudent.webshop.estudentwebshop.models.User;
import hr.estudent.webshop.estudentwebshop.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private ArticleService articleService;

    public void updateCart(Long id, CartItem cartItem) {
        cartItem.setId(id);
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> findAllCartItems() {
        return cartItemRepository.findAll();
    }


    public void addArticleInCart(Long articleId, int quantity) {
        CartItem cartItem = getArticleInCart(articleId).orElse(null);

        if(cartItem == null) {
            cartItem = new CartItem();
            cartItem.setArticle(articleService.findArticleById(articleId));
            cartItem.setQuantity(quantity);

        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItemRepository.save(cartItem);
    }

    public void updateArticle(Long articleId, int quantity) {
        CartItem cartItem = getArticleInCart(articleId).orElse(null);

        assert cartItem != null;
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    private Optional<CartItem> getArticleInCart(Long articleId) {
        return findAllCartItems().stream()
                .filter(cart -> cart.getArticle().getId().equals(articleId))
                .findFirst();
    }

    public void clearCart() {
        cartItemRepository.deleteAll();
    }

    public Double calculateTotalPrice() {
        return findAllCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getArticle().getPrice() * cartItem.getQuantity())
                .sum();
    }
}
