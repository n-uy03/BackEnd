package com.example.thuctap.Repository;

import com.example.thuctap.Entity.UrlApiRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlApiRoleRepository extends JpaRepository<UrlApiRole, Long > {

}
