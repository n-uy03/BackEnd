package com.example.thuctap.NhanVienService.Impl;

import com.example.thuctap.Entity.DuAn;
import com.example.thuctap.Entity.NhanVien;
import com.example.thuctap.Entity.NhanVienDuAn;
import com.example.thuctap.NhanVienService.DuAnService;
import com.example.thuctap.Reponse.DuAnNhanVienRepon;
import com.example.thuctap.Reponse.DuAnRepon;
import com.example.thuctap.Reponse.NhanVienDuAnRepon;
import com.example.thuctap.Repository.DuAnRepository;
import com.example.thuctap.Repository.NhanVienDuAnRepository;
import com.example.thuctap.Repository.NhanVienRepository;
import com.example.thuctap.Request.DuAnRequest;
import com.example.thuctap.Request.NhanVienDuAnrequest;
import jakarta.persistence.Access;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DuAnServiceImpl implements DuAnService {
    @Autowired
    private DuAnRepository duAnRepository;
    @Autowired
    private NhanVienDuAnRepository nhanVienDuAnRepository;
    @Autowired
    private  NhanVienRepository nhanVienRepository;


    @Override
    public Page<DuAn> searchduan(DuAnRequest duAnRequest) {
        Specification<DuAn> specification = new Specification<DuAn>() {
            @Override
            public Predicate toPredicate(Root<DuAn> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();

                if (duAnRequest.getTenduan() != null && !duAnRequest.getTenduan().isEmpty()) {
                    predicate.add(criteriaBuilder.like(root.get("tenduan"), "%" + duAnRequest.getTenduan().toLowerCase() + "%"));
                }
                if (duAnRequest.getTrangthai() != null ) {
                    predicate.add(criteriaBuilder.equal(root.get("trangthai"), duAnRequest.getTrangthai()));
                }

                return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
            }

        };
        Pageable pageable = PageRequest.of(duAnRequest.getPage(), duAnRequest.getSize());

        return duAnRepository.findAll(specification, pageable);
    }

    @Override
    public DuAn add(DuAn duAn) {
        return duAnRepository.save(duAn);
    }

    @Override
    public void delete(UUID id) {
        duAnRepository.deleteById(id);
    }

    @Override
    public DuAn detail(UUID id) {
        return duAnRepository.findById(id).orElse(null);
    }

    @Override
    public DuAn update(UUID id, DuAn duAn) {
        Optional<DuAn> optional = duAnRepository.findById(id);
        return optional.map(o -> {
            LocalDate today = LocalDate.now();
            Date sqlDateToday = Date.valueOf(today);
            o.setMaduan(duAn.getMaduan());
            o.setTenduan(duAn.getTenduan());
            o.setStartDate(duAn.getStartDate());

            // Kiểm tra trạng thái bằng số nguyên thay vì chuỗi
            if (duAn.getTrangthai() == 0) { // "kết thúc"
                o.setEndDate(Date.valueOf(LocalDate.now()));
            } else if (duAn.getTrangthai() == 1) { // "hoạt động"
                o.setEndDate(null);
            }
            o.setTrangthai(duAn.getTrangthai());
            return duAnRepository.save(o);
        }).orElse(null);

    }

    @Override
    public List<DuAnNhanVienRepon> getDuAnId(UUID duan_id) {
        Optional<DuAn> duAn = duAnRepository.findById(duan_id);
        if (!duAn.isPresent()) {
            throw new RuntimeException("Nhân viên không tồn tại.");
        }

        List<NhanVienDuAn> nhanVienDuAns = nhanVienDuAnRepository.findByDuanId(duan_id);

        return nhanVienDuAns.stream()
                .map(nvda -> {
                    DuAnNhanVienRepon repon = new DuAnNhanVienRepon();
                    repon.setId(nvda.getId()); // ID của mối quan hệ
                    repon.setNgaythamgia(nvda.getNgaythamgia());
                    repon.setNgayketthucduan(nvda.getNgayketthucduan());
                    repon.setRole(nvda.getRole());

                    if (nvda.getNhanvien() != null) {
                        repon.setTen(nvda.getNhanvien().getTen());
                    }
                    if (nvda.getNhanvien() != null) {
                        repon.setSdt(nvda.getNhanvien().getSdt());
                    }
                    if (nvda.getNhanvien() != null) {
                        repon.setGioitinh(nvda.getNhanvien().getGioitinh());
                    }
                    if (nvda.getNhanvien() != null) {
                        repon.setNgaysinh(nvda.getNhanvien().getNgaysinh());
                    }
                    repon.setTrangthaidv(nvda.getTrangthaidv());
                    return repon;
                })
                .collect(Collectors.toList()); // Chuyển đổi thành danh sách
    }


    @Override
    public NhanVienDuAn addDuantonhanvien(NhanVienDuAnrequest nhanVienDuAnrequest) {
        Optional<NhanVien> nhanVien = nhanVienRepository.findById(nhanVienDuAnrequest.getNhanvien_id());
        if (!nhanVien.isPresent()) {
            throw new RuntimeException("Nhân viên không tồn tại.");
        }
        Optional<DuAn> duAn = duAnRepository.findById(nhanVienDuAnrequest.getDuan_id());
        if (!duAn.isPresent()) {
            throw new RuntimeException("dự an không tồn tại");
        }

        NhanVienDuAn nhanVienDuAn = new NhanVienDuAn();
        nhanVienDuAn.setNhanvien(nhanVien.get());
        nhanVienDuAn.setDuan(duAn.get());
        nhanVienDuAn.setNgaythamgia(nhanVienDuAnrequest.getNgaythamgia());
        nhanVienDuAn.setNgayketthucduan(nhanVienDuAnrequest.getNgayketthucduan());
        nhanVienDuAn.setRole(nhanVienDuAnrequest.getRole());
        return nhanVienDuAnRepository.save(nhanVienDuAn);
    }


}
