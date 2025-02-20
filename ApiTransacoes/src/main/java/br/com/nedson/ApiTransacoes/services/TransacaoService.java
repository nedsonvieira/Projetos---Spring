package br.com.nedson.ApiTransacoes.services;

import br.com.nedson.ApiTransacoes.models.Transacao;
import br.com.nedson.ApiTransacoes.exceptions.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final List<Transacao> listaTransacoes = new ArrayList<>();

    public void addTransacoes(Transacao transacao){
        log.info("Adicionando transação...");
        if (transacao.dataHora().isAfter(OffsetDateTime.now())){
            log.error("Data e Hora maiores que a atual!");
            throw new UnprocessableEntity("Data e Hora maiores que a atual!");
        }
        if(transacao.valor() < 0){
            log.error("Valor da transação menor que 0!");
            throw new UnprocessableEntity("Valor da transação menor que 0!");
        }
        log.info("Transação adicionada com sucesso!");
        listaTransacoes.add(transacao);
    }

    public void deletarTransacoes(){
        log.info("Deletando transações...");
        listaTransacoes.clear();
        log.info("Transações deletadas com sucesso!");
    }

    public List<Transacao> buscarTransacoesNoIntervalo(Integer intervalo){
        log.info("Gerando lista de transações que aconteceram nos últimos {} segundos...", intervalo);
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervalo);
        log.info("Lista de transações que aconteceram nos últimos {} segundos gerada com sucesso!", intervalo);
        return listaTransacoes.stream()
                .filter(t -> t.dataHora().isAfter(dataHoraIntervalo))
                .toList();
    }
}
