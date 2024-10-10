package com.springsecurity.com.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application
{

  public static void main(String[] args)
  {
    SpringApplication.run(Application.class, args);
  }

}
