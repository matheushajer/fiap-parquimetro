package br.com.fiap.parquimetro.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class NotificationScheduler {

    // Método agendado para executar a cada hora
    @Scheduled(cron = "0 0 * * * *")
    public void sendHourlyNotification() {
        // Lógica para consultar o banco de dados e verificar se é hora de enviar a notificação
        LocalTime currentTime = LocalTime.now();
        LocalTime nextNotificationTime = getNextNotificationTimeFromDatabase(); // Método para obter o horário da próxima notificação do banco de dados

        if (currentTime.equals(nextNotificationTime)) {
            // É hora de enviar a notificação, execute a lógica de envio aqui
            sendNotification();
        }
    }

    // Método para obter o horário da próxima notificação do banco de dados (implementação fictícia)
    private LocalTime getNextNotificationTimeFromDatabase() {
        // Implementação fictícia para obter o horário da próxima notificação do banco de dados
        return LocalTime.of(9, 0); // Por exemplo, 9:00 AM
    }

    // Método para enviar a notificação (implementação fictícia)
    private void sendNotification() {
        // Implementação fictícia para enviar a notificação
        System.out.println("Notification sent!");
    }
}

