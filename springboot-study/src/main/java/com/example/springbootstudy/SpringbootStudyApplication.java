package com.example.springbootstudy;

import com.example.springbootstudy.domain.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootStudyApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootStudyApplication.class, args);

        Student student = Student.of("Fred", 20, Student.Grade.A);
        System.out.println(student);
    }

}
