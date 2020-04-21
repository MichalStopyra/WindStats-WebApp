package com.windstatsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {


    public static void main(String[] args) {
       // ArrayList<String> ar = Tools.DateArr("january");
       // Arrays.toString(ar.toArray());
        //for(int i = 0; i < ar.size(); i++)
          //  System.out.println(ar.get(i));
        SpringApplication.run(Application.class, args);
    }

}
