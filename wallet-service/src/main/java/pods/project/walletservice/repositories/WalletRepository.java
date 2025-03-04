package pods.project.walletservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.LockModeType;
import pods.project.walletservice.entities.Wallet;

import java.beans.Transient;
import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    // @Transactional
    // @Lock(LockModeType.PESSIMISTIC_WRITE)  // Ensures DB-level lock
    @Query("SELECT w FROM Wallet w WHERE w.user_id = :user_id")
    List<Wallet> findByUserId(@Param("user_id") Integer user_id);


    // @Transactional
    // @Query("UPDATE Wallet w SET w.balance = :balance WHERE w.user_id = :user_id")
    // Integer updateBalance(@Param("user_id") Integer user_id, @Param("balance") Integer balance);
}
