package com.example.thuctap.NhanVienService.Impl;

import com.example.thuctap.Entity.Role;
import com.example.thuctap.NhanVienService.RoleService;
import com.example.thuctap.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
     @Autowired
     private RoleRepository roleRepository;


     @Override
     public List<Role> getAll() {
          return roleRepository.findAll();
     }

     @Override
     public Role add(Role role) {
          return roleRepository.save(role);
     }

     @Override
     public void delete(Long id) {
          roleRepository.deleteById(id);
     }

     @Override
     public Role update(Long id, Role role) {
          Optional<Role> optional = roleRepository.findById(id);

          return optional.map(o -> {
               o.setName(role.getName());
               return roleRepository.save(o);
          }).orElse(null);
     }
}
