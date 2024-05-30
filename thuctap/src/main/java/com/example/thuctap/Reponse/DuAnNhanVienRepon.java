package com.example.thuctap.Reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DuAnNhanVienRepon {

    private UUID id;
    private String ten;
    private String gioitinh;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ngaysinh;
    private String sdt;
    private Date ngaythamgia;
    private Date ngayketthucduan;
    private String role; // Ví dụ: Vai trò của nhân viên trong dự án
    private Integer trangthaidv;



}
