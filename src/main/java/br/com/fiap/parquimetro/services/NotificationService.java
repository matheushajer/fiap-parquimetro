package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.*;
import br.com.fiap.parquimetro.entities.Condutor;
import br.com.fiap.parquimetro.repositories.CondutorRepository;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.modelmapper.ModelMapper;
import com.sendgrid.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    CondutorRepository condutorRepository;
    @Autowired
    TelefoneService telefoneService;

    public String validateCondutorSMS(String cpf, String body) {
        if (validateCPF(cpf)) {
            var cpfExists = condutorRepository.findByCpf(cpf);
            if (cpfExists.isPresent()) {
                List<TelefoneDTO> telefonesDTO = (cpfExists.get().getTelefones() != null)
                        ? cpfExists.get().getTelefones().stream().map(telefoneService::convertToDTO).collect(Collectors.toList())
                        : new ArrayList<>();
                    for (int i = 0; i < telefonesDTO.size(); i++) {
                        var telefoneprincipalexists = telefonesDTO.get(i).isTelefonePrincipal();
                        if (telefoneprincipalexists) {
                            sendSMS(telefonesDTO.get(i).ddi(), telefonesDTO.get(i).ddd(), telefonesDTO.get(i).numeroTelefone(), body);
                        }
                    }
                return "SMS enviado com sucesso";
            } else {
                throw new RuntimeException("CPF nao existe na base.");
            }

        }else {
        return "CPF nao esta no formato correto. Nao validado. Favor verifique o CPF cadastrado.";}
    }

    public String validateCondutorEMAIL(String cpf, String assunto, String body) {
        if (validateCPF(cpf)) {
            var cpfExists = condutorRepository.findByCpf(cpf);
            if (cpfExists.isPresent()) {

                if (!cpfExists.get().getEmail().isEmpty()) {
                    sendEmail(cpfExists.get().getEmail(), assunto, body);
                    return "Email enviado com sucesso.";
                } else {
                    return "Email nao encontrado para envio de comunicacao.";
                }
            } else {
                throw new RuntimeException("CPF nao existe na base.");
            }

        }else{
        return "CPF nao esta no formato correto. Nao validado. Favor verifique o CPF cadastrado.";}
    }

    public void sendSMS(int ddi, int dd, int telefone, String text) {

        try {
            StringBuilder builder = new StringBuilder();
            builder.append(ddi).append(dd).append(telefone);
            var telefonecompleto = builder.toString();
            HttpResponse response = Unirest.post("https://api.easysendsms.app/bulksms")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Cookie", "ASPSESSIONIDASCQBARR=NKOHDCHDOFEOOALJIGDGGPAM")
                    .field("username", "cleyticlzrs5i2023")
                    .field("password", "25242598@Rc")
                    .field("to", telefonecompleto)
                    .field("from", "fiap")
                    .field("text", text)
                    .field("type", "0")
                    .asString();

            if (response.isSuccess()) {
                System.out.println("SMS enviado com sucesso!");
                System.out.println("Resposta: " + response.getBody());
            } else {
                System.out.println("Erro ao enviar SMS. Código de resposta: " + response.getStatus());
                System.out.println("Mensagem de erro: " + response.getParsingError().get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Unirest.shutDown();
        }
    }

    public String sendEmail(String email, String assunto, String body) {
        Email from = new Email("ticleyton@gmail.com");
        String subject = assunto;
        Email to = new Email(email);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid("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");

        //SendGrid sg = new SendGrid(System.getenv("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\n"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            return response.getBody();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean validateCPF(String cpf) {
        // Remove caracteres não numéricos do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        boolean allDigitsEqual = true;
        for (int i = 1; i < 11 && allDigitsEqual; i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                allDigitsEqual = false;
            }
        }
        if (allDigitsEqual || cpf.equals("12345678909")) {
            return false;
        }

        // Converte os caracteres do CPF para inteiros
        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Character.getNumericValue(cpf.charAt(i));
        }

        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (10 - i) * digits[i];
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit == 10 || firstDigit == 11) {
            firstDigit = 0;
        }
        if (digits[9] != firstDigit) {
            return false;
        }

        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (11 - i) * digits[i];
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit == 10 || secondDigit == 11) {
            secondDigit = 0;
        }
        if (digits[10] != secondDigit) {
            return false;
        }

        return true;
    }
}
