package com.example.thuctap.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "duan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuAn {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull(message = "mã dự án không được để trống")
    @Size(min = 1, message = "mã không được để trống")
    private String maduan;
    @NotNull(message = "Tên dự án không được để trống")
    @Size(min = 1, message = "Tên dự án không được để trống")
    private String tenduan;
    private Date startDate;
    private Date endDate;
    private Integer trangthai;


    @OneToMany(mappedBy = "duan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<NhanVienDuAn> nhanVienDuAns;

}
