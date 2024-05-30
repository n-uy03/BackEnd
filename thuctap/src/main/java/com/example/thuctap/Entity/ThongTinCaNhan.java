package com.example.thuctap.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "thongtincanhan", uniqueConstraints = {@UniqueConstraint(columnNames = "socmnd")})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThongTinCaNhan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "socmnd", unique = true, nullable = false)
    private String socmnd;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ngaycapcmnd;


}
