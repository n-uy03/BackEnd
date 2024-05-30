package com.example.thuctap.Controller;

import com.example.thuctap.Entity.DuAn;
import com.example.thuctap.Entity.NhanVien;
import com.example.thuctap.Entity.NhanVienDuAn;
import com.example.thuctap.NhanVienService.DuAnService;
import com.example.thuctap.NhanVienService.NhanVienDuAnService;
import com.example.thuctap.Reponse.DuAnNhanVienRepon;
import com.example.thuctap.Reponse.DuAnRepon;
import com.example.thuctap.Reponse.NhanVienDuAnRepon;
import com.example.thuctap.Repository.NhanVienDuAnRepository;
import com.example.thuctap.Request.DuAnRequest;
import com.example.thuctap.Request.NhanVienDuAnrequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/duan")
public class DuAnController {
    @Autowired
    private DuAnService duAnService;
    @Autowired
    private NhanVienDuAnService nhanVienDuAnService;


//    @GetMapping("/all")
//    public ResponseEntity<List<DuAnRepon>> getAllProjects() {
//        List<DuAnRepon> projectList = duAnService.getAll();
//        return ResponseEntity.ok(projectList); // Trả về danh sách dự án với vai trò
//    }



    @PostMapping("/searchDuan")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<DuAn> searchduan(@RequestBody DuAnRequest duAnRequest){
    return duAnService.searchduan(duAnRequest);
    }


    @PostMapping("/save-duan")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DuAn>saveduan(@Valid @RequestBody DuAn duAn){
        return ResponseEntity.ok(duAnService.add(duAn));
    }

    @GetMapping("/detail-duan/{id}")
    public ResponseEntity<DuAn> detail(@PathVariable UUID id) {
        DuAn duAn = duAnService.detail(id);
        if (duAn != null) {
            return new ResponseEntity<>(duAn, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-duan/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteduan(@PathVariable UUID id){
        duAnService.delete(id);
    }

    @PutMapping("/update-duan/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DuAn> updateduan(@PathVariable UUID id, @RequestBody DuAn duAn){
        DuAn updatedDuAn = duAnService.update(id, duAn);
        if (updatedDuAn != null) {
            return ResponseEntity.ok(updatedDuAn);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{duan_id}/nhanvien/{nhanvien_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NhanVienDuAn> addNhanVienToDuAn(
            @PathVariable("duan_id") UUID duan_id,
            @PathVariable("nhanvien_id") UUID nhanvien_id,
            @RequestBody NhanVienDuAnrequest request
    ) {
        request.setDuan_id(duan_id);
        request.setNhanvien_id(nhanvien_id);
        NhanVienDuAn newNhanVienDuAn = duAnService.addDuantonhanvien(request);
        return ResponseEntity.ok(newNhanVienDuAn);
    }

    @GetMapping("/{duan_id}/nhanvien")
    public ResponseEntity<List<DuAnNhanVienRepon>> getDuAnId(@PathVariable("duan_id") UUID duan_id) {
        List<DuAnNhanVienRepon> duAnList = duAnService.getDuAnId(duan_id);
        return ResponseEntity.ok(duAnList);
    }

}
