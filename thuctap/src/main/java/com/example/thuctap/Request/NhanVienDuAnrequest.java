package com.example.thuctap.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NhanVienDuAnrequest {
    private UUID nhanvien_id;
    private UUID duan_id;
    private String role; // Ví dụ: Vai trò của nhân viên trong dự án
    private Date ngaythamgia; // Ngày bắt đầu
    private Date ngayketthucduan;
}
