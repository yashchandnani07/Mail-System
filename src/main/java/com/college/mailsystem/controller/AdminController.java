package com.college.mailsystem.controller;

import com.college.mailsystem.model.Circular;
import com.college.mailsystem.model.Student;
import com.college.mailsystem.repository.CircularRepository;
import com.college.mailsystem.repository.StudentRepository;
import com.college.mailsystem.service.CircularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CircularRepository circularRepository;

    @Autowired
    private CircularService circularService;

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentRepository.deleteById(id);
    }

    /**
     * Endpoint to send a circular with an optional attachment.
     * Uses @RequestParam to handle multipart/form-data.
     */
    @PostMapping("/send-circular")
    public String sendCircular(
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            @RequestParam("department") String department,
            @RequestParam("semester") String semester,
            @RequestParam(value = "file", required = false) org.springframework.web.multipart.MultipartFile file) {
        circularService.sendCircular(subject, message, department, semester, file);
        return "Circular sent successfully!";
    }

    /**
     * Endpoint to clear the entire circular history.
     * Deletes all records from the circulars collection.
     */
    @DeleteMapping("/history")
    public void clearHistory() {
        circularRepository.deleteAll();
    }

    /**
     * Endpoint to fetch the history of sent circulars.
     * 
     * @return List of all circulars.
     */
    @GetMapping("/history")
    public List<Circular> getHistory() {
        return circularRepository.findAll();
    }
}
