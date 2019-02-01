package com.h1totsu.agordo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class AgordoApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(AgordoApplication.class, args);
  }

}

