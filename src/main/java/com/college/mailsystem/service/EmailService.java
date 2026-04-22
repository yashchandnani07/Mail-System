package com.college.mailsystem.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an email with an optional attachment.
     * 
     * @param to      Recipient email address.
     * @param subject Email subject.
     * @param body    Email body.
     * @param file    Optional file attachment.
     */
    public void sendEmail(String to, String subject, String body,
            org.springframework.web.multipart.MultipartFile file) {
        try {
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper = new org.springframework.mail.javamail.MimeMessageHelper(
                    message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            if (file != null && !file.isEmpty()) {
                helper.addAttachment(file.getOriginalFilename(), file);
            }

            mailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            e.printStackTrace(); // Log error but don't stop execution
        }
    }
}
