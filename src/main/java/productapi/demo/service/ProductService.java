package productapi.demo.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import productapi.demo.dto.ProductBasicDTO;
import productapi.demo.dto.ProductCreationRequestDTO;
import productapi.demo.model.Category;
import productapi.demo.model.Product;
import productapi.demo.repository.CategoryRepository;
import productapi.demo.repository.ProductRepository;

import java.lang.reflect.Field;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final Validator productDTOValidator;


  //---------------------------------- CONSTRUCTOR -----------------------------------

  @Autowired
  public ProductService(
          ProductRepository productRepository,
          CategoryRepository categoryRepository,
          Validator productDTOValidator
  ) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.productDTOValidator = productDTOValidator;
  }


  //---------------------------------- METHODS -----------------------------------

  //CREATE
  public Product addProduct(ProductCreationRequestDTO prodData) throws Exception {
    Errors errors = new BeanPropertyBindingResult(prodData, "prodata");
    productDTOValidator.validate(prodData, errors);

    if (errors.hasErrors()) {
      throw new ValidationException("Validation Error");
    }

    try {

      Category cat = categoryRepository.findById(prodData.getCategoryId())
              .orElseThrow(() -> new EntityNotFoundException("Category not found"));

      Product product = new Product();
      product.setName(prodData.getName());
      product.setPrice(prodData.getPrice());
      product.setQuantityInStock(prodData.getQuantityInStock());
      product.setCategory(cat);

      productRepository.save(product);
      return product;

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }


  //READ ALL
  public Iterable<Product> readAllProducts() {
    return productRepository.findAll();
  }


  //READ BY ID
  public Product readProductById(Long id) throws EntityNotFoundException {
    return productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));
  }


  //UPDATE
  public Product updateProduct(ProductBasicDTO productDTO, Long id) throws Exception {

    try {
      Product productToUpdate = productRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException("Product not found"));

      Field[] fields = ProductBasicDTO.class.getFields();

      for (Field field: fields) {
        field.setAccessible(true);
        Object newValue = field.get(productDTO);

        if (newValue != null) {
          field.set(productToUpdate, newValue);
        }
      }

      return productRepository.save(productToUpdate);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }


  //DELETE
  public void DeleteProduct(Long id) {
    productRepository.delete(
            productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"))
    );
  }
}