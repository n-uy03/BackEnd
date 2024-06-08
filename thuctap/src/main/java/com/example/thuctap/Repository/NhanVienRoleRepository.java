package com.example.thuctap.Repository;

import com.example.thuctap.Entity.NhanVienRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRoleRepository extends JpaRepository<NhanVienRole, Long> {
}
