package productapi.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private double price;
  private int quantityInStock;

  //------------------------------------ Constructors ----------------------------

  public Product() {}


  public Product(String name, double price, int quantityInStock) {
    this.name = name;
    this.price = price;
    this.quantityInStock = quantityInStock;
  }

  //---------------------------------- Getters & Setters -----------------------------

  public Long getId() {
    return id;
  }

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
}
