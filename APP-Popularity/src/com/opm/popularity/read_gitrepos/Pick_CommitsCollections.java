/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import com.opm.popularity.core.File_Details;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author john
 */
public class Pick_CommitsCollections {

    /**
     *
     * @param file
     * @param count
     * @return
     * @throws Exception
     */
    public static String pick(String file, int count, int position) throws Exception {
        //// Store all the datas in array list.....
        List<String> lists = new ArrayList<>();

        String sheetName = "";
        Workbook workbook = File_Details.readFileName(file);
        Sheet firstSheet = workbook.getSheetAt(count);
        Iterator<Row> iterator = firstSheet.iterator();

        int p = 0;
        List<String> cList = new ArrayList<>();
        while (iterator.hasNext()) {
            p++;
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int y = 0, d = 0;

            List<String> plist = new ArrayList<>();
            int a = 0;
            int b = 0;
            List<Integer> pl = new ArrayList<>();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        if (y >= position && p > 1) {
                            d = 1;
                            if (!cell.getStringCellValue().equals("") && !cell.getStringCellValue().equals("-")) {
                                cList.add(cell.getStringCellValue());
                            }
                            //System.out.println(p + " \t " + y);
                        }
                        break;
                }
                ///System.out.print(" - ");
                y++;
            }
        }
        int com = 0;
        for (int i = 0; i < cList.size(); i++) {
            String[] splits = cList.get(i).split("/");
            com += Integer.parseInt(splits[splits.length - 1].split("_")[0]);
        }
        if (com < 6) {
            sheetName = workbook.getSheetName(count) + "/" + com;
            workbook.removeSheetAt(count);
        }else{
            String sheet =  "-";
            sheetName =  sheet+"/" + com;
        }

        workbook.close();

        return sheetName;
    }
}
