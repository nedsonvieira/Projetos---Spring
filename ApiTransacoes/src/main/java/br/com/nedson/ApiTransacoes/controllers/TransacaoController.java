package br.com.nedson.ApiTransacoes.controllers;

import br.com.nedson.ApiTransacoes.models.Transacao;
import br.com.nedson.ApiTransacoes.services.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    @Operation(description = "EndPoint - Adicionar transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação adicionada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados da transação inválidos"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição")
    })
    public ResponseEntity<Void> addTransacao(@RequestBody Transacao transacao){
        transacaoService.addTransacoes(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    @Operation(description = "EndPoint - Deletar transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transações deletadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição")
    })
    public ResponseEntity<Void> deletarTransacoes(){
        transacaoService.deletarTransacoes();
        return ResponseEntity.ok().build();
    }
}
