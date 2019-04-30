package com.mqf.study;

import com.nmtx.doc.core.api.springmvc.SpringMVCApiDocConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJdocApplication {

    public static void main(String[] args) {

        SpringMVCApiDocConfig doc = new SpringMVCApiDocConfig();
        doc.setConfigFilePath("jdoc.properties");
        doc.start();

        SpringApplication.run(SpringBootJdocApplication.class, args);
    }

}
