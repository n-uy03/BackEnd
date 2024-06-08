package com.example.thuctap.Security;


import com.example.thuctap.Entity.NhanVien;
import com.example.thuctap.Entity.NhanVienRole;
import com.example.thuctap.Entity.UrlApiRole;
import com.example.thuctap.Repository.NhanVienRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public UserDetails loadUserByUsername(String ma) throws UsernameNotFoundException {
        NhanVien nhanVien = nhanVienRepository.findByMa(ma);
        if (nhanVien == null) {
            throw new UsernameNotFoundException("Mã không tồn tại: " + ma);
        }
        // Thực hiện truy vấn để tải NhanVienRole
        nhanVien.getNhanVienRoles().size(); // Sử dụng size() để kích hoạt tải dữ liệu
        // Lấy danh sách quyền dựa trên NhanVienRole
        Collection<? extends GrantedAuthority> authorities = getAuthoritiesForUser(nhanVien);
        return new User(nhanVien.getMa(), nhanVien.getMatkhau(), authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthoritiesForUser(NhanVien nhanVien) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Set<NhanVienRole> nhanVienRoles = nhanVien.getNhanVienRoles();
        for (NhanVienRole nhanVienRole : nhanVienRoles) {
            String roleName = nhanVienRole.getRole().getName();
            authorities.add(new SimpleGrantedAuthority(roleName));
        }
        return authorities;
    }
}
