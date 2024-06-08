package com.example.thuctap.NhanVienService;

import com.example.thuctap.Entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();
    Role add(Role role);
    void delete(Long id);
    Role update(Long id, Role role);
}
