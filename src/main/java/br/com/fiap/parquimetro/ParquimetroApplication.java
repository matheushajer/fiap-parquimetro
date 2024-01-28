package br.com.fiap.parquimetro;

import br.com.fiap.parquimetro.controllers.NotificacaoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ParquimetroApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ParquimetroApplication.class, args);
		// Inicializar o contexto da aplicação Spring
		ApplicationContext context = SpringApplication.run(ParquimetroApplication.class, args);


		// Obter a instância do controlador
		NotificacaoController controller = context.getBean(NotificacaoController.class);

		// Executar o método sendMinuteNotification() do controlador
		controller.setNotificationScheduler();
	}

}
