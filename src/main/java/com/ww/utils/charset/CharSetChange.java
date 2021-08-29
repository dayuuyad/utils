package com.ww.utils.charset;

import com.ww.utils.file.FileUtils;

import java.io.*;

public class CharSetChange {
    //文件，和要替换的类型
    public static void GbkToUtf8(File file,String backWord) throws IOException {
        //如果是一个目录，递归
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                GbkToUtf8(file1,backWord);
            }
        }else {//如果是文件，替换
            if (FileUtils.getFileType(file).equals(backWord)) {
                fileGbkToUtf8(file);
            }
        }
    }
    private static void fileGbkToUtf8(File beforePath) throws IOException {

        String after= FileUtils.getFileName(beforePath)+"After."+FileUtils.getFileType(beforePath);
        File afterPath=new File(after);

        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(beforePath),"gbk"));
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afterPath),"utf8"));
        String line;
        while ((line=bufferedReader.readLine())!=null){
//            System.out.println(line);
            bufferedWriter.write(line+"\n");
        }
        bufferedReader.close();

        bufferedWriter.flush();
        bufferedWriter.close();
        beforePath.delete();

        afterPath.renameTo(new File(afterPath.getAbsolutePath().replaceAll("After", "")));

    }

    public static void GbkToUtf8(String path1,String backWord) throws IOException {
        GbkToUtf8(new File(path1),backWord);
    }
}
