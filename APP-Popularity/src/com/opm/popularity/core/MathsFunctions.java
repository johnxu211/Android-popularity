/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.core;

import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.copy;
import java.util.List;

/**
 *
 * @author john
 */
public class MathsFunctions {

    static List<Integer> inputs = new ArrayList<>();

    public MathsFunctions(List<Integer> inputs) {
        this.inputs = inputs;
    }

    public static double getMean(List<Double> invalue) {
        int num_value = invalue.size();
        double tot = 0;
        double mean = 0;
        for (int i = 0; i < num_value; i++) {
            tot = tot + invalue.get(i);
        }
        mean = tot / num_value;

        return mean;
    }

    public static double getSD(List<Double> invalue) {
        int num_value = invalue.size();
        double tot = 0;
        double mean = 0;
        for (int i = 0; i < num_value; i++) {
            tot = tot + invalue.get(i);
        }
        mean = tot / num_value;
        double sd = 0;
        for (int i = 0; i < invalue.size(); i++) {
            sd += ((invalue.get(i) - mean) * (invalue.get(i) - mean));
        }
        double standardDeviation = Math.sqrt(sd / (invalue.size() - 1));

        return standardDeviation;
    }

    public static double getMedian(List<Double> invalue) {
        int num_value = invalue.size();
        double tot = 0;

        //Median calculation
        double[] sort_invalue = new double[num_value];
        for (int i = 0; i < num_value; i++) {
            sort_invalue[i] = invalue.get(i);
        }
        Arrays.sort(sort_invalue);

        double median = 0;
        double mid = 0;
        if (num_value % 2 == 0) {
            int temp = (num_value / 2) - 1;
            for (int i = 0; i < num_value; i++) {
                if (temp == i || (temp + 1) == i) {
                    mid = mid + sort_invalue[i];
                }
            }
            mid = mid / 2;
            median = mid;
        } else {
            int temp = (num_value / 2);
            for (int i = 0; i < num_value; i++) {
                if (temp == i) {
                    mid = sort_invalue[i];
                    median = mid;
                    //System.out.println("Median value: " + mid);
                }
            }
        }

        return median;
    }

    public static double getMode(List<Double> invalue) {

        int num_value = invalue.size();
        //Mode calculation

        double tmp, maxCount, modeValue;
        int i, j, z;
        int[] tally = new int[num_value];
        for (i = 0; i < num_value; i++) {
            for (j = 0; j < num_value - i; j++) {
                if (j + 1 != num_value) {
                    if (invalue.get(j) > invalue.get(j + 1)) {
                        tmp = invalue.get(j);
                        invalue.set(j, invalue.get(j + 1));
                        invalue.set(j + 1, tmp);
                    }
                }
            }
        }
        for (i = 0; i < num_value; i++) {
            for (z = i + 1; z < num_value; z++) {
                if (invalue.get(i) == invalue.get(i)) {
                    tally[i]++;
                }
            }
        }
        maxCount = 0;
        modeValue = 0;
        for (i = 0; i < num_value; i++) {
            if (tally[i] > maxCount) {
                maxCount = tally[i];
                modeValue = invalue.get(i);
            }
        }
        return modeValue;
    }

    public static void insertionSort(List<String> lList, List<Double> input, List<Double> input2) {

        List<String> commitList = new ArrayList<>();
        double temp, temp2;
        String login;
        for (int i = 1; i < input.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (input.get(j) < input.get(j - 1)) {
                    /// Sort changed...
                    /// Sort changed...
                    temp = input.get(j);
                    input.set(j, input.get(j - 1));
                    input.set((j - 1), temp);

                    temp2 = input2.get(j);
                    input2.set(j, input2.get(j - 1));
                    input2.set((j - 1), temp2);
                    /// Sort login
                    login = lList.get(j);
                    lList.set(j, lList.get(j - 1));
                    lList.set((j - 1), login);
                    /// Sort names...
                }
            }
        }
    }

    public static void InsertionSort(List<String> lList, List<Double> input, List<Double> input2) {
        int j;                     // the number of items sorted so far
        double key;
        double key2;// the item to be inserted
        String login;
        int i;

        for (j = 1; j < input.size(); j++) // Start with 1 (not 0)
        {
            key = input.get(j);
            key2 = input2.get(j);
            login = lList.get(j);
            for (i = j - 1; (i >= 0) && (input.get(i) < key); i--) // Smaller values are moving up
            {
                input.set(i + 1, input.get(i));
                input2.set(i + 1, input2.get(i));
                lList.set(i + 1, lList.get(i));
            }
            input.set(i + 1, key); // Put the key in its proper location
            input2.set(i + 1, key2); // Put the key in its proper location
            lList.set(i + 1, login); // Put the key in its proper location
        }
    }

    public static void InsertionSort2(List<String> lList, List<Double> input, List<Double> input2, List<Double> input3) {
        int j;                     // the number of items sorted so far
        double key;
        double key2;// the item to be inserted
        double key3;
        String login;
        int i;

        for (j = 1; j < input.size(); j++) // Start with 1 (not 0)
        {
            key = input.get(j);
            key2 = input2.get(j);
            key3 = input3.get(j);
            login = lList.get(j);
            for (i = j - 1; (i >= 0) && (input.get(i) < key); i--) // Smaller values are moving up
            {
                input.set(i + 1, input.get(i));
                input2.set(i + 1, input2.get(i));
                input3.set(i + 1, input3.get(i));
                lList.set(i + 1, lList.get(i));
            }
            input.set(i + 1, key); // Put the key in its proper location
            input2.set(i + 1, key2); // Put the key in its proper location
            input3.set(i + 1, key3); // Put the key in its proper location
            lList.set(i + 1, login); // Put the key in its proper location
        }
    }
    
    public static void InsertionSort3(List<String> lList, List<Double> input, List<Double> input2, List<Double> input3, List<Double> input4) {
        int j;                     // the number of items sorted so far
        double key;
        double key2;// the item to be inserted
        double key3;
        double key4;
        String login;
        int i;

        for (j = 1; j < input.size(); j++) // Start with 1 (not 0)
        {
            key = input.get(j);
            key2 = input2.get(j);
            key3 = input3.get(j);
            key4 = input4.get(j);
            login = lList.get(j);
            for (i = j - 1; (i >= 0) && (input.get(i) < key); i--) // Smaller values are moving up
            {
                input.set(i + 1, input.get(i));
                input2.set(i + 1, input2.get(i));
                input3.set(i + 1, input3.get(i));
                input4.set(i + 1, input4.get(i));
                lList.set(i + 1, lList.get(i));
            }
            input.set(i + 1, key); // Put the key in its proper location
            input2.set(i + 1, key2); // Put the key in its proper location
            input3.set(i + 1, key3); // Put the key in its proper location
            input4.set(i + 1, key4); // Put the key in its proper location
            lList.set(i + 1, login); // Put the key in its proper location
        }
    }

    public static Double maxVal(List<Double> value) {
        Double[] inputs = new Double[value.size()];
        inputs = value.toArray(inputs);
        Arrays.sort(inputs);
        return inputs[inputs.length - 1];
    }

    public static Double minVal(List<Double> value) {
        Double[] inputs = new Double[value.size()];
        inputs = value.toArray(inputs);
        Arrays.sort(inputs);
        return inputs[0];
    }
}
