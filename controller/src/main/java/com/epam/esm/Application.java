package com.epam.esm;

import com.epam.esm.reader.MainDataReader;
import com.epam.esm.util.BeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {

    SpringApplication.run(Application.class, args);

    MainDataReader mainDataReader = BeanUtil.getBean(MainDataReader.class);
    mainDataReader.startReading();
  }
}
