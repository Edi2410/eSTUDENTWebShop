package hr.estudent.webshop.estudentwebshop.repository;

import hr.estudent.webshop.estudentwebshop.models.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
