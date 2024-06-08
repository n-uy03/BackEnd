package com.example.thuctap.NhanVienService.Impl;

import com.example.thuctap.Entity.NhanVienRole;
import com.example.thuctap.NhanVienService.NhanVienRoleService;
import com.example.thuctap.Repository.NhanVienRepository;
import com.example.thuctap.Repository.NhanVienRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienRoleImpl implements NhanVienRoleService {
    @Autowired
    private NhanVienRoleRepository nhanVienRoleRepository;
    @Override
    public List<NhanVienRole> getAllnvRole() {
        return nhanVienRoleRepository.findAll();
    }

    @Override
    public NhanVienRole add(NhanVienRole nhanVienRole) {
        return nhanVienRoleRepository.save(nhanVienRole);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public NhanVienRole update(Long id, NhanVienRole nhanVienRole) {
        return null;
    }
}
