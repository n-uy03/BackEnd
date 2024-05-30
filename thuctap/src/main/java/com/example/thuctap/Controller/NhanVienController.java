package com.example.thuctap.Controller;

import com.example.thuctap.Entity.ChucVu;
import com.example.thuctap.Entity.NhanVien;
import com.example.thuctap.Entity.NhanVienDuAn;
import com.example.thuctap.FileEx.ExportExcel;
import com.example.thuctap.NhanVienService.ChucVuService;
import com.example.thuctap.NhanVienService.NhanVienDuAnService;
import com.example.thuctap.NhanVienService.NhanVienService;
import com.example.thuctap.Reponse.NhanVienDuAnRepon;
import com.example.thuctap.Reponse.NhanVienRepon;
import com.example.thuctap.Request.NhanVienDuAnrequest;
import com.example.thuctap.Request.NhanVienRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private ChucVuService chucVuService;
    @Autowired
    private NhanVienDuAnService nhanVienDuAnService;


//    @GetMapping("/getAll")
//    List<NhanVien> getAll() {
//        return nhanVienService.getAll();
//    }

    @GetMapping("/export-to-excel")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerkey = "Content-Disposition";
        String headerValue = "attachment; filename=danh_sach_nhan_vien" + currentDateTime + ".xlsx";
        response.setHeader(headerkey, headerValue);

        List<NhanVien> nhanVienList = nhanVienService.getAll();
        ExportExcel excel = new ExportExcel(nhanVienList);
        excel.ExcelFile(response);
    }



    @GetMapping("/getListcv")
    List<ChucVu> getList(){
        return chucVuService.getList();
}
    @GetMapping("/getnhanvien")
    public Page<NhanVien> Page(@RequestParam(value = "page", defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 5);
        return nhanVienService.findAll(pageable);
    }

    @PostMapping("/save-nv")

    public ResponseEntity<Map<String, Object>> add(@Valid @RequestBody NhanVien nhanVien) {
        Map<String, Object> response = new HashMap<>();

        try {
            NhanVien addedNhanVien = nhanVienService.add(nhanVien);

            response.put("status", "success");
            response.put("message", "Thêm nhân viên thành công.");
            response.put("nhanVien", addedNhanVien);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Đã xảy ra lỗi khi thêm nhân viên.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
//        // Lấy mã người dùng từ token hoặc session
//        String currentUserId = getCurrentUserId(); // Thay thế getCurrentUserId() bằng phương thức lấy mã người dùng hiện tại
//
//        // Kiểm tra quyền truy cập của người dùng
//        boolean isAuthorized = authorizationService.isUserAuthorizedToDelete(currentUserId, id);
//
//        // Nếu người dùng không có quyền xóa bản ghi, trả về lỗi 403 - Forbidden
//        if (!isAuthorized) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xóa bản ghi này.");
//        }

        // Ngược lại, thực hiện xóa bản ghi
        if (nhanVienService.delete(id)) {
            return ResponseEntity.ok("Xóa thành công");
        } else {
            return ResponseEntity.ok("Xóa thất bại");
        }
    }

//    // Phương thức giả định để lấy mã người dùng hiện tại từ token hoặc session
//    private String getCurrentUserId() {
//        // Thực hiện logic để lấy mã người dùng từ token hoặc session
//        // Đây chỉ là ví dụ giả định, bạn cần thay thế bằng cách lấy mã người dùng thực tế
//        return "Nv03";
//    }

    @PutMapping("/update-nv/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable("id") UUID id,
            @Valid @RequestBody NhanVien nhanVien
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            NhanVien updatedNhanVien = nhanVienService.update(nhanVien, id);

            if (updatedNhanVien != null) {
                response.put("status", "success");
                response.put("message", "Cập nhật nhân viên thành công.");
                response.put("nhanVien", updatedNhanVien);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.put("status", "error");
                response.put("message", "Nhân viên với ID này không tồn tại.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (IllegalArgumentException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Đã xảy ra lỗi khi cập nhật nhân viên.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/detail-nv/{id}")
    public ResponseEntity<NhanVien> detail(@PathVariable UUID id) {
        NhanVien nhanVien = nhanVienService.detail(id);
        if (nhanVien != null) {
            return new ResponseEntity<>(nhanVien, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/search-nv")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<NhanVien> search_nv(@RequestBody NhanVienRequest nhanVienRequest) {
        return nhanVienService.search(nhanVienRequest);
    }

    @PostMapping("/search-jdbc")
    public ResponseEntity<List<NhanVienRepon>> searchNhanVien(@RequestBody NhanVienRequest nhanVienRequest) {

        List<NhanVienRepon> nhanVienList = nhanVienService.serach_jdbc(nhanVienRequest);


        return new ResponseEntity<>(nhanVienList, HttpStatus.OK);
    }

    @PostMapping("/search-pr")
    public ResponseEntity<List<NhanVienRepon>> searchNhanVienPr(
            @RequestBody NhanVienRequest nhanVienRequest
    ) {

        List<NhanVienRepon> resultList = nhanVienService.search_nvpr(nhanVienRequest);

        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{nhanvien_id}/Duanidnhanvien")
    public ResponseEntity<List<NhanVienDuAnRepon>> getNhanVienid(@PathVariable("nhanvien_id") UUID nhanvien_id) {
        List<NhanVienDuAnRepon> duAnList = nhanVienService.getNhanVienid(nhanvien_id);
        return ResponseEntity.ok(duAnList);
    }



    @PostMapping("/{nhanvien_id}/duan/{duan_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NhanVienDuAn> addNhanVienToDuAn(
            @PathVariable("nhanvien_id") UUID nhanvien_id,
            @PathVariable("duan_id") UUID duan_id,
            @RequestBody NhanVienDuAnrequest request
    ) {
        // Đảm bảo request có dữ liệu chính xác
        request.setNhanvien_id(nhanvien_id);  // Đảm bảo ID của nhân viên trong request
        request.setDuan_id(duan_id);          // Đảm bảo ID của dự án trong request

        // Gọi service để thêm nhân viên vào dự án
        NhanVienDuAn newNhanVienDuAn = nhanVienService.addnhanvientoduan(request);

        return ResponseEntity.ok(newNhanVienDuAn);
    }

    @PostMapping("/excel/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file!");
        }

        try {
            List<NhanVien> nhanVienList = nhanVienService.save(file);
            return ResponseEntity.ok().body("Uploaded successfully " + nhanVienList.size() + " records.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }
}
