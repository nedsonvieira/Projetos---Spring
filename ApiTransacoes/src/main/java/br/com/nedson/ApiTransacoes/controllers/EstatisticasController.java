package br.com.nedson.ApiTransacoes.controllers;

import br.com.nedson.ApiTransacoes.models.Estatistica;
import br.com.nedson.ApiTransacoes.services.EstatisticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estatistica")
public class EstatisticasController {

    private final EstatisticaService estatisticaService;

    @GetMapping
    @Operation(description = "EndPoint - Gerar Estatísticas das Transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
    })
    public ResponseEntity<Estatistica> gerarEstatisticas(
            @RequestParam(value = "intervalo", required = false, defaultValue = "60") Integer intervalo){
        return ResponseEntity.ok(estatisticaService.gerarEstatisticas(intervalo));
    }

}
