package hr.estudent.webshop.estudentwebshop.service;

import hr.estudent.webshop.estudentwebshop.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hr.estudent.webshop.estudentwebshop.repository.ArticleRepository;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findByCategoryId(Long categoryId) {
        return articleRepository.findByCategoryId(categoryId);
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public Article findArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }
    public Article restSaveArticle(Article article) {
        return articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public void updateArticle(Long id, Article article) {
        article.setId(id);
        articleRepository.save(article);
    }

    public List<Article> searchArticles(String query) {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .filter(article -> article.getName().toLowerCase().contains(query.toLowerCase()) ||
                        article.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                        article.getCategory().getCategory().toLowerCase().contains(query.toLowerCase()) ||
                        article.getColor().getColor().toLowerCase().contains(query.toLowerCase()) ||
                        String.valueOf(article.getPrice()).equalsIgnoreCase(query)
                )
                .toList();
    }


}
