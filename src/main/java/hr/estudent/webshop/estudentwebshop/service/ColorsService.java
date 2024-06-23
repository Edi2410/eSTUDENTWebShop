package hr.estudent.webshop.estudentwebshop.service;

import hr.estudent.webshop.estudentwebshop.models.Categories;
import hr.estudent.webshop.estudentwebshop.models.Colors;
import hr.estudent.webshop.estudentwebshop.repository.CategoriesRepository;
import hr.estudent.webshop.estudentwebshop.repository.ColorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorsService {
    @Autowired
    private ColorsRepository colorsRepository;

    public List<Colors> findAllColor() {
        return colorsRepository.findAll();
    }

    public Colors findColorById(Long id) {
        return colorsRepository.findById(id).orElse(null);
    }

    public void saveColor(Colors category) {
        colorsRepository.save(category);
    }

    public void deleteColor(Long id) {
        colorsRepository.deleteById(id);
    }

    public void updateColor(Long id, Colors color) {
        color.setId(id);
        colorsRepository.save(color);
    }
}
