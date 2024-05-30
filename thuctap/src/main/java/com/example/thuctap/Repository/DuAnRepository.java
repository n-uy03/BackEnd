package com.example.thuctap.Repository;

import com.example.thuctap.Entity.DuAn;
import com.example.thuctap.Entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DuAnRepository extends JpaRepository<DuAn, UUID>, JpaSpecificationExecutor<DuAn> {


}
