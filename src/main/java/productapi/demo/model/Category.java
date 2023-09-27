package productapi.demo.model;


import jakarta.persistence.*;

@Entity
@Table(name = "product_category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  //------------------------------------ Constructors ----------------------------

  public Category() {}

  public Category(String name) {
    this.name = name;
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
}
