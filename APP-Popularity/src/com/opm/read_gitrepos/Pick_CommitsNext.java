/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.read_gitrepos;

import com.opm.core.File_Details;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author john
 */
public class Pick_CommitsNext {

    public static List<String> pick(String file, int count, int position) throws Exception {
        List<String> lists = new ArrayList<>();
        List<String> dlist = new ArrayList<>();
        
        Workbook workbook = File_Details.readFileName(file);
        Sheet firstSheet = workbook.getSheetAt(count);
        Iterator<Row> iterator = firstSheet.iterator();
        int p = 0;

        while (iterator.hasNext()) {
            p++;
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int y = 0, d = 0;
            List<String> cList = new ArrayList<>();
            List<String> c2List = new ArrayList<>();
            List<String> plist = new ArrayList<>();
            int a = 0;
            int b = 0;
            List<Integer> pl = new ArrayList<>();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        if (y == 0 && p > 1) {
                            dlist.add(cell.getStringCellValue());
                            cList.add(cell.getStringCellValue());
                        }
                        if (y == position && p > 1) {
                            d = 1;
                            c2List.add(cell.getStringCellValue());
                            if (cell.getStringCellValue().equals("")) {
                                lists.add("-");
                            } else {
                                lists.add(cell.getStringCellValue());
                            }

                        }
                        pl.add(p);

                        /// To cater for repeated commmits onthe same date intervall....
                        if (y > position && p > 1) {
                            pl.add(p);
                            b++;
                            
                            lists.set(lists.size()-1, lists.get(lists.size()-1)+":-"+cell.getStringCellValue());
                            
                        }

                        
                        //System.out.print(y+"\t "+cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        // System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        // System.out.print(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        //  System.out.print(y+"\t NoData ");
                        //lists.add("-");
                        break;

                }
                ///System.out.print(" - ");
                y++;
            }
            if(cList.size() > c2List.size()){
             lists.add("-");
            }
            
        }

        workbook.close();
        
        return lists;

    }

}
