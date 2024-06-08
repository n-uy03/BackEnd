package com.example.thuctap.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "url_api")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

}
