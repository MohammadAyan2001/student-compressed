package com.example.student_compressed.controller;

import com.example.student_compressed.model.Student;
import com.example.student_compressed.service.StudentService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
@Slf4j
public class StudentController {

    private final StudentService service;


    public StudentController(StudentService service) {
        this.service = service;
    }


    @PostMapping("/upload")
    public ResponseEntity<Student> uploadSingle(@RequestBody Student student) {
        Student saved = service.save(student);
        return ResponseEntity.ok(saved);
    }


   @PostMapping("/upload/many")
public ResponseEntity<List<Student>> uploadMany(@RequestBody List<Student> students) {
    long startTime = System.currentTimeMillis();

    int totalStudents = students.size();
    int estimatedSize = students.toString().length(); // approximate size in chars

    log.info("Received {} students, approx size={} chars", totalStudents, estimatedSize);

    List<Student> saved = service.saveAll(students);

    long endTime = System.currentTimeMillis();
    log.info("Saved {} students in {} ms", saved.size(), (endTime - startTime));

    return ResponseEntity.ok(saved);
}

    @GetMapping
    public ResponseEntity<List<Student>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
