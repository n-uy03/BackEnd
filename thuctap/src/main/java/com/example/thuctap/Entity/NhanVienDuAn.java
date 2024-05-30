package com.example.thuctap.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name="nhanvien_duan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienDuAn {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "nhanvien_id")
    @JsonIgnore
    private NhanVien nhanvien; // Khóa ngoại trỏ đến NhanVien

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "duan_id")
    private DuAn duan; // Khóa ngoại trỏ đến DuAn
    @Transient
    private UUID nhanvien_id;

    @Transient
    private UUID duan_id;
    private String role; // Ví dụ: Vai trò của nhân viên trong dự án

    private Date ngaythamgia; // Ngày bắt đầu
    private Date ngayketthucduan; // Ngày kết thúc

    @Column(name="trangthai")
    private Integer trangthaidv;
}
