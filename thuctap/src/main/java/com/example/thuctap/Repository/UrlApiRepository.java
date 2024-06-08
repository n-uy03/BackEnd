package com.example.thuctap.Repository;

import com.example.thuctap.Entity.UrlApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UrlApiRepository extends JpaRepository<UrlApi, Long> {
    @Query(value = "select r.url from url_api r\n" +
            "join url_api_role ro on ro.url_api_id = r.id\n" +
            "join role rl on rl.id = ro.role_id\n" +
            "where rl.name in :roles", nativeQuery = true)
    List<String> findByUrlByRoleName(Collection<String> roles);
}
