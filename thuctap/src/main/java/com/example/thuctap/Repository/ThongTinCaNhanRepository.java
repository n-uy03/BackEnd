package com.example.thuctap.Repository;

import com.example.thuctap.Entity.ThongTinCaNhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ThongTinCaNhanRepository extends JpaRepository<ThongTinCaNhan, UUID> {
    Optional<ThongTinCaNhan> findBySocmnd(String socmnd);
}
