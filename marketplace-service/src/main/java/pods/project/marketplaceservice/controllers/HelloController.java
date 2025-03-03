package pods.project.marketplaceservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HelloController {

    @RequestMapping
    public String hello(){
        return "Hello From Market Place Service!";
    }
    @RequestMapping("/check")
    public String check() throws UnknownHostException {
        StringBuffer message = new StringBuffer();
        message.append("Hello from Marketplace Service!\n");
        message.append("Username: " + System.getProperty("user.name") + "\n");
        message.append("Hostname: " + InetAddress.getLocalHost().getHostName() + "\n");
        message.append("IP Address: " + InetAddress.getLocalHost().getHostAddress() + "\n");
        return message.toString();
    }
}
