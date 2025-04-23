package app.yourdevbro.backend.product.repository;

import app.yourdevbro.backend.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByHeadlineContainingIgnoreCase(String headline);
    List<Product> findAllByOrderByPriceAsc();
}