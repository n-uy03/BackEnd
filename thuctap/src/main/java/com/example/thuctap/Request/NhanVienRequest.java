package com.example.thuctap.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class NhanVienRequest {

    private UUID id;
    private String ma;
    private String ten;
    private Date ngaygianhap;
    private String sdt;
    private Integer trangthai;

    private String tencv;
    @JsonProperty("ngaybatdau")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ngaybatdau;
    @JsonProperty("ngayketthuc")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ngayketthuc;
    private int  page;
    private int size;


    public Date getNgaygianhap() {
        return ngaygianhap;
    }


}
