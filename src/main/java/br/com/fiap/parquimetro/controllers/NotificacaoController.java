package br.com.fiap.parquimetro.controllers;

import br.com.fiap.parquimetro.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificacaoController {

    @Autowired
    private NotificationService notificationService;



    @PostMapping("/send/{cpf}")
    public ResponseEntity<String> setNotification(@PathVariable Long cpf) {
        try {
            return ResponseEntity.ok(notificationService.validateCondutor(cpf.toString()));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }



}
