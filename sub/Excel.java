package sub;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Excel{

    private String name;
    private int sheetNumber;
    /*private String linkToRead;
    private String linkToWrite;*/

    public Excel(){
        this.name = "Гирман";
        this.sheetNumber = 2;
    }

    private int getRowsNumber(XSSFSheet sheet){
        return sheet.getLastRowNum();
    }

    private int getColumnsNumber(XSSFSheet sheet){
        int columnsNumber = 0;
        Iterator<?> rows = sheet.rowIterator();
        while(rows.hasNext()){
            XSSFRow row = (XSSFRow) rows.next();
            Iterator<?> cells = row.cellIterator();
            while(cells.hasNext()){
                columnsNumber++;
                cells.next();
            }
            break;
        }
        return columnsNumber;
    }

    public ArrayList<double[]> readExcel(String linkToRead){

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(linkToRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workbook.getSheetAt(sheetNumber-1);

        int rowsNumber = getRowsNumber(sheet);
        int columnsNumber = getColumnsNumber(sheet);

        ArrayList<double[]> arrayList = new ArrayList<double[]>();
        for(int j=0; j<columnsNumber; j++){
            double arr[] = new double[rowsNumber];
            for(int i=0; i<rowsNumber; i++){
                arr[i] = sheet.getRow(i+1).getCell(j).getNumericCellValue();
            }
            arrayList.add(arr);
        }

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;

    }

    public void writeExcel(ArrayList<LinkedHashMap<String, Double>> mapList, String linkToWrite){

        XSSFWorkbook workbookOut = new XSSFWorkbook();
        XSSFSheet sheetOut = workbookOut.createSheet(name);

        XSSFRow header = sheetOut.createRow(0);
        header.createCell(0).setCellValue("");
        header.createCell(1).setCellValue("X");
        header.createCell(2).setCellValue("Y");
        header.createCell(3).setCellValue("Z");
        int i = 1;
        for(String st : mapList.get(0).keySet()){
            XSSFRow rw = sheetOut.createRow(i);
            rw.createCell(0).setCellValue(st);
            for(int j=1; j<=mapList.size(); j++){
                rw.createCell(j).setCellValue(mapList.get(j-1).get(st));
            }
            i++;
        }

        try {
            workbookOut.write(new FileOutputStream(linkToWrite));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            workbookOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
