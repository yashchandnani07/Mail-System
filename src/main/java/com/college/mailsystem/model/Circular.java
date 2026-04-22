package com.college.mailsystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "circulars")
public class Circular {
    @Id
    private String id;
    private String subject;
    private String message;
    private LocalDateTime sentDate;
    private int recipientCount;
}
