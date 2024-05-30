package com.example.thuctap.NhanVienService.Impl;


import com.example.thuctap.Entity.*;
import com.example.thuctap.FileEx.ImportExcel;
import com.example.thuctap.NhanVienService.NhanVienService;
import com.example.thuctap.Reponse.NhanVienDuAnRepon;
import com.example.thuctap.Reponse.NhanVienRepon;
import com.example.thuctap.Repository.*;
import com.example.thuctap.Request.CreateNhanVienEx;
import com.example.thuctap.Request.NhanVienDuAnrequest;
import com.example.thuctap.Request.NhanVienRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import java.util.stream.Collectors;


@Service
public class NhanVienServiceImpl implements NhanVienService {
    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private ChucVuRepository chucVuRepository;
    @Autowired
    private ThongTinCaNhanRepository thongTinCaNhanRepository;
    @Autowired
    private NhanVienDuAnRepository nhanVienDuAnRepository;
    @Autowired
    private DuAnRepository duAnRepository;

    private  ImportExcel importExcel;

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;


    public NhanVienServiceImpl( JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.importExcel = importExcel;
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;

    }

    @Override
    public List<NhanVien> getAll() {
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        return nhanVienList;

    }

    @Override
    public Page<NhanVien> findAll(Pageable pageable) {
        return nhanVienRepository.findAll(pageable);
    }

    @Override
    public NhanVien add(NhanVien nhanVien) {

        String socmnd = nhanVien.getThongTinCaNhan().getSocmnd();
        Optional<ThongTinCaNhan> existing = thongTinCaNhanRepository.findBySocmnd(socmnd);

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Số CCCD không được trùng với nhân viên khác. Vui lòng kiểm tra lại!");
        }

        ChucVu chucVu = nhanVien.getChucvu(); // Lấy đối tượng ChucVu
        UUID idcv = (chucVu != null) ? chucVu.getIdcv() : null; // Lấy ID nếu có

        if (idcv == null) {
            throw new IllegalArgumentException("Chức vụ không có ID.");
        }

        Optional<ChucVu> chucVuOptional = chucVuRepository.findById(idcv);
        if (!chucVuOptional.isPresent()) {
            throw new EntityNotFoundException("Chức vụ gửi lên không tồn tại!");
        }

        nhanVien.setChucvu(chucVuOptional.get());

