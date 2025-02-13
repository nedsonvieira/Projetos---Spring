package br.com.nedson.ScreenMatchComSpring.principal;

import br.com.nedson.ScreenMatchComSpring.model.DadosEpisodio;
import br.com.nedson.ScreenMatchComSpring.model.DadosSerie;
import br.com.nedson.ScreenMatchComSpring.model.DadosTemporada;
import br.com.nedson.ScreenMatchComSpring.model.Episodio;
import br.com.nedson.ScreenMatchComSpring.service.ConexaoApi;
import br.com.nedson.ScreenMatchComSpring.service.ConversaoDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

//        System.out.println("\n Top 10 Episódios");
//        dadosEpisodio.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("n/a"))
//        //        .peek(e -> System.out.println("Filtro (N/A) " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//        //        .peek(e -> System.out.println("Filtro (Ordenar) " + e))
//                .limit(10)
//        //        .peek(e -> System.out.println("Filtro (10) " + e))
//                .map(e -> e.titulo().toUpperCase())
//        //        .peek(e -> System.out.println("Filtro (Mapeamento) " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

//        Busca por nome de ep
//        System.out.println("Digite o trecho de um título:");
//        var trechoTitulo = leitura.nextLine();
//        Optional<Episodio> epBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//
//        if(epBuscado.isPresent()){
//            System.out.println("Episódio encontrado!");
//            System.out.println("Temporada: " + epBuscado.get().getTemporada());
//        } else {
//            System.out.println("Episódio não encontrado!");
//        }

        /*Busca todos eps*/
//        List<Episodio> epsBuscados = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .collect(Collectors.toList());
//
//        epsBuscados.forEach(System.out::println);

//
//        System.out.println("Digite um ano:");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " - Episódio: " + e.getTitulo() +
//                                " - Data Lançamento: " + e.getDataLancamento().format(formatador)
//                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);


        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: " + est.getAverage() +
                "\nMelhor Ep: " + est.getMax() +
                "\nPior ep: " + est.getMin() +
                "\nQuantidade: " + est.getCount());


    }

}
