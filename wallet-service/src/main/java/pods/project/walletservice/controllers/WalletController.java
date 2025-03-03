package pods.project.walletservice.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pods.project.walletservice.entities.Wallet;
import pods.project.walletservice.repositories.WalletRepository;
import pods.project.walletservice.services.WalletService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WalletController {

    private static final Log log = LogFactory.getLog(WalletController.class);
    private final WalletRepository walletRepository;
    private final RestTemplate restTemplate;

    private WalletService walletService;

    @Autowired
    public WalletController(WalletRepository walletRepository, WalletService walletService) {
        this.walletRepository = walletRepository;
        this.restTemplate = new RestTemplate();
        this.walletService = walletService;
    }

    @GetMapping("/wallets")
    public ResponseEntity<?> findAll() {
        return walletService.findAll();
    }

    @GetMapping("/wallets/{user_id}")
    public ResponseEntity<?> findByUserId(@PathVariable Integer user_id) {
        return walletService.findByUserId(user_id);
    }

    @PostMapping("/wallets/{user_id}")
    public ResponseEntity<?> insertIntoWallets(@PathVariable Integer user_id) {
        return walletService.insertIntoWallets(user_id);
    }

    @PutMapping("/wallets/{user_id}")
    public ResponseEntity<?> updateWalletBalance(@PathVariable Integer user_id, @RequestBody Map<String, String> wallet) {
        return walletService.updateWalletBalance(user_id, wallet);
    }

    @DeleteMapping("/wallets/{user_id}")
    public ResponseEntity<?> deleteWalletByUserId(@PathVariable Integer user_id) {
        return walletService.deleteWalletByUserId(user_id);
    }

    @DeleteMapping("/wallets")
    public ResponseEntity<?> deleteWallets() {
        return walletService.deleteWallets();
    }

}
