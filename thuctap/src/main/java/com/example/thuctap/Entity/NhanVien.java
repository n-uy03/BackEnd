package com.example.thuctap.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "nhanvien")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "mã không được để trống")
    @Size(min = 1, message = "mã không được để trống")
    private String ma;
    @NotNull(message = "Tên không được để trống")
    @Size(min = 1, message = "Tên không được để trống")
    private String ten;
    private String gioitinh;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ngaysinh;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ngaygianhap;
    @NotNull(message = "Địa chỉ không được để trống")
    @Size(min = 1, message = "Địa chỉ không được để trống")
    private String diachi;
    @NotNull(message = "sdt không được để trống")
    @Size(min = 1, message = "sdt không được để trống")
    @Pattern(regexp = "^\\+?[0-9]+$", message = "Số điện thoại phải là số")
    private String sdt;
    @NotNull(message = "mật khẩu không được để trống")
    @Size(min = 1, message = "mật khẩu không được để trống")
    private String matkhau;
    private String role;
    private Integer trangthai;

    @Transient
    private UUID idcv;

    @ManyToOne
    @JoinColumn(name = "idcv")
    private ChucVu chucvu;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thongtin_nv_id", referencedColumnName = "id")
    private ThongTinCaNhan thongTinCaNhan;


    @OneToMany(mappedBy = "nhanvien", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<NhanVienDuAn> nhanVienDuAns;

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
