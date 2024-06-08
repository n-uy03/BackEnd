package com.example.thuctap.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanVienRoleRequest {

    private UUID nhanvien_id;
    private Long role_id;
    private String name;///hieudan
}
