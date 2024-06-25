package hr.estudent.webshop.estudentwebshop.controller.mvc;

import hr.estudent.webshop.estudentwebshop.models.Article;
import hr.estudent.webshop.estudentwebshop.publisher.CustomSpringEventPublisher;
import hr.estudent.webshop.estudentwebshop.service.ArticleService;
import hr.estudent.webshop.estudentwebshop.service.CategoriesService;
import hr.estudent.webshop.estudentwebshop.service.ColorsService;
import hr.estudent.webshop.estudentwebshop.service.MyUserDetailsService;
import hr.estudent.webshop.estudentwebshop.utils.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private ColorsService colorsService;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private CustomSpringEventPublisher publisher;


    @GetMapping
    public String viewArticles(Model model) {


        String[] roles = userDetailsService.findUsersRoles();
        boolean isAdmin = Arrays.stream(roles).anyMatch(role -> role.equals(RolesEnum.ADMIN.name()));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("articles", articleService.findAllArticles());
        publisher.publishCustomEvent("ArticleController :: List articles screen displayed!");
        return "home";
    }

    @GetMapping("/admin/list")
    public String viewArticlesAdmin(Model model) {
        String[] roles = userDetailsService.findUsersRoles();
        boolean isAdmin = Arrays.stream(roles).anyMatch(role -> role.equals(RolesEnum.ADMIN.name()));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("articles", articleService.findAllArticles());
        publisher.publishCustomEvent("ArticleController :: List articles screen displayed!");
        return "articles/list";
    }

    @GetMapping("/admin/add")
    public String showAddArticleForm(Model model) {
        model.addAttribute("colors", colorsService.findAllColor());
        model.addAttribute("categories", categoriesService.findAllCategories());
        model.addAttribute("article", new Article());
        publisher.publishCustomEvent("ArticleController :: Add article screen displayed!");
        return "articles/add";
    }

    @PostMapping("/admin/add")
    public String addArticle(Article article) {
        articleService.saveArticle(article);
        publisher.publishCustomEvent("ArticleController :: Article added!");
        return "redirect:/articles/admin/list";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditArticleForm(@PathVariable Long id, Model model) {
        model.addAttribute("colors", colorsService.findAllColor());
        model.addAttribute("categories", categoriesService.findAllCategories());
        model.addAttribute("article", articleService.findArticleById(id));
        publisher.publishCustomEvent("ArticleController :: Edit article screen displayed!");
        return "articles/edit";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateArticle(@PathVariable Long id, Article article) {
        articleService.updateArticle(id, article);
        publisher.publishCustomEvent("ArticleController :: Article edited!");
        return "redirect:/articles/admin/list";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        publisher.publishCustomEvent("ArticleController :: Delete article done!");
        return "redirect:/articles/admin/list";
    }


}
