package com.example.thuctap.Reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DuAnRepon {
    private UUID id;
    private String maduan;
    private String tenduan;
    private Date startDate;
    private Date endDate;
    private Integer trangthai;
    private String role;
    private List<DuAnNhanVienRepon> listds;
}
