package productapi.demo.controller;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productapi.demo.dto.CategoryRequestDTO;
import productapi.demo.model.Category;
import productapi.demo.service.CategoryService;


@RestController
@RequestMapping("api/categories")
public class CategoryController {

  private final CategoryService categoryService;

  //---------------------------------- CONSTRUCTOR -----------------------------------

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  //---------------------------------- METHODS -----------------------------------

  //CREATE
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Category> createCategorie(@RequestBody CategoryRequestDTO requestDTO) {
    Category newCat = this.categoryService.createCategory(requestDTO);

    return ResponseEntity.ok(newCat);
  }


  //READ ALL
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Iterable<Category>> getAllCategories() {
    Iterable<Category> allCats = this.categoryService.getAll();
    return ResponseEntity.ok(allCats);
  }


  //READ BY ID
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Category> getById(@PathVariable Long id) {
    try {
      Category category = categoryService.getById(id);
      return ResponseEntity.ok(category);

    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }


  //UPDATE
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO) {
    try {
      Category updatedCategory = categoryService.update(id, categoryRequestDTO);
      return ResponseEntity.ok(updatedCategory);

    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();

    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }


  //DELETE
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    try {
      categoryService.delete(id);
      return ResponseEntity.noContent().build();

    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
