package com.example.thuctap.NhanVienService.Impl;

import com.example.thuctap.Entity.UrlApi;
import com.example.thuctap.NhanVienService.UrlApiService;
import com.example.thuctap.Repository.UrlApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrlApiServiceImpl implements UrlApiService {
    @Autowired
    private UrlApiRepository urlApiRepository;


    @Override
    public List<UrlApi> getAllUrlApi() {
        return urlApiRepository.findAll();
    }

    @Override
    public UrlApi addUrl(UrlApi urlApi) {
        return urlApiRepository.save(urlApi);
    }

    @Override
    public void deleteUrl(Long id) {
    urlApiRepository.deleteById(id);
    }

    @Override
    public UrlApi updateUrl(Long id,UrlApi urlApi) {
        Optional<UrlApi> optional = urlApiRepository.findById(id);
            return optional.map(o ->{

            o.setUrl(urlApi.getUrl());
              return   urlApiRepository.save(o);
            }).orElse(null);
    }
}
