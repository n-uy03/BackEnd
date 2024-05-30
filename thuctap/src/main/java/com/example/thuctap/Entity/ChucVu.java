package com.example.thuctap.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Entity
@Table(name = "chucvu")
@Data @NoArgsConstructor @AllArgsConstructor
public class ChucVu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private UUID idcv;

    @Column(name = "Ten")
    private String tencv;




    public ChucVu(String value) {
        this.tencv = value;
    }
}
