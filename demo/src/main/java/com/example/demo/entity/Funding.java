package com.example.demo.entity;

import com.example.demo.constant.Status;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Funding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
