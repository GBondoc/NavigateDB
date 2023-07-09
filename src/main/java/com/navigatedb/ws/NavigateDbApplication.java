package com.navigatedb.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.navigatedb.ws.repository")
@EntityScan("com.navigatedb.ws.io.entity")
public class NavigateDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(NavigateDbApplication.class, args);
    }

}
