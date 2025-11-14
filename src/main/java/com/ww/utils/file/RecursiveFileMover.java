package com.ww.utils.file;

import java.io.IOException;
import java.nio.file.*;

public class RecursiveFileMover {
    public static void main(String[] args) {
        Path sourceDir = Paths.get("F:\\电影已看\\movies");
        Path targetDir = Paths.get("F:\\电影已看\\test");
        
        try {
            // 确保目标目录存在
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
                System.out.println("创建目标目录: " + targetDir);
            }
            
            // 递归移动文件
            moveFilesRecursively(sourceDir, targetDir);
            
            System.out.println("所有文件移动完成！");
        } catch (IOException e) {
            System.err.println("操作失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void moveFilesRecursively(Path source, Path target) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    // 递归处理子目录
                    moveFilesRecursively(entry, target);
                } else if (Files.isRegularFile(entry)) {
                    // 移动文件
                    Path targetFile = target.resolve(entry.getFileName());
                    
                    // 处理文件名冲突（自动重命名）
                    int counter = 1;
                    while (Files.exists(targetFile)) {
                        String fileName = entry.getFileName().toString();
                        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                        String extension = fileName.contains(".") 
                            ? fileName.substring(fileName.lastIndexOf('.')) 
                            : "";
                            
                        targetFile = target.resolve(baseName + "_" + counter + extension);
                        counter++;
                    }
                    
                    Files.move(entry, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("移动文件: " + entry + " -> " + targetFile);
                }
            }
        }
    }
}