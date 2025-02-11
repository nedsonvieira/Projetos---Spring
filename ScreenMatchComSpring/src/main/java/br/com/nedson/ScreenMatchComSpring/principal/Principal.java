package br.com.nedson.ScreenMatchComSpring.principal;

import br.com.nedson.ScreenMatchComSpring.model.DadosEpisodio;
import br.com.nedson.ScreenMatchComSpring.model.DadosSerie;
import br.com.nedson.ScreenMatchComSpring.model.DadosTemporada;
import br.com.nedson.ScreenMatchComSpring.model.Episodio;
import br.com.nedson.ScreenMatchComSpring.service.ConexaoApi;
import br.com.nedson.ScreenMatchComSpring.service.ConversaoDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConexaoApi conexaoApi = new ConexaoApi();
    private ConversaoDados conversor = new ConversaoDados();

    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String BUSCAR_TEMPORADA = "&season=";
    private final String API_KEY = "&apikey=b665269f";

    public void exibeMenu(){
        System.out.println("Digite o nome da Série para pesquisar:");
        var nomeSerie = leitura.nextLine().replace(" ", "+");

        var json = conexaoApi.obterDados(ENDERECO + nomeSerie + API_KEY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
			json = conexaoApi.obterDados(ENDERECO + nomeSerie + BUSCAR_TEMPORADA + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
        }
		temporadas.forEach(System.out::println);

//        for (int i = 0; i < dadosSerie.totalTemporadas(); i++) {
//            List<Episodio> episodiosTemporada;
//            episodiosTemporada = temporadas.get(i).episodios();
//
//            for (Episodio episodio : episodiosTemporada) {
//                System.out.println(episodio.titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(episodio -> System.out.println(episodio.titulo())));

        List<DadosEpisodio> dadosEpisodio = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(toList());

        System.out.println("\n Top 5 Episódios");
        dadosEpisodio.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("n/a"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

    }

}
