package com.example.thuctap.Repository;

import com.example.thuctap.Entity.NhanVienDuAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface NhanVienDuAnRepository extends JpaRepository<NhanVienDuAn, UUID> {
    List<NhanVienDuAn> findByNhanvienId(UUID nhanvien_id);
   List<NhanVienDuAn> findByDuanId(UUID duan_id);
}
