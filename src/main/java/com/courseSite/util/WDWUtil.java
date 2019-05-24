package com.courseSite.util;

public class WDWUtil {

    public static boolean isExcel2003(String fileName){
        return fileName.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String fileName){
        return fileName.matches("^.+\\.(?i)(xlsx)$");
    }
}
