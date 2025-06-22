package com.memorybottle.memory_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class MemoryAppApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(MemoryAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("数据源：" + dataSource.getConnection());
	}
}
