/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.core;

import java.util.List;

/**
 *
 * @author john
 */
public class InsertionSorting {

    public static void insertionSort(List<Double> chList, List<Double> cList, List<String> nList, List<String> eList, List<String> lList) {
        int j;                     // the number of items sorted so far
        //Double key;                // the item to be inserted
        Double key_ch;
        Double key_com;
        int i;
        //String tagDate;
        String n_key;
        String e_key;
        String l_key;

        for (j = 1; j < chList.size(); j++) // Start with 1 (not 0)
        {
            //key = num.get(j);
            key_ch = chList.get(j);
            key_com = cList.get(j);
            //tagDate = tagDates.get(j);
            n_key = nList.get(j);
            e_key = eList.get(j);
            l_key = lList.get(j);
            for (i = j - 1; (i >= 0) && (chList.get(i) < key_ch); i--) // Smaller values are moving up
            {
                //num[ i+1 ] = num[ i ];
                chList.set(i + 1, chList.get(i));
                cList.set(i + 1, cList.get(i));

                nList.set(i + 1, nList.get(i));
                eList.set(i + 1, eList.get(i));
                lList.set(i + 1, lList.get(i));
                //tagDates.set(i+1, tagDates.get(i));
            }
            //num[ i+1 ] = key;    // Put the key in its proper location
            //num.set(i+1, key_ch);
            chList.set(i + 1, key_ch);
            cList.set(i + 1, key_com);

            nList.set(i + 1, n_key);
            eList.set(i + 1, e_key);
            lList.set(i + 1, l_key);
            // tagDates.set(i+1, tagDate);
        }
    }

    public static void insertion_2(List<Double> chList, List<Double> cList, List<String> nList, List<String> eList, List<String> lList, List<Double> cha, List<Double> com) {
        int j;                     // the number of items sorted so far
        //Double key;                // the item to be inserted
        Double key_ch;
        Double key_com;

        Double key_cha;
        Double key_cm;
        int i;
        //String tagDate;
        String n_key;
        String e_key;
        String l_key;

        for (j = 1; j < chList.size(); j++) // Start with 1 (not 0)
        {
            //key = num.get(j);
            key_ch = chList.get(j);
            key_com = cList.get(j);

            key_cha = cha.get(j);
            key_cm = com.get(j);

            //tagDate = tagDates.get(j);
            n_key = nList.get(j);
            e_key = eList.get(j);
            l_key = lList.get(j);
            for (i = j - 1; (i >= 0) && (chList.get(i) < key_ch); i--) // Smaller values are moving up
            {
                //num[ i+1 ] = num[ i ];
                chList.set(i + 1, chList.get(i));
                cList.set(i + 1, cList.get(i));

                cha.set(i + 1, cha.get(i));
                com.set(i + 1, com.get(i));

                nList.set(i + 1, nList.get(i));
                eList.set(i + 1, eList.get(i));
                lList.set(i + 1, lList.get(i));
                //tagDates.set(i+1, tagDates.get(i));
            }
            //num[ i+1 ] = key;    // Put the key in its proper location
            //num.set(i+1, key_ch);
            chList.set(i + 1, key_ch);
            cList.set(i + 1, key_com);

            cha.set(i + 1, key_cha);
            com.set(i + 1, key_cm);

            nList.set(i + 1, n_key);
            eList.set(i + 1, e_key);
            lList.set(i + 1, l_key);
            // tagDates.set(i+1, tagDate);
        }
    }

}
