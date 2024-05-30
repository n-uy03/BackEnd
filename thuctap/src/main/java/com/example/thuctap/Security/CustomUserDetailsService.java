package com.example.thuctap.Security;


import com.example.thuctap.Entity.NhanVien;
import com.example.thuctap.Repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Override
    public UserDetails loadUserByUsername(String ma) throws UsernameNotFoundException {
        NhanVien nhanVien = nhanVienRepository.findByMa(ma);
        if (nhanVien == null) {
            throw new UsernameNotFoundException("NhanVien not found with ma: " + ma);
        }

        return new org.springframework.security.core.userdetails.User(
                nhanVien.getMa(),
                nhanVien.getMatkhau(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + nhanVien.getRole()))
        );

    }
}
