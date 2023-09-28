package productapi.demo.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import productapi.demo.model.Category;
import productapi.demo.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Optional<Category> createCategory(String name) {
    if (name.isEmpty()) return Optional.empty();

    Category cat = new Category(name);
    categoryRepository.save(cat);
    return Optional.of(cat);
  }


  public Iterable<Category> getAll() {
    return categoryRepository.findAll();
  }


  public Category findById(Long id) {
    return categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

  }

  public Category update(Long id, String name) {
    Category cat = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

    if (name.isEmpty()) throw new IllegalArgumentException("Algum dado deve ser fornecido");

    cat.setName(name);
    return categoryRepository.save(cat);
  }


  public void delete(Long id) {
    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

    categoryRepository.deleteById(id);
  }
}
