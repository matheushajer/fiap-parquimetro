package br.com.fiap.parquimetro.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class CustomErrorResponse {

    private String timestamp;
    private int status;
    private String Error;
    private String message;
    private String path;

    public CustomErrorResponse(String timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        Error = error;
        this.message = message;
        this.path = path;
    }


}
