package com.fincomun;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAsync
@SpringBootApplication
public class EstadosCuentaApplication {

    public static void main(String[] args) {

        SpringApplication.run(EstadosCuentaApplication.class, args);

    }

}
