package com.example.student_compressed.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.student_compressed.model.Student;

public interface StudentRepository extends MongoRepository<Student, String> { }
