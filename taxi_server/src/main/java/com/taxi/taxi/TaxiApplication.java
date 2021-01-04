package com.taxi.taxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		TaxiApplication.class,
		Jsr310JpaConverters.class
})
public class TaxiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiApplication.class, args);
	}

	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
