package com.example.springbootstudy.service;

import com.example.springbootstudy.domain.Student;
import com.example.springbootstudy.domain.Student2;
import com.example.springbootstudy.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public void printStudent(String name) {
        Student student = studentRepository.getStudent(name);
        System.out.println("찾는 학생 : " + student);
    }

    @PostConstruct
    public void init() {
        studentRepository.enroll("jack", 15, Student.Grade.A);
    }
}
