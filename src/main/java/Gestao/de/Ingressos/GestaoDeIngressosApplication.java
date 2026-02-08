package Gestao.de.Ingressos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GestaoDeIngressosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoDeIngressosApplication.class, args);
	}

}
