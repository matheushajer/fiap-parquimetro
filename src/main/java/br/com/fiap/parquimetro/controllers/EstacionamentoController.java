package br.com.fiap.parquimetro.controllers;

import br.com.fiap.parquimetro.dto.EstacionamentoDTO;
import br.com.fiap.parquimetro.services.EstacionamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estacionamentos")
public class EstacionamentoController {

    @Autowired
    private EstacionamentoService estacionamentoService;

    @PostMapping("/iniciar")
    public ResponseEntity<EstacionamentoDTO> iniciarPeriodoDeEstacionamento(@RequestBody EstacionamentoDTO estacionamentoDTO) {
        EstacionamentoDTO resultado = estacionamentoService.iniciarPeriodoDeEstacionamento(estacionamentoDTO);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }

    @PostMapping("/encerrar/{id}")
    public ResponseEntity<EstacionamentoDTO> encerrarPeriodoDeEstacionamento(@PathVariable Long id) {
        EstacionamentoDTO resultado = estacionamentoService.encerrarPeriodoDeEstacionamento(id);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

}
