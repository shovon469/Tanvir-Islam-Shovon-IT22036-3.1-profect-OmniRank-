package com.omnirank.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PlatformHandle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String platform;
    private String handle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}