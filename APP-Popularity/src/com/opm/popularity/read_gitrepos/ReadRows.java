/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import static com.opm.popularity.core.File_Details.readFileName;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author john
 */
public class ReadRows {
    public static List<String> readStrings(String file, int row_count, String title) throws Exception {
        int x = 0;
        // array list to store the Repos names
        ArrayList<String> list = new ArrayList<String>();
        //calling the file name.....
        XSSFWorkbook workbook = readFileName(file);
        // setting the sheet number...
        XSSFSheet spreadsheet = workbook.getSheetAt(x);
        String sname = workbook.getSheetName(x);

        Row row;
        Cell cell = null;
        for (int j = 0; j < spreadsheet.getLastRowNum() + 1; ++j) {//To loop thru the rows in a sheet
            row = spreadsheet.getRow(j);
            cell = row.getCell(row_count); //forks are in the eighth column...
            switch (cell.getCellType()) {
                //Checking for strings values inthe cells..
                case Cell.CELL_TYPE_STRING:
                    if (!cell.getStringCellValue().equals("") || !cell.getStringCellValue().equals(title)) {
                        // adding the call value to the arraylist called forksList 
                        list.add(cell.getStringCellValue());
                    }//end of if statement...
                    break;
                //Checking for numeric values inthe cells..
                case Cell.CELL_TYPE_NUMERIC:
                    //list.add(String.valueOf(cell.getNumericCellValue()));
                    break;
                //Checking for bank in the cells..
                case Cell.CELL_TYPE_BLANK:
                    break;
            }//end of switch statement

        }// end of  for loop for the rows.. 
        //returns the arraylist to the main class....
        return list;
    }
    
    public static List<Double> readNumeric(String file, int row_count) throws Exception {
        int x = 0;
        // array list to store the Repos names
        List<Double> list = new ArrayList<>();
        //calling the file name.....
        XSSFWorkbook workbook = readFileName(file);
        // setting the sheet number...
        XSSFSheet spreadsheet = workbook.getSheetAt(x);
        String sname = workbook.getSheetName(x);

        Row row;
        Cell cell = null;
        for (int j = 0; j < spreadsheet.getLastRowNum() + 1; ++j) {//To loop thru the rows in a sheet
            row = spreadsheet.getRow(j);
            cell = row.getCell(row_count); //forks are in the eighth column...
            switch (cell.getCellType()) {
                //Checking for strings values inthe cells..
                case Cell.CELL_TYPE_STRING:
                    
                    break;
                //Checking for numeric values inthe cells..
                case Cell.CELL_TYPE_NUMERIC:
                    list.add((cell.getNumericCellValue()));
                    break;
                //Checking for bank in the cells..
                case Cell.CELL_TYPE_BLANK:
                    break;
            }//end of switch statement

        }// end of  for loop for the rows.. 
        //returns the arraylist to the main class....
        return list;
    }
}
