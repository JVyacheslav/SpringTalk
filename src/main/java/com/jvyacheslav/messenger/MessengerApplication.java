package com.jvyacheslav.messenger;

import com.jvyacheslav.messenger.database.UserDatabaseManager;
import com.jvyacheslav.messenger.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
//Main class
@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@EntityScan("com.jvyacheslav.messenger")
public class MessengerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class);
	}

}
