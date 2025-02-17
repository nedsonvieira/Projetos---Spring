package br.com.nedson.UsandoApiFipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ConversorDados implements IConversorDados {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        try {
            return mapper.readValue(json,
                    mapper.getTypeFactory().constructCollectionType(List.class, classe));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter JSON para lista de objetos", e);
        }

    }

}