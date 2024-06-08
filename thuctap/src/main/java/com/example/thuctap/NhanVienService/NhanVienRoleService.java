package com.example.thuctap.NhanVienService;

import com.example.thuctap.Entity.NhanVienRole;
import com.example.thuctap.Entity.Role;

import java.util.List;

public interface NhanVienRoleService {

    List<NhanVienRole> getAllnvRole();
    NhanVienRole add(NhanVienRole nhanVienRole);
    void delete(Long id);
    NhanVienRole update(Long id, NhanVienRole nhanVienRole);

}
