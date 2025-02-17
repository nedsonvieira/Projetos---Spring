package br.com.nedson.UsandoApiFipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculos(List<Dados> modelos) {
}
