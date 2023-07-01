package com.github.fashionbrot;

import com.github.fashionbrot.annotation.EnableValidatedConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableValidatedConfig(localeParamName = "y")
public class ValidatedTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidatedTestApplication.class,args);
    }

}
