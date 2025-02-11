package br.com.nedson.ScreenMatchComSpring;

import br.com.nedson.ScreenMatchComSpring.model.Serie;
import br.com.nedson.ScreenMatchComSpring.service.ConexaoApi;
import br.com.nedson.ScreenMatchComSpring.service.ConversaoDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ScreenMatchComSpringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchComSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var leitor = new Scanner(System.in);

		var conexaoApi = new ConexaoApi();
		var site = "http://www.omdbapi.com/?t=";
		var chave = "&apikey=b665269f";

		System.out.println("Digite o Nome de uma Série:");
		var busca = leitor.nextLine();

		var json = conexaoApi.obterDados(site + busca + chave);

		System.out.println(json);

		ConversaoDados conversor = new ConversaoDados();
		Serie dados = conversor.obterDados(json, Serie.class);

		System.out.println(dados);
	}
}
