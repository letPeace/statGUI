package sub;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Excel{

    private String name;
    private int sheetNumber;
    private String linkToRead;
    private String linkToWrite;

    public Excel(){
        this.name = "Гирман";
        this.sheetNumber = 2;
    }

    public ArrayList<double[]> readExcel(String linkToRead){

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(linkToRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workbook.getSheetAt(sheetNumber-1);
        int rowsNumber = sheet.getLastRowNum();

        double arrX[] = new double[rowsNumber];
        double arrY[] = new double[rowsNumber];
        double arrZ[] = new double[rowsNumber];

        for(int i=0; i<rowsNumber; i++){
            arrX[i] = sheet.getRow(i+1).getCell(0).getNumericCellValue();
            arrY[i] = sheet.getRow(i+1).getCell(1).getNumericCellValue();
            arrZ[i] = sheet.getRow(i+1).getCell(2).getNumericCellValue();
        }

        ArrayList<double[]> arrayList = new ArrayList<double[]>();
        arrayList.add(arrX);
        arrayList.add(arrY);
        arrayList.add(arrZ);

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

        int i = 0;
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
