package br.com.nedson.UsandoApiFipe.principal;

import br.com.nedson.UsandoApiFipe.controle.Buscador;
import br.com.nedson.UsandoApiFipe.model.Dados;
import br.com.nedson.UsandoApiFipe.model.DadosVeiculos;

import java.util.*;

public class Principal {

    private Scanner leitor;
    private Buscador buscador;

    private List<Dados> listaDadosVeiculo;
    private List<Dados> listaModeloPesquisado;
    private DadosVeiculos dadosVeiculos;
    private final Map<Integer, String> TIPO_VEICULO;

    public Principal() {
        TIPO_VEICULO = new HashMap<>();
        TIPO_VEICULO.put(1, "carros");
        TIPO_VEICULO.put(2, "motos");
        TIPO_VEICULO.put(3, "caminhoes");

        leitor = new Scanner(System.in);
        buscador = new Buscador();
        listaDadosVeiculo = new ArrayList<>();
        listaModeloPesquisado = new ArrayList<>();
        dadosVeiculos = null;

    }

    public void exibeMenu() {
        var tipoVeiculo = 0;
        var json = "";
        boolean entradaValidaVeiculo = false;
        boolean entradaValidaMarca = false;
        boolean entradaValidaModelo = false;
        boolean entradaValidaCodModelo = false;

        while (!entradaValidaVeiculo) {
            try {
                System.out.println("[1] - Carros" +
                        "\n[2] - Motos" +
                        "\n[3] - Caminhões");
                System.out.println("Escolha o tipo de veículo para pesquisar [1], [2] ou [3]:");

                tipoVeiculo = leitor.nextInt();
                leitor.nextLine();

                if (tipoVeiculo >= 1 && tipoVeiculo <= 3) {
                    entradaValidaVeiculo = true;
                    listaDadosVeiculo = buscador.buscarVeiculosPorTipo(TIPO_VEICULO.get(tipoVeiculo));

                    while (!entradaValidaMarca) {
                        System.out.println("Escolha o código da marca: ");
                        var codigoPesquisa = leitor.nextLine();

                        Optional<Dados> marcaEncontrada = listaDadosVeiculo.stream()
                                .filter(m -> m.codigo().equals(codigoPesquisa))
                                .findFirst();

                        if (marcaEncontrada.isPresent()) {
                            entradaValidaMarca = true;
                            dadosVeiculos = buscador.buscarVeiculoPorCodigo(marcaEncontrada.get().codigo(), marcaEncontrada.get().nome());
                            System.out.println(dadosVeiculos);

                            while (!entradaValidaModelo) {
                                System.out.println("Digite o nome do modelo para pesquisar: ");
                                var modeloPesquisa = leitor.nextLine();

                                Optional<Dados> modeloEncontrado = dadosVeiculos.modelos().stream()
                                        .filter(m -> m.nome().toUpperCase().contains(modeloPesquisa.toUpperCase()))
                                        .findFirst();

                                if (modeloEncontrado.isPresent()) {
                                    entradaValidaModelo = true;

                                    listaModeloPesquisado = buscador.buscarVeiculoPorModelo(dadosVeiculos, modeloPesquisa);

                                    while (!entradaValidaCodModelo) {
                                        System.out.println("Digite o código de um modelo para pesquisar: ");
                                        var codModeloPesquisa = leitor.nextLine();

                                        Optional<Dados> codModeloEncontrado = listaModeloPesquisado.stream()
                                                .filter(m -> m.codigo().equals(codModeloPesquisa))
                                                .findFirst();

                                        if (codModeloEncontrado.isPresent()) {
                                            entradaValidaCodModelo = true;
                                            buscador.buscarModeloPorCodigo(codModeloPesquisa);
                                        } else {
                                            System.out.println("\n********************************************************************************************************");
                                            System.out.println("Entrada inválida! Código não encontrado na lista de modelos.");
                                            System.out.println("********************************************************************************************************\n");
                                        }
                                    }
                                } else {
                                    System.out.println("\n********************************************************************************************************");
                                    System.out.println("Entrada inválida! Nome não encontrado na lista de modelos.");
                                    System.out.println("********************************************************************************************************\n");
                                }
                            }
                        } else {
                            System.out.println("\n********************************************************************************************************");
                            System.out.println("Entrada inválida! Código não encontrado na lista de marcas.");
                            System.out.println("********************************************************************************************************\n");
                        }
                    }
                } else {
                    System.out.println("\n********************************************************************************************************");
                    System.out.println("Entrada inválida! Por favor, insira um número entre 1 e 3.");
                    System.out.println("********************************************************************************************************\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n********************************************************************************************************");
                System.out.println("Entrada inválida! Por favor, insira um número entre 1 e 3.");
                System.out.println("********************************************************************************************************\n");
                leitor.nextLine();
            }
        }
    }
}
