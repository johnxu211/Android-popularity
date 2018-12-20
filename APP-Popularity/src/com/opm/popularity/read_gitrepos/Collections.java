/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import com.opm.popularity.core.File_Details;
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
public class Collections {

    public static List<List<String>> readCommits(String file, int count) throws Exception {
        //// Store all the datas in array list.....
        List<String> lists = new ArrayList<>();
        List<String> datelists = new ArrayList<>();
        List< List<String>> pullslists = new ArrayList<>();
        List< List<String>> alllists = new ArrayList<>();

        List<String> prOpen = new ArrayList<>();
        List<String> prClosed = new ArrayList<>();
        List<String> isOpen = new ArrayList<>();
        List<String> isClosed = new ArrayList<>();
        List<String> forks = new ArrayList<>();
        List<String> watch = new ArrayList<>();

        List<String> projectlist = new ArrayList<>();

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
                            datelists.add(cell.getStringCellValue());
                            cList.add(cell.getStringCellValue());
                        }
                        if (y > 7 && p > 1) {
                            d = 1;
                            c2List.add(cell.getStringCellValue());
                            if (cell.getStringCellValue().equals("")) {
                                //lists.add("-");
                            } else {
                                // lists.add(cell.getStringCellValue());
                            }
                            lists.add(cell.getStringCellValue());

                        }
                        pl.add(p);

                        /// To cater for repeated commmits onthe same date intervall....
                        if (y > 8 && p > 1) {
                            pl.add(p);
                            b++;
                            //System.out.println(y+"\t"+p+"\t"+cell.getStringCellValue());
                            //if(pl.get(a) == pl.get(b)){
                            cList.add(cell.getStringCellValue());
                            datelists.add(datelists.get(datelists.size() - 1));
                            if (prOpen.size() > 0) {
                                prOpen.add(prOpen.get(prOpen.size() - 1));

                                prClosed.add(prClosed.get(prClosed.size() - 1));
                                isOpen.add(isOpen.get(isOpen.size() - 1));
                                isClosed.add(isClosed.get(isClosed.size() - 1));
                                forks.add(forks.get(forks.size() - 1));
                                watch.add(watch.get(watch.size() - 1));
                            }

                            // }
                        }

                        if (y == 1 && p > 1) {
                            if (cell.getStringCellValue().equals("-")) {
                                prOpen.add("0");
                            } else {
                                prOpen.add(cell.getStringCellValue());
                            }

                        }
                        if (y == 2 && p > 1) {
                            if (cell.getStringCellValue().equals("-")) {
                                prClosed.add("0");
                            } else {
                                prClosed.add(cell.getStringCellValue());
                            }
                        }
                        if (y == 3 && p > 1) {
                            if (cell.getStringCellValue().equals("-")) {
                                isOpen.add("0");
                            } else {
                                isOpen.add(cell.getStringCellValue());
                            }
                        }
                        if (y == 4 && p > 1) {
                            if (cell.getStringCellValue().equals("-")) {
                                isClosed.add("0");
                            } else {
                                isClosed.add(cell.getStringCellValue());
                            }

                        }
                        if (y == 5 && p > 1) {
                            if (cell.getStringCellValue().equals("-")) {
                                forks.add("0");
                            } else {
                                forks.add(cell.getStringCellValue());
                            }

                        }
                        if (y == 6 && p > 1) {
                            watch.add(cell.getStringCellValue());
                        }

                        if (y == 6 && p == 2) {
                            projectlist.add(cell.getStringCellValue());
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
            pullslists.add(plist);
            if (cList.size() > c2List.size()) {
                lists.add("-");
            }
            //System.out.print("\t\t "+pullslists+"\t\t"+1);
            //System.out.println();
        }

        workbook.close();
        // inputStream.close();
        for (int x = 0; x < lists.size(); x++) {
            String[] splits = lists.get(x).split(":-");
            //System.out.println(lists.get(x)+"\t length = "+splits.length);
        }

