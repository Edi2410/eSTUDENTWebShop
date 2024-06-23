package hr.estudent.webshop.estudentwebshop.repository;

import hr.estudent.webshop.estudentwebshop.models.Purchase;
import hr.estudent.webshop.estudentwebshop.utils.PurchaseEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserId(Long userId);
    List<Purchase> findByPurchaseDateBetween(String startDate, String endDate);
    List<Purchase> findByPaymentMethod(PurchaseEnum paymentMethod);
    List<Purchase> findByUserIdAndPurchaseDateBetweenAndPaymentMethod(Long user_id, String purchaseDate, String purchaseDate2, PurchaseEnum paymentMethod);
}
