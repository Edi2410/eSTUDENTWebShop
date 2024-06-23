package hr.estudent.webshop.estudentwebshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ARTICLE")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private int available;
    private String image_link;
    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Colors color;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;


}
