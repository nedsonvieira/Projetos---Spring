package br.com.nedson.ApiTransacoes.models;

import java.time.OffsetDateTime;

public record Transacao(Double valor, OffsetDateTime dataHora) {
}
