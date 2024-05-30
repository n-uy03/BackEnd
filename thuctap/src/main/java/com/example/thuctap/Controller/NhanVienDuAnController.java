package com.example.thuctap.Controller;

import com.example.thuctap.Entity.NhanVienDuAn;
import com.example.thuctap.NhanVienService.NhanVienDuAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/nhanvienduan")

public class NhanVienDuAnController {
    @Autowired
    private NhanVienDuAnService nhanVienDuAnService;

    @PostMapping("end/{id}")
    public ResponseEntity<NhanVienDuAn> updateNhanVienDuAn(
            @PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(nhanVienDuAnService.endNhanVienDuAn(id));
    }
    @PostMapping("start/{id}")
    public ResponseEntity<NhanVienDuAn> updateStartNhanVienDuAn(
            @PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(nhanVienDuAnService.startNhanVienDuAn(id));
    }

    @DeleteMapping("/deletenhanvientoduan/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletenhanvientoduan(@PathVariable UUID id){
        nhanVienDuAnService.deletenhanvientoduan(id);
    }
}
