package com.example.thuctap.NhanVienService;


import com.example.thuctap.Entity.NhanVien;
import com.example.thuctap.Entity.NhanVienDuAn;
import com.example.thuctap.Reponse.NhanVienDuAnRepon;
import com.example.thuctap.Reponse.NhanVienRepon;
import com.example.thuctap.Request.NhanVienDuAnrequest;
import com.example.thuctap.Request.NhanVienRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface NhanVienService {

    List<NhanVien> getAll();
    Page<NhanVien> findAll( Pageable pageable);
    NhanVien add(NhanVien nhanVien);
    Boolean  delete (UUID ID);
    NhanVien update(NhanVien nhanVien, UUID id);
    NhanVien detail(UUID id);
    Page<NhanVien > search(NhanVienRequest nhanVienRequest);
    List<NhanVienRepon> serach_jdbc(NhanVienRequest nhanVienRequest);

    List<NhanVienRepon> search_nvpr(NhanVienRequest nhanVienRequest) ;
    List<NhanVienDuAnRepon> getNhanVienid(UUID nhanvien_id);
    NhanVienDuAn addnhanvientoduan(NhanVienDuAnrequest nhanVienDuAnrequest);

    List<NhanVien> save(MultipartFile file) throws IOException;
}