        return nhanVienRepository.save(nhanVien);
    }


    @Override
    public Boolean delete(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("chưa truyền vào id");
        }

        if (!nhanVienRepository.existsById(id)) {
            throw new IllegalArgumentException("Nhân viên không tồn tại.");
        }

        Optional<NhanVien> optional = nhanVienRepository.findById(id);
        if (optional.isPresent()) {
            NhanVien nhanVien = optional.get();
            nhanVienRepository.delete(nhanVien);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public NhanVien update(NhanVien nhanVien, UUID id) {

        Optional<NhanVien> optional = nhanVienRepository.findById(id);

        return optional.map(o -> {
            o.setMa(nhanVien.getMa());
            o.setTen(nhanVien.getTen());
            o.setGioitinh(nhanVien.getGioitinh());
            o.setNgaysinh(nhanVien.getNgaysinh());
            o.setNgaygianhap(nhanVien.getNgaygianhap());
            o.setDiachi(nhanVien.getDiachi());
            o.setSdt(nhanVien.getSdt());
            o.setMatkhau(nhanVien.getMatkhau());
            o.setTrangthai(nhanVien.getTrangthai());
            o.setChucvu(nhanVien.getChucvu());
            o.setThongTinCaNhan(nhanVien.getThongTinCaNhan());

            return nhanVienRepository.save(o);
        }).orElse(null);
    }

    @Override
    public NhanVien detail(UUID id) {
        return nhanVienRepository.findById(id).orElse(null);
    }

    @Override
    public Page<NhanVien> search(NhanVienRequest nhanVienRequest) {
        Specification<NhanVien> specification = new Specification<NhanVien>() {
            @Override
            public Predicate toPredicate(Root<NhanVien> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) { // dk tìm kiếm
                List<Predicate> predicates = new ArrayList<>();
                String searchTerm = nhanVienRequest.getTen(); // Hoặc một trường tương ứng
                if (searchTerm != null && !searchTerm.isEmpty()) {

                    if (searchTerm.matches("^[0-9]+$")) {
                        predicates.add(criteriaBuilder.like(root.get("sdt"), "%" + searchTerm + "%"));
                    } else { // Ngược lại, coi là tên
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("ten")), "%" + searchTerm.toLowerCase() + "%"));
                    }
                }
                if (nhanVienRequest.getTencv() != null && !nhanVienRequest.getTencv().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("chucvu").get("tencv")), "%" + nhanVienRequest.getTencv().toLowerCase() + "%"));
                }

                if (nhanVienRequest.getNgaybatdau() != null && nhanVienRequest.getNgayketthuc() != null) {
                    predicates.add(criteriaBuilder.between(root.get("ngaygianhap"), nhanVienRequest.getNgaybatdau(), nhanVienRequest.getNgayketthuc()));
                }
                if (nhanVienRequest.getNgaybatdau() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ngaygianhap"), nhanVienRequest.getNgaybatdau()));
                }
                if (nhanVienRequest.getNgayketthuc() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("ngaygianhap"), nhanVienRequest.getNgayketthuc()));
                }
                query.orderBy(criteriaBuilder.desc(root.get("ngaygianhap")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };

        Pageable pageable = PageRequest.of(nhanVienRequest.getPage(), nhanVienRequest.getSize());
        return nhanVienRepository.findAll(specification, pageable);
    }

    @Override
    public List<NhanVienRepon> serach_jdbc(NhanVienRequest nhanVienRequest) {

        int page = nhanVienRequest.getPage();
        int size = nhanVienRequest.getSize();


        int offset = (page - 1) * size;

        // Build the SQL query with dynamic filtering conditions
        StringBuilder sqlstmt = new StringBuilder(
                "SELECT nv.Id, nv.Ma, nv.Ten, nv.GioiTinh, nv.NgaySinh, nv.NgayGiaNhap, nv.DiaChi, nv.Sdt, nv.MatKhau, nv.TrangThai, " +
                        "cv.Id AS idcv, cv.Ten AS tencv " +
                        "FROM NHANVIEN nv " +
                        "JOIN CHUCVU cv ON nv.idCv = cv.id " +
                        "WHERE 1=1 "
        );

        // List of parameters for the query
        List<Object> params = new ArrayList<>();

        // Add additional filtering conditions
        if (nhanVienRequest.getTen() != null && !nhanVienRequest.getTen().isEmpty()) {
            sqlstmt.append(" AND nv.Ten LIKE ?");
            params.add("%" + nhanVienRequest.getTen() + "%");
        }

        if (nhanVienRequest.getSdt() != null && !nhanVienRequest.getSdt().isEmpty()) {
            sqlstmt.append(" AND nv.Sdt = ?");
            params.add(nhanVienRequest.getSdt());
        }

        if (nhanVienRequest.getTencv() != null && !nhanVienRequest.getTencv().isEmpty()) {
            sqlstmt.append(" AND cv.Ten LIKE ?");
            params.add("%" + nhanVienRequest.getTencv() + "%");
        }

        // Add date range filtering for NgayGiaNhap
        if (nhanVienRequest.getNgaybatdau() != null) {
            sqlstmt.append(" AND nv.NgayGiaNhap >= ?");
            params.add(nhanVienRequest.getNgaybatdau());
        }

        if (nhanVienRequest.getNgayketthuc() != null) {
            sqlstmt.append(" AND nv.NgayGiaNhap <= ?");
            params.add(nhanVienRequest.getNgayketthuc());
        }

        // Apply pagination
        sqlstmt.append(" ORDER BY nv.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add(offset);
        params.add(size);

        // Execute the query with the constructed SQL and parameters
        return jdbcTemplate.query(
                sqlstmt.toString(),
                params.toArray(),
                new BeanPropertyRowMapper<>(NhanVienRepon.class)
        );
    }

    @Override
    public List<NhanVienRepon> search_nvpr(NhanVienRequest nhanVienRequest) {

        List<NhanVienRepon> resultList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement statement = connection.prepareCall("{call SPNV(?, ?, ?, ?, ?, ?, ?, ?)}")) {


            statement.setString(1, nhanVienRequest.getMa());
            statement.setString(2, nhanVienRequest.getTen());
            statement.setString(3, nhanVienRequest.getSdt());
            statement.setString(4, nhanVienRequest.getTencv());
            statement.setInt(5, nhanVienRequest.getPage());
            statement.setInt(6, nhanVienRequest.getSize());

            // Thiết lập ngày bắt đầu và ngày kết thúc
            if (nhanVienRequest.getNgaybatdau() != null) {
                statement.setDate(7, new java.sql.Date(nhanVienRequest.getNgaybatdau().getTime()));
            } else {
                statement.setNull(7, java.sql.Types.DATE);
            }

            if (nhanVienRequest.getNgayketthuc() != null) {
                statement.setDate(8, new java.sql.Date(nhanVienRequest.getNgayketthuc().getTime()));
            } else {
                statement.setNull(8, java.sql.Types.DATE);
            }

            boolean hasResults = statement.execute(); // Thực hiện stored procedure

            if (hasResults) { // Kiểm tra xem có trả về ResultSet không
                try (ResultSet rs = statement.getResultSet()) {
                    while (rs.next()) {
                        NhanVienRepon nhanVien = new NhanVienRepon();
                        nhanVien.setMa(rs.getString("Ma"));
                        nhanVien.setTen(rs.getString("Ten"));
                        nhanVien.setSdt(rs.getString("Sdt"));
                        nhanVien.setGioitinh(rs.getString("GioiTinh"));
                        nhanVien.setNgaysinh(rs.getDate("NgaySinh"));
                        nhanVien.setNgaygianhap(rs.getDate("NgayGiaNhap"));
                        nhanVien.setDiachi(rs.getString("DiaChi"));
                        nhanVien.setMatkhau(rs.getString("MatKhau"));
                        nhanVien.setTrangthai(rs.getInt("TrangThai"));
                        nhanVien.setTencv(rs.getString("Tencv"));

                        resultList.add(nhanVien);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<NhanVienDuAnRepon> getNhanVienid(UUID nhanvien_id) {
        Optional<NhanVien> nhanVien = nhanVienRepository.findById(nhanvien_id);
        if (!nhanVien.isPresent()) {
            throw new RuntimeException("Nhân viên không tồn tại.");
        }

        // Lấy danh sách dự án mà nhân viên tham gia
        List<NhanVienDuAn> nhanVienDuAns = nhanVienDuAnRepository.findByNhanvienId(nhanvien_id);

        // Chuyển đổi sang danh sách NhanVienDuAnRepon
        return nhanVienDuAns.stream()
                .map(nvda -> {
                    NhanVienDuAnRepon repon = new NhanVienDuAnRepon();
                    repon.setId(nvda.getId()); // ID của mối quan hệ
                    repon.setNgaythamgia(nvda.getNgaythamgia());
                    repon.setNgayketthucduan(nvda.getNgayketthucduan());
                    repon.setRole(nvda.getRole());

                    if (nvda.getDuan() != null) {
                        repon.setTenduan(nvda.getDuan().getTenduan());
                    }
                    repon.setTrangthaidv(nvda.getTrangthaidv());
                    return repon;
                })
                .collect(Collectors.toList()); // Chuyển đổi thành danh sách

    }

    @Override
    public NhanVienDuAn addnhanvientoduan(NhanVienDuAnrequest nhanVienDuAnrequest) {
        Optional<DuAn> duAn = duAnRepository.findById(nhanVienDuAnrequest.getDuan_id());
        if (!duAn.isPresent()) {
            throw new RuntimeException("Dự án không tồn tại");
        }
        DuAn duAn1 = duAn.get();

        // Kiểm tra nếu dự án đã kết thúc thì không thêm nhân viên
        if (duAn1.getTrangthai() == 0) {
            throw new RuntimeException("Không thể thêm nhân viên vào dự án đã kết thúc.");
        }
        // Kiểm tra nếu NhanVien tồn tại
        Optional<NhanVien> nhanVien = nhanVienRepository.findById(nhanVienDuAnrequest.getNhanvien_id());
        if (!nhanVien.isPresent()) {
            throw new RuntimeException("Nhân viên không tồn tại.");
        }

        // Đặt ngày tham gia là ngày hiện tại
        LocalDate today = LocalDate.now();
        Date sqlDateToday = Date.valueOf(today);

        // Tạo NhanVienDuAn mới
        NhanVienDuAn nhanVienDuAn = new NhanVienDuAn();
        nhanVienDuAn.setDuan(duAn.get());
        nhanVienDuAn.setNhanvien(nhanVien.get());
        nhanVienDuAn.setRole(nhanVienDuAnrequest.getRole());
        nhanVienDuAn.setNgaythamgia(sqlDateToday); // Đặt ngày hiện tại làm ngày tham gia

        nhanVienDuAn.setNgayketthucduan(nhanVienDuAnrequest.getNgayketthucduan());
        nhanVienDuAn.setTrangthaidv(1);

        return nhanVienDuAnRepository.save(nhanVienDuAn);

    }

    @Override
    public List<NhanVien> save(MultipartFile file) throws IOException {
        List<CreateNhanVienEx> nhanVienExList = ImportExcel.excelToStuList(file.getInputStream());
        if (nhanVienExList == null || nhanVienExList.isEmpty()) {
            // Xử lý trường hợp nhanVienExList là null hoặc rỗng
            System.out.println("Danh sách nhanVienExList là null hoặc rỗng");
            return new ArrayList<>(); // hoặc thực hiện các hành động khác tùy thuộc vào yêu cầu của bạn
        }

        List<NhanVien> nhanVienList = new ArrayList<>();
        for (CreateNhanVienEx ex : nhanVienExList) {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMa(ex.getMa());
            nhanVien.setTen(ex.getTen());
            nhanVien.setGioitinh(ex.getGioitinh());
            nhanVien.setNgaysinh(ex.getNgaysinh());
            nhanVien.setNgaygianhap(ex.getNgaygianhap());
            nhanVien.setDiachi(ex.getDiachi());
            nhanVien.setSdt(ex.getSdt());
            nhanVien.setMatkhau(ex.getMatkhau());
            nhanVien.setTrangthai(ex.getTrangthai());

            ChucVu chucVu = new ChucVu();
            chucVu.setIdcv(ex.getIdcv());
            nhanVien.setChucvu(chucVu);

            ThongTinCaNhan thongTinCaNhan = new ThongTinCaNhan();
            thongTinCaNhan.setSocmnd(ex.getSocmnd());
            thongTinCaNhan.setNgaycapcmnd(ex.getNgaycapcmnd());
            nhanVien.setThongTinCaNhan(thongTinCaNhan);

            Optional<ThongTinCaNhan> existing = thongTinCaNhanRepository.findBySocmnd(thongTinCaNhan.getSocmnd());
            if (existing.isPresent()) {
                // Nếu số CCCD đã tồn tại, bỏ qua và không thêm vào danh sách
                System.out.println("Số CCCD đã tồn tại: " + thongTinCaNhan.getSocmnd());
            } else {
                // Nếu số CCCD chưa tồn tại, thêm người dùng vào danh sách
                nhanVienList.add(nhanVien);
            }
        }

        if (!nhanVienList.isEmpty()) {
            nhanVienRepository.saveAll(nhanVienList);
        }

        return nhanVienList;
    }






}





