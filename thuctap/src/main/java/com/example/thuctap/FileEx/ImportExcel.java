package com.example.thuctap.FileEx;

import com.example.thuctap.Entity.NhanVien;
import com.example.thuctap.Entity.ThongTinCaNhan;
import com.example.thuctap.Request.CreateNhanVienEx;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImportExcel {
    public static String TYPE ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    static String[] HEADERs = { "Ma", "Tên","Giới tính","Ngày sinh", "Ngày gia nhập", "Địa chỉ","Số điện thoại","Mật khẩu", "Chúc vụ","Số cccd", "Ngày cấp" };
    static String SHEET ="nhanvien";

    public static boolean hasExcelformat(MultipartFile  file){
        if(!TYPE.equals(file.getContentType())){
            return false;
        }
        return true;
    }
    public static List<CreateNhanVienEx> excelToStuList(InputStream is) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<CreateNhanVienEx> nhanVienList = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    // Bỏ qua hàng tiêu đề
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                CreateNhanVienEx nv = new CreateNhanVienEx();
                int cellIdx = 0;

                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {

                        case 0:
                            nv.setMa(currentCell.getStringCellValue());
                            break;
                        case 1:
                            nv.setTen(currentCell.getStringCellValue());
                            break;
                        case 2:
                            nv.setGioitinh(currentCell.getStringCellValue());
                            break;
                        case 3:
                            Date ngaySinh = sdf.parse(currentCell.getStringCellValue());
                            nv.setNgaysinh(ngaySinh);
                            break;
                        case 4:
                            Date ngayGiaNhap = sdf.parse(currentCell.getStringCellValue());
                            nv.setNgaygianhap(ngayGiaNhap);
                            break;
                        case 5:
                            nv.setDiachi(currentCell.getStringCellValue());
                            break;
                        case 6:
                            nv.setSdt(currentCell.getStringCellValue());
                            break;
                        case 7:
                            nv.setMatkhau(currentCell.getStringCellValue());
                            break;
                        case 8:
                            nv.setTrangthai(currentCell.getStringCellValue().equals("Nghỉ Làm") ? 0 : 1);
                            break;
                        case 9:
                            String uuidString = currentCell.getStringCellValue();
                            nv.setIdcv(UUID.fromString(uuidString));
                            break;
                        case 10:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                nv.setSocmnd(String.valueOf((int) currentCell.getNumericCellValue()));
                            } else {
                                nv.setSocmnd(currentCell.getStringCellValue());
                            }
                            break;
                        case 11:
                            Date ngayCapCMND = sdf.parse(currentCell.getStringCellValue());
                            nv.setNgaycapcmnd(ngayCapCMND);
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                nhanVienList.add(nv);
            }
            workbook.close();
            return nhanVienList;
        } catch (Exception e) {
            // Xử lý ngoại lệ và thông báo lỗi nếu có
            System.out.println("Lỗi: " + e.getMessage());
            return null;
        }
    }


}
