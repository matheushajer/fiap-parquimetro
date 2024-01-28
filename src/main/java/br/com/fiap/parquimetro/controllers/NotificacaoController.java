package br.com.fiap.parquimetro.controllers;

import br.com.fiap.parquimetro.dto.ReciboDTO;
import br.com.fiap.parquimetro.services.EstacionamentoService;
import br.com.fiap.parquimetro.services.NotificationService;
import org.hibernate.query.QueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificacaoController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EstacionamentoService estacionamentoService;



    @PostMapping("/send/sms/{cpf}")
    public ResponseEntity<String> setNotificationSMS(@PathVariable Long cpf, @RequestParam String body) {
        try {
            return ResponseEntity.ok(notificationService.validateCondutorSMS(cpf.toString(), body));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @PostMapping("/send/email/{cpf}")
    public ResponseEntity<String> setNotificationEMAIL(@PathVariable Long cpf, @RequestParam String assunto, @RequestParam String body) {
        try {
            return ResponseEntity.ok(notificationService.validateCondutorEMAIL(cpf.toString(), assunto, body));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/send/scheduler")
    public ResponseEntity<String> setNotificationScheduler() {
        try {
            return ResponseEntity.ok(estacionamentoService.sendHourlyNotification());
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }



}
