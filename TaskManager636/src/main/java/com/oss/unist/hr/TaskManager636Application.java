package com.oss.unist.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//cd /d I:\postgreSQL\bin nakon toga psql -U postgres /// \q za odspojit se /// \l za vidit sve baze /// \dt vidit sve tablice
@EnableJpaRepositories("com.oss.unist.hr.*")
@EntityScan("com.oss.unist.hr.*")
@ComponentScan(basePackages = "com.oss.unist.hr")
@SpringBootApplication
public class TaskManager636Application {

	public static void main(String[] args) {
		SpringApplication.run(TaskManager636Application.class, args);
	}

}
