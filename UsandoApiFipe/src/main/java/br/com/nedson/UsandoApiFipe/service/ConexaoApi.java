package br.com.nedson.UsandoApiFipe.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexaoApi {

    private final HttpClient client;

    // Construtor - permite reutilização do HttpClient
    public ConexaoApi() {
        this.client = HttpClient.newHttpClient();
    }

    public String obterDadosFipe(String endereco) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .GET()
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException e) {
            System.err.println("Erro de conexão com a API FIPE: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Requisição interrompida: " + e.getMessage());
        }
        return null; // Retorna null em caso de falha
    }

}
