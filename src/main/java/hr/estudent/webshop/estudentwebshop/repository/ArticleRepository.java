package hr.estudent.webshop.estudentwebshop.repository;


import hr.estudent.webshop.estudentwebshop.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByCategoryId(Long categoryId);
}
