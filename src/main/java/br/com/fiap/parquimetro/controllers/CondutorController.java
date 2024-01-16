package br.com.fiap.parquimetro.controllers;

import br.com.fiap.parquimetro.dto.CondutorDTO;
import br.com.fiap.parquimetro.dto.TelefoneDTO;
import br.com.fiap.parquimetro.services.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/condutores")
public class CondutorController {

    @Autowired
    private CondutorService condutorService;

    @GetMapping
    public ResponseEntity<List<CondutorDTO>> getAllCondutores() {
        List<CondutorDTO> condutores = condutorService.getAllCondutores();
        return ResponseEntity.ok(condutores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondutorDTO> getCondutorById(@PathVariable Long id) {
        CondutorDTO condutor = condutorService.getCondutorById(id);
        return ResponseEntity.ok(condutor);
    }

    @PostMapping
    public ResponseEntity<CondutorDTO> createCondutor(@RequestBody CondutorDTO condutorDTO) {
        CondutorDTO createdCondutor = condutorService.createCondutorDTO(condutorDTO);
        return ResponseEntity.ok(createdCondutor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondutorDTO> updateCondutor(@PathVariable Long id, @RequestBody CondutorDTO condutorDTO) {
        CondutorDTO updatedCondutor = condutorService.updateCondutorDTO(id, condutorDTO);
        return ResponseEntity.ok(updatedCondutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCondutor(@PathVariable Long id) {
        condutorService.deleteCondutor(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/adicionar-telefones")
    public ResponseEntity<CondutorDTO> adicionarNovosTelefonesAoCondutor(
            @PathVariable Long id, @RequestBody List<TelefoneDTO> novosTelefonesDTO) {
        CondutorDTO updatedCondutor = condutorService.adicionarNovosTelefonesAoCondutor(id, novosTelefonesDTO);
        return ResponseEntity.ok(updatedCondutor);
    }

    @DeleteMapping("/{id}/remover-telefones")
    public ResponseEntity<CondutorDTO> removerTelefonesDoCondutor(
            @PathVariable Long id, @RequestParam List<Integer> ordens) {
        CondutorDTO updatedCondutor = condutorService.removerTelefonesDoCondutor(id, ordens);
        return ResponseEntity.ok(updatedCondutor);
    }


}
