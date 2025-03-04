package pods.project.marketplaceservice.repositories;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pods.project.marketplaceservice.entities.Product;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    List<Product> findByUserId(@Param("id") Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    List<Product> findProductByIdIs(@Param("id") Integer id);

    @Transactional
    @Modifying
    @QueryHints(@QueryHint(name = "javax.persistence.lock.timeout", value = "5000"))
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("UPDATE Product p SET p.stock_quantity=:quantity WHERE p.id=:id")
    Integer updateQuantity(@Param("id") Integer id, @Param("quantity") Integer quantity);
}
