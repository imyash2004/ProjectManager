package com.yash.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor@AllArgsConstructor
public class Comments {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    private LocalDate createdDate;
    @ManyToOne
    private User user;

    @ManyToOne
    private Issue issue;
}
