package br.com.nedson.UsandoApiFipe;

import br.com.nedson.UsandoApiFipe.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsandoApiFipeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UsandoApiFipeApplication.class, args);
    }


    @Override
    public void run(String... args) {
        Principal principal = new Principal();
        principal.menu();
    }
}
