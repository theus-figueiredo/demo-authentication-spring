package productapi.demo.dto;


import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDTO {

  @NotBlank
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
