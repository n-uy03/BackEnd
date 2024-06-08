package com.example.thuctap.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nhanvien_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NhanVienRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "nhanvien_id")
    private NhanVien nhanVien;

}
