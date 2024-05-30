package com.example.thuctap.Reponse;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NhanVienDuAnRepon {

    private UUID id;
    private Date ngaythamgia; // Ngày bắt đầu
    private Date ngayketthucduan;
    private String tenduan;
    private String role; // Ví dụ: Vai trò của nhân viên trong dự án
    private Integer trangthaidv;
}