        if (lists.size() > prOpen.size()) {
            int dif = lists.size() - prOpen.size();
            for (int i = 0; i < dif; i++) {
                prOpen.add("0");
                prClosed.add("0");
                isOpen.add("0");
                isClosed.add("0");
                forks.add("0");
                watch.add("0");
            }
        }

        /// Add all the Lists to the new List
        alllists.add(lists);
        alllists.add(datelists);
        alllists.add(prOpen);
        alllists.add(prClosed);
        alllists.add(isOpen);
        alllists.add(isClosed);
        alllists.add(forks);
        alllists.add(watch);

        alllists.add(projectlist);

        //System.out.println(lists.size()+" : "+datelists.size()+" : "+prOpen.size()+" : "+prClosed.size());
        /// Return the lists to the Merger_Class  ...
        return alllists;

    }

    public static List<List<String>> readCommits_2(String file, int count) throws Exception {
        //// Store all the datas in array list.....
        List<String> lists = new ArrayList<>();
        //List<String> datelists = new ArrayList<>();
        //List< List<String>> pullslists = new ArrayList<>();
        List< List<String>> alllists = new ArrayList<>();

        Workbook workbook = File_Details.readFileName(file);
        Sheet firstSheet = workbook.getSheetAt(count);
        Iterator<Row> iterator = firstSheet.iterator();
        int p = 0;

        while (iterator.hasNext()) {
            p++;
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int y = 0, d = 0;
            //List<String> plist = new ArrayList<>();
            int a = 0;
            int b = 0;
            List<String> cList = new ArrayList<>();
            List<String> c2List = new ArrayList<>();

            //List<Integer> pl = new ArrayList<>();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        if (y == 0 && p > 1) {
                            //datelists.add(cell.getStringCellValue());
                            cList.add(cell.getStringCellValue());
                        }
                        if (y == 8 && p > 1) {
                            d = 1;
                            c2List.add(cell.getStringCellValue());

                            lists.add(cell.getStringCellValue());

                        }
                        //pl.add(p);

                        /// To cater for repeated commmits onthe same date intervall....
                        if (y > 8 && p > 1) {
                            //pl.add(p);
                            b++;
                            //System.out.println(y+"\t"+p+"\t"+cell.getStringCellValue());
                            //if(pl.get(a) == pl.get(b)){
                            c2List.add(cell.getStringCellValue());
                            lists.set(lists.size() - 1, lists.get(lists.size() - 1) + ":-" + cell.getStringCellValue());
                            //datelists.set(datelists.size() - 1, datelists.get(datelists.size() - 1) + ":-" + datelists.get(datelists.size() - 1));
                        }

                        //System.out.print(y+"\t "+cell.getStringCellValue());
                        break;

                }

                ///System.out.print(" - ");
                y++;

            }
            if (cList.size() > c2List.size()) {
                lists.add("-");
            }
            // pullslists.add(plist);

            //System.out.print("\t\t "+pullslists+"\t\t"+1);
            //System.out.println();
        }

        /// Add all the Lists to the new List
        alllists.add(lists);
        //alllists.add(datelists);
        /// Return the lists to the Merger_Class  ...
        return alllists;

    }

    public static List<String> reads(String file, int count, int position) throws Exception {
        //// Store all the datas in array list.....
        List<String> lists = new ArrayList<>();

        Workbook workbook = File_Details.readFileName(file);
        Sheet firstSheet = workbook.getSheetAt(count);
        Iterator<Row> iterator = firstSheet.iterator();
        int p = 0;

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

                            lists.add(cell.getStringCellValue());
                        }
                        pl.add(p);
                        /// To cater for repeated commmits onthe same date intervall....
                        if (y > position && p > 1) {
                            pl.add(p);
                            b++;
                            lists.set(lists.size() - 1, lists.get(lists.size() - 1) + ":-" + cell.getStringCellValue());
                        }
                        break;
                }
                y++;
            }
        }
        workbook.close();
        return lists;
    }
}
