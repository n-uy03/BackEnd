package com.example.thuctap.NhanVienService;

import com.example.thuctap.Entity.UrlApi;

import java.util.List;

public interface UrlApiService {
    List<UrlApi> getAllUrlApi();
    UrlApi addUrl(UrlApi urlApi);
    void deleteUrl(Long id);
    UrlApi updateUrl(Long id, UrlApi urlApi);
}
