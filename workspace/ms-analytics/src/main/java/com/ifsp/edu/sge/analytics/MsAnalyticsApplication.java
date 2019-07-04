package com.ifsp.edu.sge.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MsAnalyticsApplication { public static void main(String[] args) {
		SpringApplication.run(MsAnalyticsApplication.class, args);
	}

}
