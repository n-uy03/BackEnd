package com.example.thuctap.FileEx;

import com.example.thuctap.Entity.NhanVien;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExportExcel {
    private List<NhanVien> nhanViens ;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    public ExportExcel(List<NhanVien> nhanViens) {
        this.nhanViens = nhanViens;
        this.workbook = new XSSFWorkbook(); // Khởi tạo workbook ở đây
    }
    private void writeHeader(){
        sheet = workbook.createSheet("NhanVien");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Mã", style);
        createCell(row, 1, "Tên", style);
        createCell(row, 2, "Giới Tính", style);
        createCell(row, 3, "Ngày Sinh", style);
        createCell(row, 4, "Ngày Gia Nhập", style);
        createCell(row, 5, "Địa chỉ", style);
        createCell(row, 6, "Số điện thoại", style);
        createCell(row, 7, "Mật Khẩu", style);
        createCell(row, 8, "Trạng thái", style);
        createCell(row, 9, "Chức Vụ", style);
        createCell(row, 10, "Số Căn Cước Công Dân", style);
        createCell(row, 11, "Ngày cấp căn cước công dân", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Boolean) {
            cell.setCellValue((Boolean) valueOfCell);
        }
        else if (valueOfCell instanceof Double) {
            // Kiểm tra nếu giá trị là số CMND thì chuyển thành chuỗi
            if (columnCount == 11) { // Kiểm tra cột số CMND, trong trường hợp này cột số CMND có index là 11
                cell.setCellValue(String.valueOf(valueOfCell));
            } else {
                cell.setCellValue((Double) valueOfCell);
            }
        }
            else {
            cell.setCellValue(valueOfCell.toString());
        }
        cell.setCellStyle(style);
    }
    private void write(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for(NhanVien record: nhanViens){
            Row row = sheet.createRow(rowCount++);
            int colunmCount = 0;

            createCell(row, colunmCount++, record.getMa(), style );
            createCell(row, colunmCount++, record.getTen(), style );
            createCell(row, colunmCount++, record.getGioitinh(), style );
            createCell(row, colunmCount++, sdf.format(record.getNgaysinh()), style );
            createCell(row, colunmCount++, sdf.format(record.getNgaygianhap()), style );
            createCell(row, colunmCount++, record.getDiachi(), style );
            createCell(row, colunmCount++, record.getSdt(), style );
            createCell(row, colunmCount++, record.getMatkhau(), style );
            createCell(row, colunmCount++, record.getTrangthai()== 0? "Nghỉ làm": "Đang làm", style );
            createCell(row, colunmCount++, record.getChucvu().getIdcv(), style );
            createCell(row, colunmCount++, record.getThongTinCaNhan().getSocmnd(), style );
            createCell(row, colunmCount++, sdf.format(record.getThongTinCaNhan().getNgaycapcmnd()), style );



        }
    }
    public void ExcelFile(HttpServletResponse response) throws IOException{
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
