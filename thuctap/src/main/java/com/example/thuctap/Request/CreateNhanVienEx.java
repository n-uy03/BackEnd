package com.example.thuctap.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class CreateNhanVienEx {
    private String ma;
    private String ten;
    private String gioitinh;
    private Date ngaysinh;
    private Date ngaygianhap;
    private String diachi;
    private String sdt;
    private String matkhau;
    private Integer trangthai;
    private UUID idcv;
    private String socmnd;
    private Date ngaycapcmnd;


}
