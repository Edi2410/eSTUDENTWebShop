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

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public Article findArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public void updateArticle(Long id, Article article) {
        article.setId(id);
        articleRepository.save(article);
    }


}
