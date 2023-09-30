package productapi.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductRequestDTO {

  @NotBlank(message = "Name must not be blank")
  private String name;

  @PositiveOrZero(message = "Price has to be at least 0 or higher")
  private double price;

  @PositiveOrZero(message = "Quantity in stock must be at least 0 or higher")
  private int quantityInStock;

  @Positive(message = "Category id must be at least 1")
  private Long categoryId;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantityInStock() {
    return quantityInStock;
  }

  public void setQuantityInStock(int quantityInStock) {
    this.quantityInStock = quantityInStock;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
}
