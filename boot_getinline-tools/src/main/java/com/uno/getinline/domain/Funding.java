package com.uno.getinline.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Funding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
