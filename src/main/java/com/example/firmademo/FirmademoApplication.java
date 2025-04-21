package com.example.firmademo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.firmademo.dto.SignatoryDto;
import com.example.firmademo.services.SignatoryService;

@SpringBootApplication
public class FirmademoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirmademoApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(SignatoryService signatoryService){
		return args -> {
			signatoryService.createSignatory(signatoryService.convertDtoToSignatory(new SignatoryDto("user1","1234")));
			signatoryService.createSignatory(signatoryService.convertDtoToSignatory(new SignatoryDto("user2","1234")));
			signatoryService.createSignatory(signatoryService.convertDtoToSignatory(new SignatoryDto("user3","1234")));
		};
	}

}
