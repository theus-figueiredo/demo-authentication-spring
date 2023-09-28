package productapi.demo.service;

import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productapi.demo.dto.CategoryRequestDTO;
import productapi.demo.model.Category;
import productapi.demo.repository.CategoryRepository;


@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category createCategory(CategoryRequestDTO requestDTO) {
    Category cat = new Category(requestDTO.getName());
    categoryRepository.save(cat);
    return cat;
  }


  public Iterable<Category> getAll() {
    return categoryRepository.findAll();
  }


  public Category getById(Long id) {
    return categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

  }

  public Category update(Long id, @NotNull CategoryRequestDTO categoryRequestDTO) {
    Category cat = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

    if (categoryRequestDTO.getName().isEmpty()) {
      throw new IllegalArgumentException("Algum dado deve ser fornecido");
    }

    cat.setName(categoryRequestDTO.getName());
    return categoryRepository.save(cat);
  }


  public void delete(Long id) {
    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

    categoryRepository.deleteById(id);
  }
}
