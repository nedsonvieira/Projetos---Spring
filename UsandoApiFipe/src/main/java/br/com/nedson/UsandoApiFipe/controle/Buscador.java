package br.com.nedson.UsandoApiFipe.controle;

import br.com.nedson.UsandoApiFipe.model.Dados;
import br.com.nedson.UsandoApiFipe.model.DadosVeiculos;
import br.com.nedson.UsandoApiFipe.model.Veiculo;
import br.com.nedson.UsandoApiFipe.service.ConexaoApi;
import br.com.nedson.UsandoApiFipe.service.ConversorDados;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Buscador {

    private ConexaoApi conexao;
    private ConversorDados conversor;

    final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    final String URI_MARCAS = "/marcas/";
    final String URI_MODELOS = "/modelos/";
    final String URI_ANOS = "/anos/";
    private String json;
    private String enderecoCompleto;

    public Buscador() {
        this.conexao = new ConexaoApi();
        this.conversor = new ConversorDados();
    }

    public List<Dados> buscarVeiculosPorTipo(String tipoVeiculo) {
        setEnderecoCompleto(ENDERECO + tipoVeiculo + URI_MARCAS);
        setJson(getConexao().obterDadosFipe(getEnderecoCompleto()));

        var listaDadosVeiculo = getConversor().obterLista(getJson(), Dados.class);

        System.out.println("Código\t\tNome");
        listaDadosVeiculo.stream()
                .sorted((c1, c2) -> Integer.compare(Integer.parseInt(c1.codigo()), Integer.parseInt(c2.codigo())))
                .forEach(c -> System.out.println("\t" + c.codigo() + "\t\t" + c.nome()));

        return listaDadosVeiculo;
    }

    public DadosVeiculos buscarVeiculoPorCodigo(String codigoVeiculo, String nomeMarca) {
        setEnderecoCompleto(getEnderecoCompleto() + codigoVeiculo + URI_MODELOS);
        setJson(getConexao().obterDadosFipe(getEnderecoCompleto()));

        var dadosVeiculo = getConversor().obterDados(getJson(), DadosVeiculos.class);

        System.out.println("\nModelos da marca " + nomeMarca);
        dadosVeiculo.modelos().stream()
                .sorted((dc1, dc2) -> Integer.compare(Integer.parseInt(dc1.codigo()), Integer.parseInt(dc2.codigo())))
                .forEach(m -> System.out.println("Código: " + m.codigo() + "\t\tModelo: " + m.nome()));

        return dadosVeiculo;
    }

    public List<Dados> buscarVeiculoPorModelo(@NotNull DadosVeiculos modeloVeiculo, String modeloPesquisa) {
        var listaModeloPesquisado = modeloVeiculo.modelos().stream()
                .filter(dn -> dn.nome().toUpperCase().contains(modeloPesquisa.toUpperCase()))
                .sorted((dc1, dc2) -> Integer.compare(Integer.parseInt(dc1.codigo()), Integer.parseInt(dc2.codigo())))
                .collect(Collectors.toList());
        listaModeloPesquisado.forEach(m -> System.out.println("Código: " + m.codigo() + "\t\tModelo: " + m.nome()));

        return listaModeloPesquisado;
    }

    public void buscarModeloPorCodigo(String codigoModelo) {
        setEnderecoCompleto(getEnderecoCompleto() + codigoModelo + URI_ANOS);
        setJson(conexao.obterDadosFipe(getEnderecoCompleto()));

        var listaDadosAnosModelo = getConversor().obterLista(json, Dados.class);
        Veiculo veiculo;
        List<Veiculo> listaVeiculos = new ArrayList<>();

        for (Dados dados : listaDadosAnosModelo) {
            setJson(getConexao().obterDadosFipe(getEnderecoCompleto() + dados.codigo()));
            veiculo = getConversor().obterDados(getJson(), Veiculo.class);

            listaVeiculos.add(veiculo);
        }

        listaVeiculos.stream()
                .sorted((c1, c2) -> Integer.compare(Integer.parseInt(c1.ano()), Integer.parseInt(c2.ano())))
                .forEach(v -> System.out.println(
                        "Ano Modelo: " + v.ano() +
                                " Marca: " + v.marca() +
                                " Modelo: " + v.modelo() +
                                " Valor: " + v.valor() +
                                " Combustível: " + v.combustivel()));

    }

    public ConexaoApi getConexao() {
        return conexao;
    }

    public ConversorDados getConversor() {
        return conversor;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }
}
