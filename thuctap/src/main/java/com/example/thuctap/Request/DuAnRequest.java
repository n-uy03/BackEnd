package com.example.thuctap.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DuAnRequest {
    private UUID id;
    private String maduan;
    private String tenduan;
    private Integer trangthai;
    private int page;
    private int size;
}
