package com.example.thuctap.Reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class NhanVienRepon {

    private UUID id;
    private String ma;
    private String ten;
    private String gioitinh;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ngaysinh;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ngaygianhap;
    private String diachi;
    private String sdt;
    private String matkhau;
    private Integer trangthai;

    private UUID idcv;

    private String tencv;
}
