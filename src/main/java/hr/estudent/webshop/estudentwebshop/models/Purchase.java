package hr.estudent.webshop.estudentwebshop.models;

import hr.estudent.webshop.estudentwebshop.utils.PurchaseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PURCHASE")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String purchaseDate;
    private PurchaseEnum paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<PurchaseItem> purchaseItems;

    public Double getTotalAmount() {
        return purchaseItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getArticle().getPrice())
                .sum();
    }

}
