/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.core;

/**
 *
 * @author john
 */
public class SplitString {
    public static String[] split(String src, int len) {
        String[] result = new String[(int) Math.ceil((double) src.length() / (double) len)];
        for (int i = 0; i < result.length; i++) {
            result[i] = src.substring(i * len, Math.min(src.length(), (i + 1) * len));
        }
        return result;
    }
}
