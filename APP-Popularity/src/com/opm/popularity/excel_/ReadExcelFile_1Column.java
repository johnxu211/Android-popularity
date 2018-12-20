/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.excel_;


import com.opm.popularity.core.File_Details;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author openja moses
 * 
 */
public class ReadExcelFile_1Column {
    /**
     * 
     * @param file excel file complete path.. 
     * @param sheet_number the current sheet number to be read
     * @param column_index
     * @param start_row_index
     * @return returns the Column in List of Strings...
     * @throws Exception 
     */
    public static ArrayList<String> readColumnAsString(String file, int sheet_number, int column_index, int start_row_index) throws Exception {

        int j, x;
        // array list to store the forks
        ArrayList<String> lists = new ArrayList<String>();
        //calling the file name.....
        XSSFWorkbook workbook = File_Details.readFileName(file);
        XSSFSheet spreadsheet = workbook.getSheetAt(sheet_number);
        String sname = workbook.getSheetName(sheet_number);

        Row row;
        Cell cell = null;
        for (j = start_row_index; j < spreadsheet.getLastRowNum() + 1; ++j) {//To loop thru the rows in a sheet
            row = spreadsheet.getRow(j);
            cell = row.getCell(column_index); //forks are in the eighth column...
            if (cell != null) {
                switch (cell.getCellType()) {
                    //Checking for strings values inthe cells..
                    case Cell.CELL_TYPE_STRING:
                        //if (!cell.getStringCellValue().equals("") && !cell.getStringCellValue().equals(type)) {
                        // adding the call value to the arraylist called forksList 
                        lists.add(cell.getStringCellValue());
                        //System.out.println(cell.getStringCellValue());
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

        }// end of  for loop for the rows..

        //returns the arraylist to the main class....
        return lists;
    }
    /**
     * 
     * @param file
     * @param sheet_number
     * @param column_index
     * @param start_row_index
     * @return returns List of the column data as Double..
     * @throws Exception 
     */
    public static ArrayList<Double> readColumnAsNumeric(String file, int sheet_number, int column_index, int start_row_index) throws Exception {
        int j, x;
        double empty = 0;
        // array list to store the forks
        ArrayList<Double> lists = new ArrayList<>();
        //calling the file name.....
        XSSFWorkbook workbook = File_Details.readFileName(file);
        XSSFSheet spreadsheet = workbook.getSheetAt(sheet_number);
        String sname = workbook.getSheetName(sheet_number);

        Row row;
        Cell cell = null;
        for (j = start_row_index; j < spreadsheet.getLastRowNum() + 1; ++j) {//To loop thru the rows in a sheet
            row = spreadsheet.getRow(j);
            cell = row.getCell(column_index); //forks are in the eighth column...
            if (cell != null) {
                switch (cell.getCellType()) {
                    //Checking for strings values inthe cells..
                  
                    //Checking for numeric values inthe cells..
                    case Cell.CELL_TYPE_NUMERIC:
                        lists.add(cell.getNumericCellValue());
                        break;
                    //Checking for bank in the cells..
                    case Cell.CELL_TYPE_BLANK:
                        lists.add(empty);
                        break;
                    case Cell.CELL_TYPE_ERROR:
                        lists.add(empty);
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        lists.add(empty);
                        break;
                }//end of switch statement
            }

        }// end of  for loop for the rows..

        //returns the arraylist to the main class....
        return lists;
    }
}
