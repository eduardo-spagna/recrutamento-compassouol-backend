package com.compassouol.backendrecruitment;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendRecruitmentApplication {
	@PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

	public static void main(String[] args) {
		SpringApplication.run(BackendRecruitmentApplication.class, args);
	}

}
