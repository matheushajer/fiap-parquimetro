package br.com.fiap.parquimetro.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // Obter o Instant atual
    Instant instant = Instant.now();

    // Converter Instant para ZonedDateTime associando um fuso horário
    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("America/Sao_Paulo"));

    // Criar um formato de data/hora para o (PT-BR)
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String dataFormatada = zonedDateTime.format(formatter);


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomErrorResponse> illegalArgument(IllegalArgumentException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                dataFormatada,
                status.value(),
                e.getMessage(),
                "Parâmetro inválido!",
                request.getRequestURI());

        return ResponseEntity.status(status).body(errorResponse);

    }

}
