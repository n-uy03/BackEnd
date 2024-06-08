package com.example.thuctap.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "url_api_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlApiRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "url_api_id")
    private UrlApi urlApi;

    @ManyToOne
    @JoinColumn(name = "role_id" , nullable = false)
    private Role role;

}
