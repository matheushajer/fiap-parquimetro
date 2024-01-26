package br.com.fiap.parquimetro.services;

import br.com.fiap.parquimetro.dto.CondutorDTO;
import br.com.fiap.parquimetro.dto.TelefoneDTO;
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

import java.io.IOException;

@Service
public class NotificationService {
    @Autowired
    CondutorRepository condutorRepository;
    @Autowired
    CondutorService condutorService;

    public String validateCondutor(String cpf) {
        boolean enviosms = false, envioemail = false;
        if (validateCPF(cpf)) {
            var cpfExists = condutorRepository.findByCpf(cpf);
            if (cpfExists.isPresent()) {
                var condutor = condutorService.convertToDTO(cpfExists.get());
                var telefonesPrincipais = condutor.telefonesCondutor().stream()
                        .filter(TelefoneDTO::isTelefonePrincipal)
                        .count();
                if (telefonesPrincipais > 0) {
                    for (int i = 0; i < condutor.telefonesCondutor().size(); i++) {
                        var telefonePrincipal = condutor.telefonesCondutor().get(i).isTelefonePrincipal();
                        if (telefonePrincipal) {
                            String DDtelefone = Integer.toString(condutor.telefonesCondutor().get(i).ddd() + condutor.telefonesCondutor().get(i).numeroTelefone());
                            sendSMS(condutor.telefonesCondutor().get(i).ddi(), condutor.telefonesCondutor().get(i).ddd(), condutor.telefonesCondutor().get(i).numeroTelefone(), condutor.nomeCondutor());
                            enviosms = true;
                        }
                    }
                } else {
                    enviosms = false;
                    return "Nao possui numeros principais cadastrados. Defina um numero como principal.";
                }

                if (!condutor.emailCondutor().isEmpty()) {
                    sendEmail(condutor.emailCondutor());
                    envioemail = true;
                } else {
                    envioemail = false;
                    return "Email nao encontrado para envio de comunicacao. Apenas SMS enviado.";
                }
                if (enviosms && envioemail) {
                    return "Comunicacao de SMS e Email enviada.";
                }
                if (!enviosms && envioemail) {
                    return "Somente comunicacao de Email enviada.";
                }
                if (enviosms && !envioemail) {
                    return "Somente comunicacao de SMS enviada.";
                }
            } else {
                throw new RuntimeException("CPF nao existe na base.");
            }

        }
        return "CPF nao esta no formato correto. Nao validado. Favor verifique o CPF cadastrado.";
    }

    public void sendSMS(int ddi, int dd, int telefone, String nome) {

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
                    .field("text", "Bem vindo " + nome + ".")
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

    public String sendEmail(String email) {
        Email from = new Email("ticleyton@gmail.com");
        String subject = "Comunicacao Condutor - FIAP - POS GRADUACAO";
        Email to = new Email(email);
        Content content = new Content("text/plain", "bem vindo! email de verificacao.");
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
