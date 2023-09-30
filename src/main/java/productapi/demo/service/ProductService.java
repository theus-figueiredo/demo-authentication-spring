package productapi.demo.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import productapi.demo.dto.ProductBasicDTO;
import productapi.demo.dto.ProductRequestDTO;
import productapi.demo.model.Category;
import productapi.demo.model.Product;
import productapi.demo.repository.CategoryRepository;
import productapi.demo.repository.ProductRepository;

import java.lang.reflect.Field;
import java.util.Arrays;

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
  public Product addProduct(ProductRequestDTO prodData) throws Exception {
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

      Field[] fields = ProductBasicDTO.class.getDeclaredFields();

      for (Field field: fields) {
        field.setAccessible(true);
        Object newValue = field.get(productDTO);

        if (newValue != null) {

          if (field.getName().equals("categoryId")) {
            Category category = categoryRepository.findById((Long) newValue)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));

            productToUpdate.setCategory(category);
          } else {
            Field productField = Product.class.getDeclaredField(field.getName());
            productField.setAccessible(true);
            productField.set(productToUpdate, newValue);
          }
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
