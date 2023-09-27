package productapi.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import productapi.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
