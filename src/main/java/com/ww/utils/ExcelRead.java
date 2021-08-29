package com.ww.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelRead {
    //不用了
    public static String[][] getSheetData1(XSSFSheet sheet) throws IOException {
        String[][] testArray = new String[sheet.getPhysicalNumberOfRows()][];
        for(int rowId =0;rowId<sheet.getPhysicalNumberOfRows();rowId++){
            XSSFRow row = sheet.getRow(rowId);
            List<String> testSetList = new ArrayList<String>();
            for(int column=0;column<row.getPhysicalNumberOfCells();column++){
                row.getCell(column).setCellType(CellType.STRING);
                testSetList.add(row.getCell(column).getStringCellValue());
            }
            testArray[rowId] = (String[])testSetList.
                    toArray(new String[testSetList.size()]);
        }
        return testArray;
    }
    //默认第一页
    public static String[][] getSheetData(String path) throws IOException {
        return getSheetData(path, 0);
    }

    public static String[][] getSheetData(String path,int sheetIndex) throws IOException {
        FileInputStream is =  new FileInputStream(path);
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(is);
        } catch (Exception ex) {
            workbook = new HSSFWorkbook(is);
        }
//        HSSFWorkbook excel=new HSSFWorkbook(is);
//        HSSFSheet sheet=excel.getSheet("XXXXX");
        //获取第一个sheet
        Sheet sheet=workbook.getSheetAt(sheetIndex);
        //建立二维数组，行数等于sheet页的行数
        String[][] sheetData = new String[sheet.getPhysicalNumberOfRows()][];
        //遍历每一行
        for(int rowId =0;rowId<sheet.getPhysicalNumberOfRows();rowId++){
            Row row = sheet.getRow(rowId);
//            List<String> testSetList = new ArrayList<String>();
            //每一行是一个一维数组
            String[] rowArrays=new String[row.getPhysicalNumberOfCells()];
            //遍历该行的每一列，给一维数组赋值
            for(int column=0;column<row.getPhysicalNumberOfCells();column++){
                row.getCell(column).setCellType(CellType.STRING);
                rowArrays[column]=row.getCell(column).getStringCellValue();
//                testSetList.add(row.getCell(column).getStringCellValue());
            }
            //将本行数据添加到二维数组中
            sheetData[rowId] = rowArrays;

        }
        return sheetData;
    }

    public static void printSheetData(String[][] sheetData) throws IOException{
        for(int i =0; i<sheetData.length;i++ )
        {
            for (int j=0; j<sheetData[i].length;j++)
            {
                System.out.print(sheetData[i][j]+"\t\t");
            }
            System.out.println();
        }

    }

    public static <T> List<T> sheetDataToObject(String[][]sheetData, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        List<T> list=new ArrayList<>();
//        String [] s=new String[4];
        String[] culomnNames=sheetData[0];
        Map<String,Integer> map=new HashMap<>();
//        Class clazz=t.getClass();
        Field[] fields=clazz.getDeclaredFields();
        Method[] methods=clazz.getMethods();
        for (Field field :fields) {
            String fieldName=field.getName();
//            for (String culomnName :culomnNames) {
            for (int i=0;i<culomnNames.length;i++) {
                String culomnName=culomnNames[i];
                if (fieldName.equals(culomnName)){

                    String methodName="set"+StringUtils.toUpperFirstOne(fieldName);
                    map.put(methodName,i);
                }
            }
        }
        for (int i=1;i<sheetData.length;i++){
            T t = clazz.getConstructor().newInstance();
            for (String key : map.keySet()) {
                Method setMethod=t.getClass().getMethod(key,String.class);
                setMethod.invoke(t,sheetData[i][map.get(key)]);
            }
            list.add(t);
        }
        return list;
    }

    public static <T> List<T> sheetDataToObjectNew(String[][]sheetData, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        List<T> list=new ArrayList<>();
        String[] culomnNames=sheetData[0];
        Field[] fields=clazz.getDeclaredFields();
//        System.out.println(clazz);
//        System.out.println("fields"+fields.length);
//        Method[] methods=clazz.getMethods();
//        for (Field o :fields) {
//            System.out.println(o.getName());
//        }
        //遍历每一行
        for (int i=1;i<sheetData.length;i++){
            //将每一行转换成一个对象
            T t = clazz.getConstructor().newInstance();
            //遍历对象所有属性
            for (Field field :fields) {
                String fieldName=field.getName();
                //遍历所有列明
                for (int index=0;index<culomnNames.length;index++) {
                    String culomnName=culomnNames[index];
                    //如果列明与对象属性明一致，用这个属性的set方法赋值，跳出循环
                    if (fieldName.equals(culomnName)){
                        String methodName="set"+StringUtils.toUpperFirstOne(fieldName);
                        Method setMethod=clazz.getMethod(methodName,field.getType());
                        setMethod.invoke(t,sheetData[i][index]);
                        break;
                    }
                }

            }
            list.add(t);//将对象添加到集合
        }
        return list;
    }

    //优化过的，效率好的
    public static <T> List<T> sheetDataToObjectNew2(String[][]sheetData, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        List<T> list=new ArrayList<>();
        //列明
        String[] culomnNames=sheetData[0];
        //属性名
        Field[] fields=clazz.getDeclaredFields();
        Map<Method,Integer> map=new HashMap<>();
        //遍历所有属性
        for (Field field :fields) {
            //遍历所有列明，如果列明等于属性明，将属性的set方法，和列的列号的映射关系，放进去map。跳出循环
            for (int i=0;i<culomnNames.length;i++){
                if (field.getName().equals(culomnNames[i])){
                    String methodName="set"+StringUtils.toUpperFirstOne(field.getName());
                    Method setMethod=clazz.getMethod(methodName,field.getType());
                    map.put(setMethod,i);
                    break;
                }
            }
        }
        //遍历所有行
        for (int i=1;i<sheetData.length;i++){
            //每行转成一个对象
            T t = clazz.getConstructor().newInstance();
            int finalI = i;
            //遍历对象的set方法，执行对象的set方法
            map.forEach((key, value) ->{
                try {
                    key.invoke(t,sheetData[finalI][value]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            list.add(t);//对象添加进入集合
        }
        return list;
    }

    //每一行转成一个键值对
    public static List<Map<String,String>> sheetDataToMap(String[][]sheetData){
        List<Map<String,String>> list=new ArrayList<>();
        for (int i=1;i<sheetData.length;i++){
            Map<String,String> map=new HashMap<>();
            for (int j=0;j<sheetData[i].length;j++){
                map.put(sheetData[0][j],sheetData[i][j]);
            }
            list.add(map);
        }
        return list;
    }
}
