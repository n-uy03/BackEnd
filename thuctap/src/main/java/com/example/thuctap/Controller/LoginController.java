package com.example.thuctap.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
    @GetMapping("/admin/test")
    public String adminTest() {
        return "Admin can access this";
    }

    @GetMapping("/nhanvien/test")
    public String userTest() {
        return "Both Admin and User can access this";
    }
}
