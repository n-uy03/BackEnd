package com.example.thuctap.NhanVienService;

import com.example.thuctap.Entity.DuAn;
import com.example.thuctap.Entity.NhanVienDuAn;
import com.example.thuctap.Reponse.DuAnNhanVienRepon;
import com.example.thuctap.Request.DuAnRequest;
import com.example.thuctap.Request.NhanVienDuAnrequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface DuAnService {
//        List<DuAnRepon> getAll();

    Page<DuAn> searchduan(DuAnRequest duAnRequest);
    DuAn add(DuAn duAn);
    void delete(UUID id);
    DuAn detail(UUID id);
    DuAn update(UUID id, DuAn duAn);

    List<DuAnNhanVienRepon> getDuAnId(UUID duan_id);

    NhanVienDuAn addDuantonhanvien(NhanVienDuAnrequest nhanVienDuAnrequest);

}
