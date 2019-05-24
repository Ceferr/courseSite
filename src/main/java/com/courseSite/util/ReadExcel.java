package com.courseSite.util;

import com.courseSite.constant.Constant;
import com.courseSite.pojo.Student;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadExcel {

    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;

    private String errorMsg;

    public ReadExcel() {}

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCells() {
        return totalCells;
    }

    public String getErrorMsg() {
        return errorMsg;
    }


    public boolean validateExcel(String fileName){
        if (fileName == null||(!(WDWUtil.isExcel2003(fileName))&&!(WDWUtil.isExcel2007(fileName)))){
            errorMsg = "文件不是excel格式";
            System.out.println(errorMsg);
            return false;
        }
        return true;
    }

    public List<Student> getExcelInfo(String fileName, MultipartFile mfile){
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)mfile;

        File file = new File(Constant.EXCEL_ROOT);
        if (!file.exists()) file.mkdirs();
        File file1 = new File(Constant.EXCEL_ROOT+new Date().getTime()+".xlsx");
        try {
            commonsMultipartFile.getFileItem().write(file1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Student> students = new ArrayList<>();
        InputStream inputStream = null;
        try {
            if (!validateExcel(fileName)){
                return null;
            }
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(fileName)){
                isExcel2003 = false;
            }
            inputStream = new FileInputStream(file1);
            students = getExcelInfo(inputStream,isExcel2003);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    inputStream = null;
                    e.printStackTrace();
                }
            }
        }
        return students;
    }

    public List<Student> getExcelInfo(InputStream inputStream,boolean isExcel2003){
        List<Student> students = null;
        Workbook workbook = null;
        try {
            if (isExcel2003){
                workbook = new HSSFWorkbook(inputStream);
            }else{
                workbook = new XSSFWorkbook(inputStream);
            }
            students = readExcelValue(workbook);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    private List<Student> readExcelValue(Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<Student> students = new ArrayList<>();
        Student student;
        System.out.println(this.totalRows+" "+this.totalCells);
        for (int r = 1;r<this.totalRows;r++){
            Row row = sheet.getRow(r);
            if (row == null) continue;
            student = new Student();

            for (int c = 0;c<this.totalCells;c++){
                Cell cell = row.getCell(c);
                if (cell!=null){
                    if (c==0){
                        student.setStudentID(Long.valueOf(NumberToTextConverter.toText(cell.getNumericCellValue())));
                    }else if (c==1){
                        student.setName(cell.getStringCellValue());
                    }else if (c==2){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        student.setPassword(cell.getStringCellValue());
                    }else if (c==3){
                        student.setSex(cell.getStringCellValue());
                    }else if (c==4){
                        student.setTeacherID(Long.valueOf(NumberToTextConverter.toText(cell.getNumericCellValue())));
                    }
                }
            }
            students.add(student);
        }
        return students;
    }
}
