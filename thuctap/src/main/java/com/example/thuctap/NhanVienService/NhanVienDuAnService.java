package com.example.thuctap.NhanVienService;

import com.example.thuctap.Entity.NhanVien;
import com.example.thuctap.Entity.NhanVienDuAn;

import java.util.List;
import java.util.UUID;

public interface NhanVienDuAnService {
    NhanVienDuAn endNhanVienDuAn( UUID id);
    NhanVienDuAn startNhanVienDuAn(UUID id);

    void deletenhanvientoduan(UUID id);
}
