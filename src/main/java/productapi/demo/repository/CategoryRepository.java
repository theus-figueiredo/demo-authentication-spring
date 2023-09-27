package productapi.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import productapi.demo.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
