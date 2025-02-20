package br.com.nedson.UsandoApiFipe;

import br.com.nedson.UsandoApiFipe.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsandoApiFipeApplication implements CommandLineRunner {

    private final Principal principal;

    @Autowired
    public UsandoApiFipeApplication(Principal principal) {
        this.principal = principal;
    }

    public static void main(String[] args) {
        SpringApplication.run(UsandoApiFipeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        principal.menu();
    }
}