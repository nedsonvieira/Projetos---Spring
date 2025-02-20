package br.com.nedson.UsandoApiFipe.service;

import br.com.nedson.UsandoApiFipe.model.Dados;
import br.com.nedson.UsandoApiFipe.model.DadosVeiculos;
import br.com.nedson.UsandoApiFipe.model.Veiculo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Buscador {

    private final ConexaoApi conexao;
    private final ConversorDados conversor;
    private static final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public Buscador(ConexaoApi conexao, ConversorDados conversor) {
        this.conexao = conexao;
        this.conversor = conversor;
    }

    public List<Dados> buscarVeiculosPorTipo(int tipo) {
        String tipoVeiculo = switch (tipo) {
            case 1 -> "carros";
            case 2 -> "motos";
            case 3 -> "caminhoes";
            default -> throw new IllegalArgumentException("Tipo inválido!");
        };

        String url = URL_BASE + tipoVeiculo + "/marcas/";
        String json = conexao.obterDadosFipe(url);
        List<Dados> listaMarcas = conversor.obterLista(json, Dados.class);

        System.out.println("\n=== Marcas Disponíveis ===");
        listaMarcas.stream()
                .sorted(Comparator.comparingInt(m -> Integer.parseInt(m.codigo())))
                .forEach(m -> System.out.println("Código: " + m.codigo() + " | Nome: " + m.nome()));

        return listaMarcas;
    }

    public DadosVeiculos buscarVeiculoPorCodigo(String codigoMarca, String nomeMarca) {
        String url = URL_BASE + "carros/marcas/" + codigoMarca + "/modelos/";
        String json = conexao.obterDadosFipe(url);
        DadosVeiculos veiculos = conversor.obterDados(json, DadosVeiculos.class);

        System.out.println("\n=== Modelos da Marca: " + nomeMarca + " ===");
        veiculos.modelos().stream()
                .sorted(Comparator.comparingInt(m -> Integer.parseInt(m.codigo())))
                .forEach(m -> System.out.println("Código: " + m.codigo() + " | Modelo: " + m.nome()));

        return veiculos;
    }

    public List<Dados> buscarVeiculoPorModelo(DadosVeiculos veiculos, String modeloPesquisa) {
        List<Dados> modelosFiltrados = veiculos.modelos().stream()
                .filter(m -> m.nome().toUpperCase().contains(modeloPesquisa.toUpperCase()))
                .sorted(Comparator.comparingInt(m -> Integer.parseInt(m.codigo())))
                .collect(Collectors.toList());

        System.out.println("\n=== Modelos Encontrados ===");
        modelosFiltrados.forEach(m -> System.out.println("Código: " + m.codigo() + " | Modelo: " + m.nome()));

        return modelosFiltrados;
    }

    public void buscarModeloPorCodigo(String codigoMarca, String codigoModelo) {
        String url = URL_BASE + "carros/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos/";
        String json = conexao.obterDadosFipe(url);

        List<Dados> anosModelo = conversor.obterLista(json, Dados.class);

        System.out.println("\n=== Detalhes do Modelo ===");
        for (Dados ano : anosModelo) {
            String urlAno = url + ano.codigo();
            String jsonAno = conexao.obterDadosFipe(urlAno);
            Veiculo veiculo = conversor.obterDados(jsonAno, Veiculo.class);

            System.out.println("Ano: " + veiculo.ano() +
                    " | Marca: " + veiculo.marca() +
                    " | Modelo: " + veiculo.modelo() +
                    " | Valor: " + veiculo.valor() +
                    " | Combustível: " + veiculo.combustivel());
        }
    }
}