package hr.estudent.webshop.estudentwebshop.repository;

import hr.estudent.webshop.estudentwebshop.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
