package com.ww.utils.file;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.io.IOException;
import java.nio.file.*;


public class FileMover {
    public static void main(String[] args) {
        // 源文件夹路径
        Path sourceDir = Paths.get("F:\\电影已看\\movie");
        // 目标文件夹路径
        Path targetDir = Paths.get("F:\\电影已看\\movie");
        
        try {
            // 确保目标文件夹存在，不存在则创建
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
                System.out.println("创建目标目录: " + targetDir);
            }
            
            // 遍历源文件夹下的所有文件
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir)) {
                for (Path file : stream) {
                    if (Files.isRegularFile(file)) {  // 确保是文件而非文件夹
                        Path targetFile = targetDir.resolve(file.getFileName());
                        
                        // 移动文件（覆盖已存在的同名文件）
                        Files.move(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("移动文件: " + file.getFileName());
                    }
                    System.out.println(file.getFileName());
                    break;
                }
            }
            System.out.println("所有文件移动完成！");
        } catch (IOException e) {
            System.err.println("操作失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}