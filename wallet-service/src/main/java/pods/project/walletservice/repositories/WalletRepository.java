package pods.project.walletservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pods.project.walletservice.entities.Wallet;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query("SELECT w FROM Wallet w WHERE w.user_id = :user_id")
    List<Wallet> findByUserId(@Param("user_id") Integer user_id);
}
