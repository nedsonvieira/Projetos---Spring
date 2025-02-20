package br.com.nedson.UsandoApiFipe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConexaoApi {

    private static final Logger logger = LoggerFactory.getLogger(ConexaoApi.class);
    private final HttpClient client;

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
            logger.error("Erro de conexão com a API FIPE: {}", e.getMessage());
            throw new RuntimeException("Erro de conexão com a API FIPE", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Requisição interrompida: {}", e.getMessage());
            throw new RuntimeException("Requisição interrompida", e);
        }
    }
}