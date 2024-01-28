package org.IM2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class Im2Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  SpringApplication.run(Im2Application.class, args);
//		PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
//		System.out.println(encoder.encode("pass"));
		Logger logger = Logger.getLogger(Im2Application.class.getName());
		logger.log(Level.INFO, "Приложение запущено");
		//TODO:
	}

}
