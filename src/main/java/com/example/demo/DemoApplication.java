package com.example.demo;

import com.example.demo.listener.MySpringBootListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@ServletComponentScan("com.example.demo.listener")
@EnableTransactionManagement
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(DemoApplication.class);
		application.addListeners(new MySpringBootListener());
		application.run(args);
		System.out.println("启动完成！");
	}
}
