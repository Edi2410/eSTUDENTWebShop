package hr.estudent.webshop.estudentwebshop.service;

import hr.estudent.webshop.estudentwebshop.models.Categories;
import hr.estudent.webshop.estudentwebshop.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Categories> findAllCategories() {
        return categoriesRepository.findAll();
    }

    public Categories findCategoryById(Long id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    public void saveCategory(Categories category) {
        categoriesRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoriesRepository.deleteById(id);
    }

    public void updateCategory(Long id, Categories category) {
        category.setId(id);
        categoriesRepository.save(category);
    }




}
