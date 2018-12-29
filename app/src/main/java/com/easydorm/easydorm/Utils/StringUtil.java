package com.easydorm.easydorm.Utils;

public class StringUtil {

    public static String makeFileName(String userId, String fileName) {
        if(userId == null || fileName == null) return fileName;
        int dot = fileName.lastIndexOf('.');
        if(dot > -1 && dot < fileName.length()) {
            fileName = userId +fileName.substring(dot);
        }
        return fileName;
    }

}
