package productapi.demo.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import productapi.demo.dto.CategoryRequestDTO;
import productapi.demo.model.Category;
import productapi.demo.service.CategoryService;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  private final CategoryService categoryService;

  //---------------------------------- CONSTRUCTOR -------------------------------

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  //---------------------------------- METHODS -----------------------------------

  //CREATE
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> createCategorie(@Valid @RequestBody CategoryRequestDTO requestDTO, @NotNull BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("Name must not be blank");
    }

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
  public ResponseEntity<?> updateCategory(
          @PathVariable Long id,
          @Valid @RequestBody CategoryRequestDTO categoryRequestDTO,
          @NotNull BindingResult bindingResult
  ) {

    if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body("Name must not be empty");

    try {
      Category updatedCategory = categoryService.update(id, categoryRequestDTO);
      return ResponseEntity.ok(updatedCategory);

    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();

    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }


  //DELETE
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
    try {
      categoryService.delete(id);
      return ResponseEntity.noContent().build();

    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
