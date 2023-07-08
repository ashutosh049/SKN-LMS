/*
 * **********************************************************************************************************
 * Copyright (c) 2018-2022. Sirfsup Inc. and/or its affiliates. (https://www.sirfsup.com) All Rights reserved.
 * File: sirfsup/sirf-cricket-service-app/Application.java
 * Author: Kumar Ashutosh
 * Last Modified: 02/05/22, 5:45 PM
 * **********************************************************************************************************
 */

package skn.lms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import java.util.concurrent.ForkJoinPool;

import static java.time.ZoneOffset.UTC;
import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition
public class Application {

  @Value("${skn.lms.timezone:UTC}")
  private String timezone;

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    application.run(args);
  }

  @PostConstruct
  void started() {
    log.info("--------------------------------------------------------------");
    log.info("System default timezone:: {}", TimeZone.getDefault());
    if (isEmpty(timezone)) {
      log.warn(
          "Unable to locate property \"sirf.timezone\". Setting \""
              + UTC
              + "\" as default time zone.");
      TimeZone.setDefault(TimeZone.getTimeZone(UTC));
    } else {
      TimeZone.setDefault(TimeZone.getTimeZone(timezone));
    }
    log.info("System default timezone set as :: {}", TimeZone.getDefault());
    log.info("CPU Core: " + Runtime.getRuntime().availableProcessors());
    log.info("CommonPool Parallelism: " + ForkJoinPool.commonPool().getParallelism());
    log.info("CommonPool Common Parallelism: " + ForkJoinPool.getCommonPoolParallelism());
    log.info("--------------------------------------------------------------");
  }
}
