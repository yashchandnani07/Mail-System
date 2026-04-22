package com.college.mailsystem.repository;

import com.college.mailsystem.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByDepartmentAndSemester(String department, String semester);
}
