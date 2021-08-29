package com.ww;

import com.ww.test.ExcelBean;
import com.ww.test.ExcelBean2;
import com.ww.utils.ExcelRead;
import com.ww.utils.charset.CharSetChange;
import com.ww.utils.file.FileMerge;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String path="C:\\Users\\ww\\Desktop\\test\\test.xlsx";
        String [][] sheetData= ExcelRead.getSheetData(path,0);
//        String [][] sheetData= ExcelRead.getSheetData(path);
        ExcelRead.printSheetData(sheetData);
//        System.out.println("\t\t111111111111");
//        List<ExcelBean> excelBeans=ExcelRead.SheetDataToObject(sheetData,ExcelBean.class);
//        List<ExcelBean> excelBeans=ExcelRead.SheetDataToObjectNew(sheetData,ExcelBean.class);
        List<ExcelBean2> excelBeans=ExcelRead.sheetDataToObjectNew2(sheetData, ExcelBean2.class);

        for (ExcelBean2 excelBean :excelBeans) {
            System.out.println(excelBean);
        }

    }
    @Test
    public void test2(){

        String path="C:\\Users\\ww\\Desktop\\test\\test.xlsx";
        try {
            String [][] sheetData= ExcelRead.getSheetData(path,0);
            List<Map<String,String>> list=ExcelRead.sheetDataToMap(sheetData);
            for (Map map:list){
                System.out.println(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test3(){
        Field[] fields=ExcelBean.class.getDeclaredFields();
        System.out.println(fields.length);
    }

    @Test
    public void testCharset() throws IOException {
        File directory=new File("C:\\Users\\ww\\Desktop\\test");
        CharSetChange.GbkToUtf8(directory,"java");
    }
    @Test
    public void FileMerge() throws Exception {
        String numbers="2";
        char[] chars=numbers.toCharArray();
        String path="E:\\tools\\Thunder Network\\Xmp\\Profiles\\截图\\";
        String tpye=".mp4";
        List<File> list=new ArrayList<>();
        for (char c :chars) {
            File f=new File(path+c+tpye);
            list.add(f);
        }
        File[] files=new File[list.size()];
        list.toArray(files);

        for (File file :files) {
//            System.out.println(file);
        }
        FileMerge.merge(files,new File(path+"mergeFile"+tpye));
    }

    @Test
    public void FileMerge2() throws IOException {
        String numbers="66";
        char[] chars=numbers.toCharArray();
        String path="D:\\download\\test\\";
        List<File> list=new ArrayList<>();
        for (char c :chars) {
            File f=new File(path+c+".ts");
            list.add(f);
        }
        File[] files=new File[list.size()];
        list.toArray(files);

        for (File file :files) {
//            System.out.println(file);
        }
        FileMerge.merge(files,new File("D:\\download\\mergeFile.mp4"));

    }

    @Test
    public void FileMerge3() throws Exception {
        String numbers="999";
        char[] chars=numbers.toCharArray();
        String path="E:\\tools\\Thunder Network\\Xmp\\Profiles\\截图\\";
        String tpye=".mp4";
        List<String> list=new ArrayList<>();
        for (char c :chars) {
//            File f=new File(path+c+tpye);
            list.add(path+c+tpye);
        }

        FileMerge.mergeVideo(list,new File(path+"mergeFile"+tpye));
    }
    @Test
    public void test0328(){
        FileMerge.merge2();
    }

    @Test
    public void FileMerge4() throws Exception {
        String path = "E:\\tools\\Thunder Network\\Xmp\\Profiles\\截图\\";
        String tpye = ".mp4";
        List<String> list = new ArrayList<>();
        list.add("D:\\download\\test\\out\\mv1.mp4");
        list.add("D:\\download\\test\\out\\mv2.mp4");
        String mergeFile = "D:\\download\\test\\out\\mv3.mp4";
        FileMerge.mergeVideo(list, new File(mergeFile));
    }


}
