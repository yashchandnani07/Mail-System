package com.college.mailsystem.service;

import com.college.mailsystem.model.Circular;
import com.college.mailsystem.model.Student;
import com.college.mailsystem.repository.CircularRepository;
import com.college.mailsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CircularService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CircularRepository circularRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Sends a circular to filtered students with an optional attachment.
     * 
     * @param subject    Circular subject.
     * @param message    Circular message body.
     * @param department Target department (or "ALL").
     * @param semester   Target semester (or "ALL").
     * @param file       Optional file attachment.
     */
    public void sendCircular(String subject, String message, String department, String semester,
            org.springframework.web.multipart.MultipartFile file) {
        List<Student> students;
        if ("ALL".equalsIgnoreCase(department)) {
            students = studentRepository.findAll();
        } else {
            students = studentRepository.findByDepartmentAndSemester(department, semester);
        }

        for (Student student : students) {
            emailService.sendEmail(student.getEmail(), subject, message, file);
        }

        Circular circular = new Circular();
        circular.setSubject(subject);
        circular.setMessage(message);
        circular.setSentDate(LocalDateTime.now());
        circular.setRecipientCount(students.size());
        circularRepository.save(circular);
    }
}
