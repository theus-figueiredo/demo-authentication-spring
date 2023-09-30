package productapi.demo.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import productapi.demo.dto.ProductBasicDTO;
import productapi.demo.dto.ProductRequestDTO;
import productapi.demo.model.Product;
import productapi.demo.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  //---------------------------------- CONSTRUCTOR -----------------------------------

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  //---------------------------------- METHODS -----------------------------------


  //CREATE
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> store(@Valid @RequestBody ProductRequestDTO productRequestDTO, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) return ResponseEntity.badRequest().body("Invalid data sent");

    try {
      Product newProduct = productService.addProduct(productRequestDTO);
      return ResponseEntity.ok(newProduct);

    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();

    } catch (ValidationException e) {
      return ResponseEntity.badRequest().body("Invalid Data");

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }


  //READ ALL
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Iterable<Product>> getAllProducts() {
    Iterable<Product> products = productService.readAllProducts();
    return ResponseEntity.ok(products);
  }


  //READ BY ID
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Product> getById(@PathVariable Long id) {
    try {
      Product product = productService.readProductById(id);
      return ResponseEntity.ok(product);

    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }


  //UPDATE
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductBasicDTO productDTO) {
    try {
      Product updatedProduct = productService.updateProduct(productDTO, id);
      return  ResponseEntity.ok(updatedProduct);

    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();

    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }


  //DELETE
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    try {
      productService.DeleteProduct(id);
      return ResponseEntity.noContent().build();

    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
