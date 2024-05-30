package com.example.thuctap.NhanVienService.Impl;

import com.example.thuctap.Entity.ChucVu;
import com.example.thuctap.NhanVienService.ChucVuService;
import com.example.thuctap.Repository.ChucVuRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChucVuServiceImpl implements ChucVuService {
    @Autowired
    private ChucVuRepository chucVuRepository;

    @Override
    public List<ChucVu> getList() {
        return chucVuRepository.findAll();
    }
}
