package br.com.nedson.ApiTransacoes.models;

public record Estatistica(Long count,
                          Double sum,
                          Double avg,
                          Double min,
                          Double max) {
}
