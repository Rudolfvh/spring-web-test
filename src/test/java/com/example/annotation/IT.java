package com.example.annotation;

import com.example.ApplicationRunner;
import com.example.ApplicationRunnerTests;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureTestDatabase
@SpringBootTest(classes = {ApplicationRunner.class, ApplicationRunnerTests.class})
@Transactional
public @interface IT {
}
