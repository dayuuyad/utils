package com.ww.utils.file;

import java.io.File;

public class FileUtils {
    //获取文件名，不带后缀
    public static String getFileName(File file){
        return file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("."));
    }
    //获取文件类型
    public static String getFileType(File file){
        return file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".")+1);
    }
}
