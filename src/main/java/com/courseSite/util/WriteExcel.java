package com.courseSite.util;

import com.courseSite.pojo.Student;
import org.apache.poi.hssf.usermodel.*;

import java.util.List;

public class WriteExcel {

    public static HSSFWorkbook getWorkBook(String sheetName, List<String> title, List<Student> students){
        //创建一个HSSFWorkbook,对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //在Excel中创建一个sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //在sheet中添加第0行
        HSSFRow row = sheet.createRow(0);
        //设置单元格，并设置表头居中
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFCell cell = null;

        //创建标题
        for (int i = 0;i<title.size();i++){
            cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0;i<students.size();i++){
            row = sheet.createRow(i+1);
            for (int j = 0;j<title.size();j++){
                if (j==0){
                    row.createCell(j).setCellValue(students.get(i).getStudentID());
                }else if (j==1){
                    row.createCell(j).setCellValue(students.get(i).getName());
                }else if (j==2){
                    row.createCell(j).setCellValue(students.get(i).getSex());
                }else if (j==3){
                    row.createCell(j).setCellValue(students.get(i).getTeacherID());
                }
            }
        }
        return workbook;
    }
}
