package com.example.student_compressed.service;

import org.springframework.stereotype.Service;

import com.example.student_compressed.model.Student;
import com.example.student_compressed.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    // Save single student
    public Student save(Student student) {
        return repo.save(student);
    }

    // Save multiple students
    public List<Student> saveAll(List<Student> students) {
        return repo.saveAll(students);
    }

    // Get all students
    public List<Student> findAll() {
        return repo.findAll();
    }
}
