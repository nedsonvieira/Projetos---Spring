package br.com.nedson.UsandoApiFipe.principal;

import br.com.nedson.UsandoApiFipe.service.Buscador;
import br.com.nedson.UsandoApiFipe.model.Dados;
import br.com.nedson.UsandoApiFipe.model.DadosVeiculos;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private final Scanner leitor;
    private final Buscador buscador;

    public Principal(Buscador buscador) {
        this.leitor = new Scanner(System.in);
        this.buscador = buscador;
    }

    public void menu() {

        System.out.println("\n=== Consulta FIPE ===");
        System.out.println("[1] - Carros");
        System.out.println("[2] - Motos");
        System.out.println("[3] - Caminhões");
        System.out.print("Escolha o tipo de veículo para pesquisar: ");

        try {
            int tipoVeiculo = leitor.nextInt();
            leitor.nextLine();

            if (tipoVeiculo < 1 || tipoVeiculo > 3) {
                System.out.println("Opção inválida! Tente novamente.");
                return;
            }

            List<Dados> listaMarcas = buscador.buscarVeiculosPorTipo(tipoVeiculo);
            System.out.print("Digite o código da marca: ");
            String codigoMarca = leitor.nextLine();

            Optional<Dados> marcaSelecionada = listaMarcas.stream()
                    .filter(m -> m.codigo().equals(codigoMarca))
                    .findFirst();

            if (marcaSelecionada.isEmpty()) {
                System.out.println("Código inválido! Tente novamente.");
                return;
            }

            DadosVeiculos dadosVeiculos = buscador.buscarVeiculoPorCodigo(codigoMarca, marcaSelecionada.get().nome());

            System.out.print("Digite o nome do modelo para pesquisar: ");
            String modeloPesquisa = leitor.nextLine();

            List<Dados> modelosFiltrados = buscador.buscarVeiculoPorModelo(dadosVeiculos, modeloPesquisa);
            if (modelosFiltrados.isEmpty()) {
                System.out.println("Nenhum modelo encontrado!");
                return;
            }

            System.out.print("Digite o código do modelo: ");
            String codigoModelo = leitor.nextLine();

            Optional<Dados> modeloSelecionado = modelosFiltrados.stream()
                    .filter(m -> m.codigo().equals(codigoModelo))
                    .findFirst();

            if (modeloSelecionado.isEmpty()) {
                System.out.println("Código inválido! Tente novamente.");
                return;
            }

            buscador.buscarModeloPorCodigo(codigoMarca, codigoModelo);

        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Use apenas números.");
        }
    }
}
