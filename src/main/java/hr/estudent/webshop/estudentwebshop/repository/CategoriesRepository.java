package hr.estudent.webshop.estudentwebshop.repository;


import hr.estudent.webshop.estudentwebshop.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
