/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.excel_;


import com.opm.core.File_Details;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author john
 */
public class ReadExcelFile_1Row {
    /**
     * 
     * @param file
     * @param loop
     * @param position
     * @param next
     * @throws Exception 
     */
    public static List<String> readRowofStrings(String file, int loop, int position) throws Exception {
        // array list to store the forks
        List<String> lists = new ArrayList<String>();
        //calling the file name.....
        XSSFWorkbook workbook = File_Details.readFileName(file);
       
        XSSFSheet spreadsheet = workbook.getSheetAt(loop);
        String sname = workbook.getSheetName(loop);
        Sheet sheet = workbook.getSheetAt(loop);
        int count = 0;
        for (Row row : sheet) {
                    
            if (count == position) {
                for (Cell cell : row) {
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            //Checking for strings values inthe cells..
                            case Cell.CELL_TYPE_STRING:
                                //if (!cell.getStringCellValue().equals("") && !cell.getStringCellValue().equals(type)) {
                                // adding the call value to the arraylist called forksList 
                                lists.add(cell.getStringCellValue());
                                ///System.out.print("\t" + cell.getStringCellValue());
                                ///}//end of if statement...
                                break;
                            //Checking for numeric values inthe cells..
                            case Cell.CELL_TYPE_NUMERIC:
                                //lists.add( String.valueOf(cell.getNumericCellValue()) );
                                break;
                            //Checking for bank in the cells..
                            case Cell.CELL_TYPE_BLANK:
                                break;
                        }//end of switch statement
                    }
                }
                //System.out.println("\n");
            }
            count++; 
        }
        return lists;
    }
}
