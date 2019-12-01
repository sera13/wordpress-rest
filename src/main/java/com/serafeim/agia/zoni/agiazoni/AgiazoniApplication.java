package com.serafeim.agia.zoni.agiazoni;

import com.serafeim.agia.zoni.agiazoni.service.ReadJSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgiazoniApplication {
	@Autowired
	public ReadJSONService readJSONService;


	public static void main(String[] args) {
		SpringApplication.run(AgiazoniApplication.class, args);
	}

}
