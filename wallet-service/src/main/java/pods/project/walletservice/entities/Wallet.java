package pods.project.walletservice.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @Column(name = "user_id")
    Integer user_id;

    @Column(name = "balance", nullable = false)
    Integer balance;

    public Wallet() {
        super();
    }

    public Wallet(Integer balance) {
        super();
        this.balance = balance;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

}
