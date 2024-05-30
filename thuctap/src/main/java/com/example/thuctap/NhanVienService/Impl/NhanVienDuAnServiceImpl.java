package com.example.thuctap.NhanVienService.Impl;

import com.example.thuctap.Entity.DuAn;
import com.example.thuctap.Entity.NhanVien;
import com.example.thuctap.Entity.NhanVienDuAn;
import com.example.thuctap.NhanVienService.NhanVienDuAnService;
import com.example.thuctap.Repository.NhanVienDuAnRepository;
import com.example.thuctap.Repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class NhanVienDuAnServiceImpl implements NhanVienDuAnService {

    @Autowired
    private NhanVienDuAnRepository nhanVienDuAnRepository;



    @Override
    public NhanVienDuAn endNhanVienDuAn(UUID id) {
        Optional<NhanVienDuAn> optional = nhanVienDuAnRepository.findById(id);
        if (optional.isPresent()) {
            LocalDate today = LocalDate.now();
            Date sqlDateToday = Date.valueOf(today);
            NhanVienDuAn nhanVienDuAn = optional.get();
            nhanVienDuAn.setNgayketthucduan(sqlDateToday);
            nhanVienDuAn.setTrangthaidv(0);  // Chỉnh trạng thái thành 0
            nhanVienDuAnRepository.save(nhanVienDuAn);  // Lưu thay đổi vào cơ sở dữ liệu
            return nhanVienDuAn;  // Trả về đối tượng đã được thay đổi
        } else {
            return null;  // Nếu không tìm thấy đối tượng, trả về null
        }
    }

    @Override
    public NhanVienDuAn startNhanVienDuAn(UUID id) {
        Optional<NhanVienDuAn> optional = nhanVienDuAnRepository.findById(id);
        if (optional.isPresent()) {
            LocalDate today = LocalDate.now();
            Date sqlDateToday = Date.valueOf(today);
            NhanVienDuAn nhanVienDuAn = optional.get();
            nhanVienDuAn.setNgayketthucduan(null);
            nhanVienDuAn.setTrangthaidv(1);  // Chỉnh trạng thái thành 0
            nhanVienDuAnRepository.save(nhanVienDuAn);  // Lưu thay đổi vào cơ sở dữ liệu
            return nhanVienDuAn;  // Trả về đối tượng đã được thay đổi
        } else {
            return null;  // Nếu không tìm thấy đối tượng, trả về null
        }
    }

    @Override
    public void deletenhanvientoduan(UUID id) {
        nhanVienDuAnRepository.deleteById(id);
    }


}
